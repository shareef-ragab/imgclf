package v2;

public class FullyConnectedLayer {
	private final double[][] weights;
	private final ActivationFunction activation;
	
	private FullyConnectedLayer(double[][] weights, ActivationFunction activation) {
		this.weights = weights;
		this.activation = activation;
	}
	
	public double[] computeOutput(double[] input) {
		if (input.length != weights[0].length) { // Valid check because we enforce > 0 inputs.
			throw new IllegalArgumentException(
					"Input length must match layer input specification.");
		}
		double[] outputs = new double[weights.length]; 
		for (int i = 0; i < outputs.length; i++) {
			double sum = 0;
			for (int j = 0; j < weights[i].length; j++) {
				sum += weights[i][j] * input[j];
			}
			// TODO: Add offset.
			outputs[i] = activation.apply(sum);
		}
		return outputs;
	}

	public double[] propagateError(double[] error, double learningRate) {
		// TODO: Implement this method.
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n------\tFully Connected Layer\t------\n");
		builder.append(String.format("Number of inputs: %d\n", weights[0].length));
		builder.append(String.format("Number of nodes: %d\n", weights.length));
		builder.append(String.format("Activation function: %s\n", activation.toString()));
		builder.append("\n\t------------\t\n");
		return builder.toString();
	}
	
	public static Builder newBuilder() { return new Builder(); }
	public static class Builder {
		private ActivationFunction func = null;
		private int numInputs = 0;
		private int numNodes = 0;
		
		private Builder() {}
		
		public Builder setActivationFunction(ActivationFunction func) {
			if (func == null) {
				throw new NullPointerException();
			}
			this.func = func;
			return this;
		}
		
		public Builder setNumInputs(int numInputs) {
			if (numInputs <= 0) {
				throw new IllegalArgumentException(
						"Must have at least one input in fully connected layer.");
			}
			this.numInputs = numInputs;
			return this;
		}
		
		public Builder setNumNodes(int numNodes) {
			if (numNodes <= 0) {
				throw new IllegalArgumentException(
						"Must have at least one node in fully connected layer.");
			}
			this.numNodes = numNodes;
			return this;
		}
		
		public FullyConnectedLayer build() {
			if (numInputs <= 0 || numNodes <= 0 || func == null) {
				throw new IllegalStateException(
						"One of node count, input count, and activation function not specified.");
			}
			double[][] weights = new double[numNodes][numInputs];
			for (int i = 0; i < weights.length; i++) {
				for (int j = 0; j < weights[i].length; j++) {
					weights[i][j] = Util.RNG.nextGaussian();
				}
			}
			return new FullyConnectedLayer(weights, func);
		}
	}
}

package neuropingpong;

import static neuropingpong.Helpers.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an artificial neuron.
 */
class Neuron {
  private final List<Double> weights;
  private final double bias;
  private double output = 0;

  Neuron(double bias, List<Double> weights) {
    this.bias = bias;
    this.weights = new ArrayList<>(weights);
  }

  void activate(List<Double> inputs) {
    checkArgument(inputs.size() == weights.size());
    var sum = bias;
    for (int i = 0; i < weights.size(); i++) {
      sum += inputs.get(i) * weights.get(i);
    }
    output = Math.tanh(sum);
  }

  double getOutput() {
    return output;
  }

  double getBias() {
    return bias;
  }

  List<Double> getWeights() {
    return new ArrayList<>(weights);
  }
}

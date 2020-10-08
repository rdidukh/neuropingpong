package neuropingpong;

import static neuropingpong.Helpers.checkArgument;
import static neuropingpong.Helpers.randomDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents an artificial neuron.
 */
class Neuron {
  private final List<Double> weights;
  private double bias;
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

  /**
   * Mutates the neuron by modifying the bias or one of the weights.
   *
   * @param random    a random number generator.
   * @param magnitude specifies the maximum change of a weight or the bias.
   */
  void mutate(Random random, double magnitude) {
    double delta = randomDouble(random, -magnitude, magnitude);
    if (random.nextBoolean()) {
      bias += delta;
    } else {
      var w = random.nextInt(weights.size());
      weights.set(w, weights.get(w) + delta);
    }
  }

  Neuron copy() {
    return new Neuron(bias, weights);
  }

  @Override
  public boolean equals(Object that) {
    if (that == this) {
      return true;
    }

    if (that instanceof Neuron) {
      Neuron neuron = (Neuron) that;
      return neuron.bias == bias && neuron.weights.equals(weights);
    }

    return false;
  }

  @Override
  public String toString() {
    return String.format("Neuron{bias: %f, weights: [%s]}", bias,
        weights.stream().map(String::valueOf)
            .collect(Collectors.joining(", ")));
  }
}

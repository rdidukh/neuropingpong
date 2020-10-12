package neuropingpong;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


class Layer implements Cloneable {
  private final List<Neuron> neurons;

  Layer(List<Neuron> neurons) {
    this.neurons =
        neurons.stream().map(Neuron::copy).collect(Collectors.toList());
  }

  void activate(List<Double> inputs) {
    for (Neuron neuron : neurons) {
      neuron.activate(inputs);
    }
  }

  List<Double> getOutputs() {
    return neurons.stream().map(Neuron::getOutput).collect(Collectors.toList());
  }

  Layer copy() {
    return new Layer(neurons);
  }

  List<Neuron> getNeurons() {
    return neurons.stream().map(Neuron::copy).collect(Collectors.toList());
  }

  void mutate(Random random, double magnitude) {
    var n = random.nextInt(neurons.size());
    neurons.get(n).mutate(random, magnitude);
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (that instanceof Layer) {
      Layer layer = (Layer) that;
      return neurons.equals(layer.neurons);
    }

    return false;
  }
}

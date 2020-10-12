package neuropingpong;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NeuralNetwork {

  private final List<Layer> layers;

  NeuralNetwork(List<Layer> layers) {
    this.layers = layers.stream().map(Layer::copy).collect(Collectors.toList());
  }

  List<Double> getOutputs() {
    return List.copyOf(layers.get(layers.size() - 1).getOutputs());
  }

  void activate(List<Double> inputs) {
    if (layers.isEmpty()) {
      return;
    }

    layers.get(0).activate(inputs);
    for (int i = 1; i < layers.size(); i++) {
      layers.get(i).activate(layers.get(i - 1).getOutputs());
    }
  }

  List<Layer> getLayers() {
    return layers.stream().map(Layer::copy).collect(Collectors.toList());
  }

  NeuralNetwork copy() {
    return new NeuralNetwork(layers);
  }

  void mutate(Random random, double magnitude) {
    int n = random.nextInt(layers.size());
    layers.get(n).mutate(random, magnitude);
  }
}

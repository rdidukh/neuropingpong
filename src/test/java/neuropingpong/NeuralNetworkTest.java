package neuropingpong;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class NeuralNetworkTest {
  private static List<Layer> LAYERS =
      asList(layer(neuron(0.1, -0.1, 0.1, 0.2), neuron(0.2, 0.1, -0.2, 0.4)),
          layer(neuron(0.3, -0.2, 0.3), neuron(0.4, 0.3, -0.4)),
          layer(neuron(0.5, -0.4, -0.5), neuron(0.6, 0.5, 0.6),
              neuron(0.7, -0.6, -0.7)));


  @Test
  void activate() {
    NeuralNetwork neuralNetwork = new NeuralNetwork(LAYERS);
    neuralNetwork.activate(asList(0.5, -0.3, 0.1));

    assertOutputsEqual(neuralNetwork, 0.2119, 0.7394, 0.2787);
  }

  @Test
  void mutate() {
    NeuralNetwork neuralNetwork = new NeuralNetwork(LAYERS);
    Random mockRandom = spy(Random.class);
    when(mockRandom.nextInt(3)).thenReturn(1);
    neuralNetwork.mutate(mockRandom, 0.5);
    var layers = neuralNetwork.getLayers();

    assertEquals(LAYERS.get(0), layers.get(0));
    assertNotEquals(LAYERS.get(1), layers.get(1));
    assertEquals(LAYERS.get(2), layers.get(2));
  }

  @Test
  void copy() {
    NeuralNetwork originalNetwork = new NeuralNetwork(LAYERS);
    NeuralNetwork neuralNetwork = originalNetwork.copy();

    assertIterableEquals(LAYERS, neuralNetwork.getLayers());
  }

  private void assertOutputsEqual(NeuralNetwork network,
      double... expectedOutputs) {
    assertArrayEquals(expectedOutputs,
        network.getOutputs().stream().mapToDouble(Double::doubleValue)
            .toArray(), 0.00005);
  }

  private static Layer layer(Neuron... neurons) {
    return new Layer(Arrays.asList(neurons));
  }

  private static Neuron neuron(double bias, double... weights) {
    return new Neuron(bias,
        Arrays.stream(weights).boxed().collect(Collectors.toList()));
  }
}

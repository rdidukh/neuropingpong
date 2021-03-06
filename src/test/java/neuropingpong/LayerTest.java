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

class LayerTest {

  private static final List<Neuron> NEURONS =
      asList(new Neuron(0.1, asList(0.75, -0.5)),
          new Neuron(-0.2, asList(0.1, 0.2)),
          new Neuron(0.3, asList(-0.3, 0.4)));

  @Test
  void activate() {
    Layer layer = new Layer(NEURONS);
    layer.activate(asList(2.0, 4.0));

    assertOutputsEqual(layer, -0.3799, 0.664, 0.8617);
  }

  @Test
  void copy() {
    var originalLayer = new Layer(NEURONS);
    var layer = originalLayer.copy();

    assertIterableEquals(NEURONS, layer.getNeurons());
  }

  @Test
  void mutate() {
    var layer = new Layer(NEURONS);
    Random mockRandom = spy(Random.class);
    when(mockRandom.nextInt(3)).thenReturn(1);
    layer.mutate(mockRandom, 0.5);
    var neurons = layer.getNeurons();
    assertEquals(NEURONS.get(0), neurons.get(0));
    assertNotEquals(NEURONS.get(1), neurons.get(1));
  }

  @Test
  void layerEquals() {
    Layer layer =
        newLayer(neuron(3.2, 1.1, -2.2, 3.3), neuron(0.4, -4.4, 5.5, 6.6));
    assertEquals(layer,
        newLayer(neuron(3.2, 1.1, -2.2, 3.3), neuron(0.4, -4.4, 5.5, 6.6)));
    assertNotEquals(layer,
        newLayer(neuron(3.2, 1.1, -2.2, 3.3), neuron(0.5, -4.4, 5.5, 6.6)));
    assertNotEquals(layer,
        newLayer(neuron(3.2, 1.1, -2.1, 3.3), neuron(0.4, -4.4, 5.5, 6.6)));
    assertNotEquals(layer, newLayer(neuron(3.2, 1.1, -2.2, 3.3)));
  }

  private void assertOutputsEqual(Layer layer, double... expectedOutputs) {
    assertArrayEquals(expectedOutputs,
        layer.getOutputs().stream().mapToDouble(Double::doubleValue).toArray(),
        0.0001);
  }

  static Neuron neuron(double bias, double... weights) {
    return new Neuron(bias,
        Arrays.stream(weights).boxed().collect(Collectors.toList()));
  }

  private static Layer newLayer(Neuron... neurons) {
    return new Layer(Arrays.asList(neurons));
  }
}

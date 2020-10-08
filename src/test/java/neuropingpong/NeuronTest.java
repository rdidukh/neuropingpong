package neuropingpong;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class NeuronTest {

  @Test
  void getBias() {
    var neuron = new Neuron(23.4, Collections.emptyList());
    assertEquals(23.4, neuron.getBias(), 0.0001);
  }

  @Test
  void getWeights() {
    var weights = asList(23.4, -56.7, 12.42);
    var neuron = new Neuron(0, weights);
    assertArrayEquals(new double[]{23.4, -56.7, 12.42},
        neuron.getWeights().stream().mapToDouble(Double::doubleValue).toArray(),
        0.0001);
    assertNotSame(weights, neuron.getWeights());
  }

  @Test
  void activate_smallPositive() {
    var weights = asList(2.0, -3.0, 1.0);
    var neuron = new Neuron(-2.0, weights);

    neuron.activate(asList(2.5, -0.5, -3.5));
    assertEquals(0.7615, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_zero_returnsZero() {
    var weights = asList(2.0, -3.0, 1.0);
    var neuron = new Neuron(-3, weights);

    neuron.activate(asList(2.5, -0.5, -3.5));
    assertEquals(0, neuron.getOutput(), 0.00001);
  }

  @Test
  void activate_bigPositive_returnsOne() {
    var weights = asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    neuron.activate(asList(2000.0, 7000.0, 15000.0));
    assertEquals(1, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_bigNegative_returnsMinusOne() {
    var weights = asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    neuron.activate(asList(-2000.0, -7000.0, -15000.0));
    assertEquals(-1, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_throwsOnMissingOrExtraInputs() {
    var weights = asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    assertThrows(IllegalArgumentException.class,
        () -> neuron.activate(asList(1.0, 2.0)));
    assertThrows(IllegalArgumentException.class,
        () -> neuron.activate(asList(1.0, 2.0, 3.0, 4.0)));
  }

  @Test
  void mutate_bias() {
    var weights = asList(1.0, 1.0, 1.0);
    var neuron = new Neuron(1.0, weights);

    Random mockRandom = mock(Random.class);
    when(mockRandom.nextBoolean()).thenReturn(true);
    when(mockRandom.nextInt(3)).thenReturn(2);
    when(mockRandom.nextDouble()).thenReturn(0.37);

    neuron.mutate(mockRandom, 0.15);

    assertEquals(0.961, neuron.getBias(), 0.001);
    assertArrayEquals(new double[]{1.0, 1.0, 1.0}, toArray(neuron.getWeights()),
        0.001);
  }

  @Test
  void mutate_weight() {
    var weights = asList(1.0, 1.0, 1.0);
    var neuron = new Neuron(1.0, weights);

    Random mockRandom = mock(Random.class);
    when(mockRandom.nextBoolean()).thenReturn(false);
    when(mockRandom.nextInt(3)).thenReturn(2);
    when(mockRandom.nextDouble()).thenReturn(0.37);

    neuron.mutate(mockRandom, 0.15);

    assertEquals(1.0, neuron.getBias());
    assertArrayEquals(new double[]{1.0, 1.0, 0.961},
        toArray(neuron.getWeights()), 0.001);
  }

  @Test
  void copy() {
    var weights = asList(1.1, 2.2, 3.3);
    var oldNeuron = new Neuron(4.4, weights);

    var neuron = oldNeuron.copy();

    assertEquals(4.4, neuron.getBias(), 0.0001);
    assertArrayEquals(new double[]{1.1, 2.2, 3.3}, toArray(neuron.getWeights()),
        0.0001);
  }

  @Test
  void neuronEquals() {
    Neuron neuron = new Neuron(3.2, asList(1.2, 3.4, 5.6));
    assertEquals(neuron, new Neuron(3.2, asList(1.2, 3.4, 5.6)));
    assertNotEquals(neuron, new Neuron(3.1, asList(1.2, 3.4, 5.6)));
    assertNotEquals(neuron, new Neuron(3.2, asList(1.2, 3.3, 5.6)));
  }

  @Test
  void neuronToString() {
    Neuron neuron = new Neuron(3.2, asList(1.0, 3.40, 5.626));
    assertEquals("Neuron{bias: 3.20, weights: [1.00, 3.40, 5.63]}",
        neuron.toString());
  }

  private static double[] toArray(List<Double> list) {
    return list.stream().mapToDouble(Double::doubleValue).toArray();
  }
}

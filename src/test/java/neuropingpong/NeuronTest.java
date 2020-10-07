package neuropingpong;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class NeuronTest {

  @Test
  void getBias() {
    var neuron = new Neuron(23.4, Collections.emptyList());
    assertEquals(23.4, neuron.getBias(), 0.0001);
  }

  @Test
  void getWeights() {
    var weights = Arrays.asList(23.4, -56.7, 12.42);
    var neuron = new Neuron(0, weights);
    assertArrayEquals(new double[]{23.4, -56.7, 12.42},
        neuron.getWeights().stream().mapToDouble(Double::doubleValue).toArray(),
        0.0001);
    assertNotSame(weights, neuron.getWeights());
  }

  @Test
  void activate_smallPositive() {
    var weights = Arrays.asList(2.0, -3.0, 1.0);
    var neuron = new Neuron(-2.0, weights);

    neuron.activate(Arrays.asList(2.5, -0.5, -3.5));
    assertEquals(0.7615, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_zero_returnsZero() {
    var weights = Arrays.asList(2.0, -3.0, 1.0);
    var neuron = new Neuron(-3, weights);

    neuron.activate(Arrays.asList(2.5, -0.5, -3.5));
    assertEquals(0, neuron.getOutput(), 0.00001);
  }

  @Test
  void activate_bigPositive_returnsOne() {
    var weights = Arrays.asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    neuron.activate(Arrays.asList(2000.0, 7000.0, 15000.0));
    assertEquals(1, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_bigNegative_returnsMinusOne() {
    var weights = Arrays.asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    neuron.activate(Arrays.asList(-2000.0, -7000.0, -15000.0));
    assertEquals(-1, neuron.getOutput(), 0.0001);
  }

  @Test
  void activate_throwsOnMissingOrExtraInputs() {
    var weights = Arrays.asList(2000.0, 3000.0, 1000.0);
    var neuron = new Neuron(-20000, weights);

    assertThrows(IllegalArgumentException.class,
        () -> neuron.activate(Arrays.asList(1.0, 2.0)));
    assertThrows(IllegalArgumentException.class,
        () -> neuron.activate(Arrays.asList(1.0, 2.0, 3.0, 4.0)));
  }
}

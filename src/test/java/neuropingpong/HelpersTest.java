package neuropingpong;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import org.junit.jupiter.api.Test;

class HelpersTest {

  @Test
  void checkArgument_false_throws() {
    assertThrows(IllegalArgumentException.class,
        () -> Helpers.checkArgument(false));
  }

  @Test
  void checkArgument_true_returns() {
    assertDoesNotThrow(() -> Helpers.checkArgument(true));
  }

  @Test
  void randomDouble() {
    Random mockRandom = mock(Random.class);
    when(mockRandom.nextDouble()).thenReturn(0.7);
    double value = Helpers.randomDouble(mockRandom, 1, 10);
    assertEquals(7.3, value, 0.001);
  }

  @Test
  void randomDouble_swappedMinMax() {
    Random mockRandom = mock(Random.class);
    when(mockRandom.nextDouble()).thenReturn(0.125);
    double value = Helpers.randomDouble(mockRandom, 4, -4);
    assertEquals(3.0, value, 0.001);
  }
}

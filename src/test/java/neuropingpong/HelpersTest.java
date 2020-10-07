package neuropingpong;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}

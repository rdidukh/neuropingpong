package neuropingpong;

import java.util.Random;

class Helpers {
  static void checkArgument(boolean condition) {
    if (!condition) {
      throw new IllegalArgumentException();
    }
  }

  static double randomDouble(Random random, double minValue, double maxValue) {
    return minValue + (maxValue - minValue) * random.nextDouble();
  }
}

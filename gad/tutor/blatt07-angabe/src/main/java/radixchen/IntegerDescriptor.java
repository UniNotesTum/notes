package radixchen;

public class IntegerDescriptor implements KeyDescriptor<Integer> {

    private final static int INTEGER_BUCKETS = 10;

  @Override public int buckets () {

      return INTEGER_BUCKETS;

  }

  @Override public int digits () {

      int max = Integer.MAX_VALUE;

      int count = 0;

      while (max > 0) {
          max /= 10;
          count++;
      }

      return count;

  }

  @Override public int key (Integer element, int digit) {

      if (element < 0) throw new RuntimeException("Negative element");

      for (int i = 0; i < digit; i++) {
          element /= 10;
          if (element == 0) return 0;
      }

      int d = element % 10;

      return d;

  }

}

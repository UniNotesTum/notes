package radixchen;

public class StringDescriptor implements KeyDescriptor<String> {

    private String[] elements;
    private int buckets;
    private int digits;

    public StringDescriptor(String[] elements) {
        this.elements = elements;

        buckets = ('z' - 'a') + ('Z' - 'A') + 12;

        int max = 0;

        for (String element : elements) {
            if (element.length() > max) max = element.length();
        }

        digits = max;


    }
  
  @Override public int buckets () {
    return buckets;
  }

  @Override public int digits () {
    return digits;
  }

  @Override public int key (String element, int digit) {

      digit = digits - digit - 1;

      if (digit >= element.length()) digit = element.length() - 1;

      char c = element.charAt(digit);

      if (!Character.isDigit(c) && !Character.isAlphabetic(c)) throw new RuntimeException(c + " is not from the correct range!");

      int k;

      if (Character.isDigit(c)) k = c - '0';
      else if (Character.isUpperCase(c)) k = (c - 'A') + ('9' - '0') + 1;
      else k = (c - 'a') + ('Z' - 'A') + ('9' - '0') + 2;

      return k;

  }

}

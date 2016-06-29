import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Die Klasse {@link HashString} kann dazu verwendet werden,
 * Strings zu hashen.
 */
public class HashString {

  private final static int CHARSIZE = 16;

  private List<Integer> a = new ArrayList<>();

  private final int w, size;

  private final static Random random = new Random();

  /**
   * Dieser Konstruktor initialisiert ein {@link HashString}
   * Objekt für einen gegebenen Maximalwert (size - 1) der gehashten
   * Werte.
   * 
   * @param size die Größe der Hashtabelle
   */
  public HashString (int size) {

    // maybe I should assert that the size is a prime number

    this.size = size;

    w = (int) (Math.log(size)/Math.log(2));

  }

  /**
   * Diese Methode berechnet den Hashwert für einen String.
   * 
   * @param key der Schlüssel, der gehasht werden sollen
   * @return der Hashwert des Schlüssels
   */
  public synchronized int hash (String key) {

    int k = key.length() * CHARSIZE / w;

    for (int i = a.size(); i < k; i++) {

      a.add(random.nextInt(size));

    }

    byte[] bytes = key.getBytes();

    int[] x = new int[k];

    int j = 0;

    for (int i = 0; i < bytes.length; i += w) {

      int shift = 0;

      for (int q = i + w - 1; q >= i; q--) {

        if (q >= bytes.length) break;

        x[j] += (bytes[q] << shift) % (1 << w);

        shift += 8;

      }

      j++;

    }

    // j is now where the result will be stored
    j = 0;

    for (int i = 0; i < k; i++) {

      j += a.get(i) * x[i];

    }

    return j % size;

  }

}

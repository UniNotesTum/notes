import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

/**
 * Die Klasse {@link DoubleHashString} kann dazu verwendet werden,
 * Strings zu hashen.
 */
public class DoubleHashString implements DoubleHashable<String> {

    private static int CHARSIZE = 8;

    private List<Integer> a1;
    private List<Integer> a2;

    private int w;
    private int size;

    private Random random = new Random();

  /**
   * Dieser Konstruktor initialisiert ein {@link DoubleHashString}
   * Objekt für einen gegebenen Maximalwert (size - 1) der gehashten
   * Werte.
   * 
   * @param size die Größe der Hashtabelle
   */
  public DoubleHashString (int size) {

      this.size = size;

      a1 = new ArrayList<>();

      a2 = new ArrayList<>();

      w = (int) (Math.log(size)/Math.log(2));

      if (w < 16) w = 16;

      a1.add(random.nextInt(size));

      int k = random.nextInt(size);

      while (k == a1.get(0)) k = random.nextInt(size);

  }
  
  /**
   * Diese Methode berechnet h(key) für einen String.
   * 
   * @param key der Schlüssel, der gehasht werden soll
   * @return der Hashwert des Schlüssels
   */
    public long hash (String key) {
          return _hash(a1, key);
    }
  
  /**
   * Diese Methode berechnet h'(key) für einen String.
   * 
   * @param key der Schlüssel, der gehasht werden soll
   * @return der Hashwert des Schlüssels
   */
    public long hashTick (String key) {
        return _hash(a2, key);
    }


    private int _hash(List<Integer> a, String key) {

        int k = key.length() * CHARSIZE / w;

        for (int i = a.size(); i < k; i++) {

            a.add(random.nextInt(size));

        }

        byte[] bytes = key.getBytes();

        int[] x = new int[k];

        int j = 0;

        for (int i = 0; i < bytes.length; i+=w/8) {

            int shift = 0;

            for (int q = i; q < i + w/8; q++) {

                if (q >= bytes.length) break;
                if (j >= x.length) break;

                x[j] += (bytes[q] << shift) % (1 << w);

                shift += 8;

            }

            j++;

        }

        // j is now where the result will be stored
        j = 0;

        for (int i = 0; i < k; i++) {

            j += (a.get(i) * x[i]) % size;

        }

        return j % size;

    }
}

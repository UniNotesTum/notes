package radixchen;

import java.util.ArrayList;
import java.util.List;

public class RadixSort<K> {

    private KeyDescriptor<K> keyDescriptor;
    private int buckets;
    private int digits;

    public RadixSort(KeyDescriptor<K> keyDescriptor) {
        this.keyDescriptor = keyDescriptor;
        buckets = keyDescriptor.buckets();
        digits  = keyDescriptor.digits();
    }

  private void kSort(K[] elements, int digit) {

      List<List<K>> bucketList = new ArrayList<>(buckets);

      for (int i = 0; i < buckets; i++) {
          bucketList.add(i, new ArrayList<>());
      }

      for (int i = 0; i < elements.length; i++) {
          bucketList.get(keyDescriptor.key(elements[i], digit)).add(elements[i]);
      }

      int k = 0;

      for (List<K> list : bucketList) {

          for (K element : list) {

              elements[k++] = element;

          }

      }

  }
  
  public void sort(K[] elements) {

      for (int i = 0; i < digits; i++) {

          kSort(elements, i);

      }

  }

  /**
   * Diese Methode sortiert ein Feld mittels RadixSort.
   * 
   * @param keyDescriptor ein Objekt zur Ermittelung von Informationen über die zu sortierenden Elemente
   * @param elements die zu sortierenden Elemente
   */
  public static <K> void sort(KeyDescriptor<K> keyDescriptor, K[] elements) {
    RadixSort<K> radixSort = new RadixSort<K>(keyDescriptor);
    radixSort.sort(elements);
  }
}

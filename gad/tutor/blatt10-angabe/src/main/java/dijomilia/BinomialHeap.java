package dijomilia;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BinomialHeap<T extends Comparable<T>> {

    private List<BinomialTreeNode<T>> list;
  
  /**
   * Dieser Konstruktor baut einen leeren Haufen.
   */
  public BinomialHeap() {
    list = new LinkedList<>();
  }
  
  /**
   * Diese Methode fügt einen Wert in den Haufen ein.
   * 
   * @param value der einzufügende Wert
   * @return BinomialHeapHandle
   */
  public void insert(T value) {

      BinomialTreeNode<T> node = new BinomialTreeNode<>(value);

      merger(node, -1);

  }

    private void merger(BinomialTreeNode<T> node, int index) {

        for (int i = 0; i < list.size(); i++) {

            BinomialTreeNode<T> listNode = list.get(i);

            if (listNode.rank() == node.rank() && index != i) {

                BinomialTreeNode<T> newNode = BinomialTreeNode.merge(node, listNode);

                list.set(i, newNode);

                if (index >= 0 && index < list.size()) {
                    list.remove(index);
                    if (i > index) i--;
                }

                merger(newNode, i);

                return;

            }

        }

        if (index < 0) list.add(node);

    }
  
  /**
   * Diese Methode ermittelt das minimale Element im binomialen
   * Haufen.
   * 
   * @return das minimale Element
   */
  public T min() {

      if (list.isEmpty()) throw new RuntimeException("No elements");

      return list.get(minIndex()).min();

  }

    private int minIndex() {

        if (list.isEmpty()) throw new RuntimeException("No elements");

        int minInd = 0;

        int k = 0;

        for (BinomialTreeNode node : list) {

            if (node.min().compareTo(list.get(minInd).min()) < 0) minInd = k;

            k++;

        }

        return minInd;

    }
  
  /**
   * Diese Methode entfernt das minimale Element aus dem binomialen
   * Haufen und gibt es zurück.
   * 
   * @return das minimale Element
   */
  public T deleteMin() {

      int ind = minIndex();

      T min = list.get(ind).min();

      BinomialTreeNode<T>[] arr = list.get(ind).deleteMin();

      list.remove(ind);

      if (arr == null) return min;

      List<BinomialTreeNode<T>> newList = Arrays.asList(arr);

      for (BinomialTreeNode<T> node : newList) {

          merger(node, -1);

      }

      return min;

  }

}

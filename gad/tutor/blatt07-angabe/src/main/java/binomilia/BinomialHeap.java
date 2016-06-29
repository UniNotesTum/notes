package binomilia;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BinomialHeap {

    private List<BinomialTreeNode> list;
  
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
   */
  public void insert(int value) {

      BinomialTreeNode node = new BinomialTreeNode(value);

      merger(node, -1);

  }

    private void merger(BinomialTreeNode node, int index) {

        for (int i = 0; i < list.size(); i++) {

            BinomialTreeNode listNode = list.get(i);

            if (listNode.rank() == node.rank() && index != i) {

                BinomialTreeNode newNode = BinomialTreeNode.merge(node, listNode);

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
  public int min() {

      if (list.isEmpty()) throw new RuntimeException("No elements");

      return list.get(minIndex()).min();

  }

    private int minIndex() {

        if (list.isEmpty()) throw new RuntimeException("No elements");

        int minInd = 0;

        int k = 0;

        for (BinomialTreeNode node : list) {

            if (node.min() < list.get(minInd).min()) minInd = k;

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
  public int deleteMin() {

      int ind = minIndex();

      int min = list.get(ind).min();

      BinomialTreeNode[] arr = list.get(ind).deleteMin();

      list.remove(ind);

      if (arr == null) return min;

      List<BinomialTreeNode> newList = Arrays.asList(arr);

      for (BinomialTreeNode node : newList) {

          merger(node, -1);

      }

      return min;

  }

}

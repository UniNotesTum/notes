package dijomilia;

import java.util.LinkedList;
import java.util.List;

public class BinomialTreeNode<T extends Comparable<T>> {

    private Pair<T, List<BinomialTreeNode>> root;

    public BinomialTreeNode(T element) {
        root = new Pair<>(element, new LinkedList<>());
    }

    /**
     * Ermittelt das minimale Element im Teilbaum.
     *
     * @return das minimale Element
     */
    public T min() {
        return root._1;
    }

    public Pair<T, List<BinomialTreeNode>> getRoot() {
        return root;
    }

    /**
     * Gibt den Rang des Teilbaumes zurück.
     *
     * @return der Rang des Teilbaumes
     */
    public int rank() {

        if (root == null)
            return 0;

        if (root._2 == null) return 0;

        return root._2.size();
    }

    /**
     * Gibt eine Menge von Teilbäumen zurück, in die der
     * aktuelle Baum zerfällt, wenn man den Knoten des minimalen
     * Elements entfernt.
     *
     * @return die Menge von Teilbäumen
     */
    public BinomialTreeNode[] deleteMin() {
        BinomialTreeNode[] nodes = null;
        if (!root._2.isEmpty()) {
            List<BinomialTreeNode> binNodes = root._2;
            nodes = new BinomialTreeNode[binNodes.size()];
            for (int i = 0; i < binNodes.size(); i++) {

                nodes[i] = binNodes.get(i);

            }
        }
        root = null;
        return nodes;
    }

    /**
     * Diese Methode vereint zwei Bäume des gleichen Ranges.
     *
     * @param a der erste Baum
     * @param b der zweite Baum
     * @return denjenigen der beiden Bäume, an den der andere angehängt wurde
     */
    public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {

        if (a.min().compareTo(b.min()) > 0) {
            BinomialTreeNode temp = a;
            a = b;
            b = temp;
        }

        cast(a.root._2).add(b);
        return a;

    }


    @SuppressWarnings("unchecked")
    public static <T extends List<BinomialTreeNode>> T cast(Object obj) {
        return (T) obj;
    }
}
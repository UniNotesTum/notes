import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Diese Klasse implementiert einen AVL-Baum.
 */
public class AVLTree {
  /**
   * Diese innere Klasse repräsentiert einen Knoten des AVL-Baumes.
   */
  private static class AVLTreeNode {
    /**
     * Diese Variable enthält den Schlüssel, den der Knoten speichert.
     */
    private int key;

    /**
     * Diese Variable speichert die Balancierung des Knotens - wie in der
     * Vorlesung erklärt - ab. Ein Wert von -1 bedeutet, dass der linke Teilbaum
     * um eins höher ist als der rechte Teilbaum. Ein Wert von 0 bedeutet, dass
     * die beiden Teilbäume gleich hoch sind. Ein Wert von 1 bedeutet, dass der
     * rechte Teilbaum höher ist.
     */
    private int balance = 0;

      private Optional<AVLTreeNode> parent = Optional.empty();
      private boolean isRightSide = false;

    /**
     * Diese Variable speichert den linken Teilbaum.
     */
    private Optional<AVLTreeNode> left = Optional.empty();

    public Optional<AVLTreeNode> getLeft () {
      return left;
    }

    public void setLeft (Optional<AVLTreeNode> left) {
      this.left = left;
    }

    /**
     * Diese Variable speichert den rechten Teilbaum.
     */
    private Optional<AVLTreeNode> right = Optional.empty();

    public Optional<AVLTreeNode> getRight () {
      return right;
    }

    public void setRight (Optional<AVLTreeNode> right) {
      this.right = right;
    }


    private void rebalance (Consumer<AVLTreeNode> replaceChild) {

        if (parent.isPresent()) replaceChild.accept(parent.get());
        else replaceChild.accept(null);

    }


    public AVLTreeNode (int key) {
      this.key = key;
    }

    public static Optional<AVLTreeNode> optCtor (int key) {
      return Optional.of(new AVLTreeNode(key));
    }

    /**
     * Diese Methode ermittelt die Höhe des Teilbaums unter diesem Knoten.
     * 
     * @return die ermittelte Höhe
     */
    public int height () {
        return height(Optional.of(this));
    }

      private int height(Optional<AVLTreeNode> node) {
          if (!node.isPresent()) return -1;
          return max(height(node.get().left), height(node.get().right)) + 1;
      }

      private static int max(int a, int b) {
          return a > b ? a : b;
      }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     * 
     * @param sb der StringBuilder für die Ausgabe
     * @param from der Index des nächsten Knotens
     * @return der nächste freie Index
     */
    int dot (StringBuilder sb, int from) {
      int mine = from;
      sb.append('\t').append(mine).append(" [label=\"").append(key)
          .append(", b = ").append(balance).append("\"];\n");
      Mutable<Integer> fromWrapped = new Mutable<Integer>(from);
      BiConsumer<Optional<AVLTreeNode>, String> forNode = (node, label) -> {
        if (node.isPresent()) {
          int leftInd = fromWrapped.get() + 1;
          sb.append('\t').append(mine).append(" -> ").append(leftInd)
              .append(" [label=").append(label).append("];\n");
          fromWrapped.set(node.get().dot(sb, leftInd));
        }
      };
      forNode.accept(left, "l");
      forNode.accept(right, "r");
      return fromWrapped.get();
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     * 
     * @param sb der StringBuilder für die Ausgabe
     */
    void dot (StringBuilder sb) {
      dot(sb, 0);
    }
  }

  /**
   * Diese Variable speichert die Wurzel des Baumes.
   */
  Optional<AVLTreeNode> root = Optional.empty();

  public AVLTree () {
  }

  /**
   * Diese Methode ist zum Debuggen gedacht und prüft, ob es sich
   * um einen validen AVL-Baum handelt. Dabei werden die folgenden Eigenschaften
   * geprüft:
   * 
   * - Die Höhe des linken Teilbaumes eines Knotens unterscheidet sich von der
   * Höhe des rechten Teilbaumes um höchstens eins.
   * - Die Schlüssel im linken Teilbaum eines Knotens sind kleiner als der oder
   * gleich dem Schlüssel des Knotens.
   * - Die Schlüssel im rechten Teilbaum eines Knotens sind größer als der Schlüssel des Knotens.
   * - Die Balancierung jedes Knoten entspricht der Höhendifferenz der Teilbäume
   * entsprechend der Erklärung in der Vorlesung.
   * 
   * @return 'true' falls der Baum ein valider AVL-Baum ist, 'false' sonst
   */
  public boolean validAVL () {
      return testHeight(root) && testOrder(root) && testBalance(root);
  }

    private static boolean testHeight(Optional<AVLTreeNode> node) {

        if (!node.isPresent()) return true;

        AVLTreeNode treeNode = node.get();

        int heightL = 0, heightR = 0;

        if (treeNode.left.isPresent()) heightL = treeNode.left.get().height();
        if (treeNode.right.isPresent()) heightR = treeNode.right.get().height();

        if (Math.abs(heightL - heightR) > 1) return false;

        if (!testHeight(treeNode.left) || !testHeight(treeNode.right)) return false;

        return true;

    }

    private static boolean testOrder(Optional<AVLTreeNode> node) {

        if (!node.isPresent()) return true;

        AVLTreeNode treeNode = node.get();

        if (treeNode.left.isPresent() && treeNode.left.get().key > treeNode.key) return false;
        if (treeNode.right.isPresent() && treeNode.right.get().key < treeNode.key) return false;

        if (!testOrder(treeNode.left) || !testOrder(treeNode.right)) return false;

        return true;

    }

    private static boolean testBalance(Optional<AVLTreeNode> node) {

        if (!node.isPresent()) return true;

        AVLTreeNode treeNode = node.get();

        int heightL = 0, heightR = 0;

        if (treeNode.left.isPresent()) heightL = treeNode.left.get().height();
        if (treeNode.right.isPresent()) heightR = treeNode.right.get().height();

        if (treeNode.balance != heightR - heightL) return false;

        testBalance(treeNode.left);
        testBalance(treeNode.right);

        return true;

    }

  /**
   * Diese Methode fügt einen neuen Schlüssel in den AVL-Baum ein.
   * 
   * @param key der einzufügende Schlüssel
   */
  public void insert (int key) {
      if (!root.isPresent()) root = AVLTreeNode.optCtor(key);
      else insert(root, key);
  }

    private void pleaseBalance(Optional<AVLTreeNode> node) {

        AVLTreeNode node1 = node.get();

        AVLTreeNode parent = null;

        if (node1.parent.isPresent()) parent = node1.parent.get();

        if (node1.balance > 1) {

            if (node1.right.get().balance < 0) {
                node1.right = rotateRight(node1.right.get());
            }

            if (parent == null) root = rotateLeft(node1);
            else {

                if (node1.isRightSide)
                    parent.right = rotateLeft(node1);
                else
                    parent.left = rotateLeft(node1);


            }

        } else {

            if (node1.left.get().balance > 0) {
                node1.left = rotateLeft(node1.left.get());
            }

            if (parent == null) root = rotateRight(node1);
            else {

                if (node1.isRightSide)
                    parent.right = rotateRight(node1);
                else
                    parent.left = rotateRight(node1);

            }

        }

        if (parent == null)
            updateBalance(root);
        else updateBalance(Optional.of(parent));

    }

    private void updBal(boolean isRight, Optional<AVLTreeNode> node) {

        AVLTreeNode treeNode = node.get();

        while (true) {
            if (isRight) treeNode.balance++;
            else treeNode.balance--;

            if (Math.abs(treeNode.balance) > 1) {
                pleaseBalance(node);
                return;
            }

            isRight = treeNode.isRightSide;
            if (!treeNode.parent.isPresent()) return;
            if (treeNode.balance == 0) return;
            node = treeNode.parent;
            treeNode = node.get();
        }
    }

    private void insert(Optional<AVLTreeNode> node, int key) {

        AVLTreeNode treeNode = node.get();

        if (treeNode.key > key) {

            boolean wasPresent = treeNode.left.isPresent();

            if (!wasPresent) treeNode.left = AVLTreeNode.optCtor(key);
            else insert(treeNode.left, key);

            if (!wasPresent) {
                //treeNode.balance--;
                treeNode.left.get().parent = node;

                updBal(false, node);
            }

        }
        else {

            boolean wasPresent = treeNode.right.isPresent();

            if (!wasPresent) treeNode.right = AVLTreeNode.optCtor(key);
            else insert(treeNode.right, key);

            if (!wasPresent) {
                //treeNode.balance++;
                treeNode.right.get().parent = node;
                treeNode.right.get().isRightSide = true;

                updBal(true, node);
            }

        }



    }

    private void updateBalance(Optional<AVLTreeNode> node) {

        if (!node.isPresent()) return;

        AVLTreeNode treeNode = node.get();

        int heightL = -1, heightR = -1;

        if (treeNode.left.isPresent()) heightL = treeNode.left.get().height();
        if (treeNode.right.isPresent()) heightR = treeNode.right.get().height();

        treeNode.balance = heightR - heightL;

        updateBalance(treeNode.left);
        updateBalance(treeNode.right);

        if (Math.abs(treeNode.balance) > 1) {
            pleaseBalance(node);
        }

    }

    private static Optional<AVLTreeNode> rotateLeft(AVLTreeNode node) {

        Optional<AVLTreeNode> temp = node.parent;

        node.parent = node.right;

        if (node.right.get().left.isPresent()) {
            node.right.get().left.get().parent = Optional.of(node);
        }

        node.right.get().parent = temp;

        temp = node.right;

        node.right = node.right.get().left;

        if (node.right.isPresent()) node.right.get().isRightSide = true;

        temp.get().left = Optional.of(node);

        temp.get().left.get().isRightSide = false;

        return temp;

    }

    private static Optional<AVLTreeNode> rotateRight(AVLTreeNode node) {

        Optional<AVLTreeNode> temp = node.parent;

        node.parent = node.left;

        if (node.left.get().right.isPresent()) {
            node.left.get().right.get().parent = Optional.of(node);
        }

        node.left.get().parent = temp;

        temp = node.left;

        node.left = node.left.get().right;

        if (node.left.isPresent()) node.left.get().isRightSide = false;

        temp.get().right = Optional.of(node);

        temp.get().right.get().isRightSide = true;

        return temp;

    }


  /**
   * Diese Methode löscht einen Schlüssel aus dem AVL-Baum.
   * 
   * @param key der zu löschende Schlüssel
   * @return 'true' falls der Schlüssel gefunden und gelöscht wurde, 'false' sonst
   */
  public boolean remove (int key) {
    return true;
  }

  /**
   * Diese Methode sucht einen Schlüssel im AVL-Baum.
   * 
   * @param key der Schlüssel, der gesucht werden soll
   * @return 'true', falls der Schlüssel gefunden wurde, 'false' sonst
   */
  public boolean find (int key) {
      return find(root, key);
  }

    private boolean find(Optional<AVLTreeNode> node, int key) {

        if (!node.isPresent()) return false;

        AVLTreeNode treeNode = node.get();

        if (treeNode.key == key) {
            return true;
        }

        if (treeNode.key > key) return find(treeNode.left, key);
        else return find(treeNode.right, key);

    }

  /**
   * Diese Methode wandelt den Baum in das Graphviz-Format um.
   * 
   * @return der Baum im Graphiz-Format
   */
  String dot () {
    StringBuilder sb = new StringBuilder();
    sb.append("digraph {\n");
    if (root.isPresent())
      root.get().dot(sb);
    sb.append("}");
    return sb.toString();
  }

  /**
   * Diese Methode wandelt den Baum in das Graphviz-Format um.
   * 
   * @return der Baum im Graphiz-Format
   */
  @Override public String toString () {
    return dot();
  }

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();

        Random random = new Random();

        for (int i = 40; i > 0; i--) {
            tree.insert(random.nextInt(200));
        }

        System.out.println(tree);
        System.out.println(tree.validAVL());


    }

}

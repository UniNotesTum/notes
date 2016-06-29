import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Diese Klasse implementiert einen (a,b)-Baum.
 */
public class ABTree {
  /**
   * Diese Variable speichert die untere Schranke des Knotengrades.
   */
  private int a;

  /**
   * Diese Variable speichert die obere Schranke des Knotengrades.
   */
  private int b;

  /**
   * Diese Klasse repräsentiert einen Knoten des Baumes. Es kann sich
   * dabei um einen inneren Knoten oder ein Blatt handeln.
   */
  public abstract class ABTreeNode {

    Pair[] pairs;

    ABTreeNode parent = null;

    //how many elements
    int count = 1;

    ABTreeNode(int n, ABTreeNode parent) {

      //first element is a phony element
      //pointer attached to a pairs points to the right
      n++;
      pairs = new Pair[n];
      this.parent = parent;

      if (!(this instanceof ABTreeLeaf))
        pairs[0] = new Pair(Integer.MIN_VALUE, new ABTreeLeaf(Integer.MIN_VALUE, this));
      else
        pairs[0] = new Pair(Integer.MIN_VALUE);

    }

    //binary search, if not found return -1
    int find(Pair elem) {

      int index = findPlace(elem);

      if (pairs[index].equals(elem)) return index;
      else return -1;

    }

    //returns the place the the element is supposed to be
    int findPlace(Pair elem) {

      int left = 0;

      int right = count - 1;

      int middle = left + (right - left) / 2;

      while (right >= left) {

        if (elem.compareTo(pairs[middle]) > 0) left = middle + 1;
        else if (elem.compareTo(pairs[middle]) < 0) right = middle - 1;
        else return middle;

        middle = (left + right) / 2;

      }

      return middle;

    }

    void putIntoList(int index, int value) {

      ABTreeNode node = this;

      while (node.pairs[index].pointer != null) {
        node = node.pairs[index].pointer;
        index = 0;
      }

      node.pairs[index].pointer = new ABTreeLeaf(value, node);

    }

    void insert(Pair elem) {

      int index = findPlace(elem);

      //if the element already exists, stop insertion
      if (pairs[index].equals(elem) && count > index) return;

      if (pairs[index].compareTo(elem) < 0 && count > index) index++;

      Pair temp;

      if (index == b) {
        temp = elem;
        count++;
      } else {

        temp = pairs[index];

        pairs[index] = elem;

        // insert into list
        putIntoList(index, elem.index);

        index++;

        //plus one element
        count++;

        while (index < count) {

          if (index == b) {
            break;
          }

          Pair secondTemp = pairs[index];

          pairs[index++] = temp;

          temp = secondTemp;

        }
      }

      //if the side is full, we need to split it
      if (count == b + 1) {

        //index of the middle
        int middle = count / 2 + 1;
        //creating a new node
        ABTreeInnerNode newNode = new ABTreeInnerNode(b, parent);

        for (int i = middle + 1; i < count - 1; i++) {
          newNode.insert(pairs[i]);
          if (pairs[i].pointer != null)
            pairs[i].pointer.parent = newNode;
          pairs[i] = null;
        }
        newNode.insert(temp);
        if (temp.pointer != null)
          temp.pointer.parent = newNode;

        if (pairs[middle].pointer != null) {
          newNode.pairs[0].pointer = pairs[middle].pointer;
          pairs[middle].pointer.parent = newNode;
        }

        pairs[middle].pointer = newNode;

        count = middle;

        if (parent == null) {
          parent = new ABTreeInnerNode(b, null);
          root = parent;
          parent.pairs[0].pointer = this;
        }

        newNode.parent = parent;

        //now I insert the element
        parent.insert(pairs[middle]);
        pairs[middle] = null;
      }
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     * 
     * @return der Baum im Graphiz-Format
     */
    public abstract int dot (StringBuilder sb, int from);
  }

  /**
   * Diese Klasse repräsentiert einen inneren Knoten des Baumes.
   */
  private class ABTreeInnerNode extends ABTreeNode {

    ABTreeInnerNode(int n, ABTreeNode parent) { super(n, parent); }

    @Override public int dot (StringBuilder sb, int from) {
      int mine = from++;
      sb.append("\tstruct").append(mine).append(" [label=\"");
      sb.append("<f").append(0).append("> |");
      for (int i = 2; i < count*2; i+=2) {
        if (i > 2)
          sb.append("|");
        sb.append("<f").append(i - 1).append("> ");
        sb.append(pairs[i/2].index);
        sb.append("|<f").append(i).append("> ");
      }
      sb.append("\"];\n");
      for (int i = 0; i < count; i++) {
        int field = 2*i;
        if (pairs[i].pointer != null) {
          sb.append("\tstruct").append(mine).append(":<f").append(field)
                  .append(">").append(" -> ").append("struct").append(from).append(";\n");
          from = pairs[i].pointer.dot(sb, from);
        }
      }
      return from;
    }
  }

  /**
   * Diese Klasse repräsentiert ein Blatt des Baumes.
   */
  private class ABTreeLeaf extends ABTreeNode {

    public int value;

    ABTreeLeaf(int value, ABTreeNode parent) {
      super(b, parent);
      this.value = value;
    }

    @Override public int dot (StringBuilder sb, int from) {
      //sb.append("\tstruct").append(from).append(" [label=leaf, shape=ellipse];\n");
      sb.append("\tstruct").append(from).append(" [label=leaf, shape=ellipse];\n");
      return from + 1;
    }
  }

  public ABTree (int a, int b) {
    if(a < 2)
      throw new RuntimeException("Invalid a");
    else if(b < 2*a - 1)
      throw new RuntimeException("Invalid b");
    this.a = a;
    this.b = b;
  }

  /**
   * Diese Objektvariable speichert die Wurzel des Baumes.
   */
  private ABTreeNode root = null;

  /**
   * Diese Methode ist zum Debuggen gedacht und prüft, ob es sich
   * um einen validen (a,b)-Baum handelt.
   * 
   * @return 'true' falls der Baum ein valider (a,b)-Baum ist, 'false' sonst
  */
  public boolean validAB () {
    return validHeight(root)
            && validOrder(root, Integer.MIN_VALUE, Integer.MAX_VALUE)
            && validBoundary(root);
  }

  private boolean validHeight(ABTreeNode node) {

    if (node == null || node.pairs[0].pointer == null) return true;

    int height = height(node.pairs[0].pointer);

    for (int i = 0; i < node.count; i++) {
      if (height != height(node.pairs[i].pointer) || !validHeight(node.pairs[i].pointer)) return false;
    }

    return true;

  }

  private boolean validOrder(ABTreeNode node, int min, int max) {

    if (node == null || node instanceof ABTreeLeaf) return true;

    if (node.pairs[1].index < min || node.pairs[node.count - 1].index > max) return false;

    for (int i = 0; i < node.count - 1; i++) {

      if (node.pairs[i].index > node.pairs[i + 1].index) return false;

      if (node.pairs[i].pointer != null && !validOrder(node.pairs[i].pointer, node.pairs[i].index, node.pairs[i + 1].index)) return false;

    }

    if (!validOrder(node.pairs[node.count - 1].pointer, node.pairs[node.count - 1].index, Integer.MAX_VALUE)) return false;

    return true;

  }

  private boolean validBoundary(ABTreeNode node) {

    if (node == null || node instanceof ABTreeLeaf) return true;

    if (node != root && (node.count > b || node.count < a)) return false;

    for (int i = 0; i < node.count; i++) if (!validBoundary(node.pairs[i].pointer)) return false;

    return true;

  }

  /**
   * Diese Methode ermittelt die Höhe des Baumes.
   * 
   * @return die ermittelte Höhe
   */
  public int height() {

    if (root == null) return -1;

    return height(root) + 1;

  }

  private int height(ABTreeNode node) {

    if (node.pairs[0].pointer == null || node.pairs[0].pointer instanceof ABTreeLeaf) return -1;

    int max = -1;

    for (int i = 0; i < node.count; i++) {
      if (node.pairs[i].pointer != null)
        max = max(max, height(node.pairs[i].pointer));
    }

    return max + 1;

  }

  private static int max(int a, int b) { return a > b ? a : b; }

  /**
   * Diese Methode sucht einen Schlüssel im (a,b)-Baum.
   * 
   * @param key der Schlüssel, der gesucht werden soll
   * @return 'true', falls der Schlüssel gefunden wurde, 'false' sonst
   */
  public boolean find (int key) {

    if (locate(key) != null) return true;

    return false;
  }

  private ABTreeLeaf locate(int key) {

    if (root == null) return null;

    ABTreeNode node = root;

    while (node != null && !(node instanceof ABTreeLeaf)) {

      int ind = node.findPlace(new Pair(key, null));

      if (node.pairs[ind] == null) return null;

      node = node.pairs[ind].pointer;

    }

    if (node == null) return null;

    if (((ABTreeLeaf)node).value == key) return (ABTreeLeaf) node;

    return null;

  }

  /**
   * Diese Methode fügt einen neuen Schlüssel in den (a,b)-Baum ein.
   * 
   * @param key der einzufügende Schlüssel
   */
  public void insert (int key) {

    if (root == null) {
      root = new ABTreeInnerNode(b, null);
    }

    ABTreeNode current = root;

    Pair pair = new Pair(key);

    while (true) {
      //if the element is already in the tree, stop the insertion
      if (current.find(pair) != -1) return;

      int index = current.findPlace(pair);

      // if (pair.compareTo(current.pairs[index]) > 0 && index > 0 && current.pairs[index].pointer == null) index++;
      if (current.pairs[index].pointer == null || current.pairs[index].pointer instanceof ABTreeLeaf) {
        current.insert(pair);
        break;
      }
      else current = current.pairs[index].pointer;

    }
  }

  /**
   * Diese Methode löscht einen Schlüssel aus dem (a,b)-Baum.
   * 
   * @param key der zu löschende Schlüssel
   * @return 'true' falls der Schlüssel gefunden und gelöscht wurde, 'false' sonst
   */
  public boolean remove (int key) {

    if (!find(key)) return false;

    ABTreeNode node = locate(key).parent;

    int ind = node.findPlace(new Pair(key, null));

    return remove(node, ind, key);
  }

  private boolean remove(ABTreeNode node, int ind, int key) {

    if (node.parent.count == 2 && root == node) {
      root = node.pairs[0].pointer;
      node.pairs[0].pointer.parent = null;
      return true;
    }

    if (ind == 0 && node == root && node.count <= 2) {
      root = null;
      return true;
    }


    if (ind == 0 && node != root) {

      int tmp = node.parent.pairs[node.parent.findPlace(new Pair(key))].index;

      node.parent.pairs[node.parent.findPlace(new Pair(key))].index = node.pairs[1].index;

      node.pairs[1].index = tmp;

    }

    deleteElement(node, ind);

    if (node.count >= a || node.parent == null || node == root) return true;

    if (neighbourLookup(node, key) == 1) {
      steelRight(node, key);
    } else if (neighbourLookup(node, key) == -1) {
      steelLeft(node, key);
    } else {
      if (!mergeRight(node, key)) mergeLeft(node, key);
    }

    return true;

  }

  private boolean mergeLeft(ABTreeNode node, int key) {

    int ind = node.parent.findPlace(new Pair(key));

    if (ind <= 1) return false;

    ABTreeNode left = node.parent.pairs[ind - 1].pointer;

    if (left == null || left.pairs[left.count - 1] == null) return false;

    ABTreeNode newNode = new ABTreeInnerNode(b, node.parent);

    newNode.count = node.count + left.count;

    for (int i = 0; i < left.count; i++) {
      newNode.pairs[i] = left.pairs[i];
    }

    newNode.pairs[left.count] = new Pair(node.parent.pairs[ind].index, node.pairs[0].pointer);
    node.pairs[0].pointer.parent = newNode;

    for (int i = left.count + 1; i < newNode.count; i++) {
      newNode.pairs[i] = node.pairs[i - left.count];
    }

    node.parent.pairs[ind - 1].pointer = newNode;

    return remove(node.parent, ind, key);

  }

  private boolean mergeRight(ABTreeNode node, int key) {

    int ind = node.parent.findPlace(new Pair(key)) + 1;

    if (ind == node.parent.count) return false;

    ABTreeNode right = node.parent.pairs[ind].pointer;

    if (right == null || right.pairs[0] == null) return false;

    ABTreeNode newNode = new ABTreeInnerNode(b, node.parent);

    newNode.count = node.count + right.count;

    for (int i = 0; i < node.count; i++) {
      newNode.pairs[i] = node.pairs[i];
    }

    newNode.pairs[node.count] = new Pair(node.parent.pairs[ind].index, right.pairs[0].pointer);
    right.pairs[0].pointer.parent = newNode;

    for (int i = node.count + 1; i < newNode.count; i++) {
      newNode.pairs[i] = right.pairs[i - node.count];
    }

    node.parent.pairs[ind - 1].pointer = newNode;

    return remove(node.parent, ind, key);

  }

  public void steelRight(ABTreeNode node, int key) {

    int ind = node.parent.findPlace(new Pair(key, null)) + 1;

    ABTreeNode right = node.parent.pairs[ind].pointer;

    // take the first element from the right node to the parent and the parent element to the left
    node.pairs[node.count++] = new Pair(node.parent.pairs[ind].index, right.pairs[0].pointer);
    node.parent.pairs[ind].index = right.pairs[1].index;
    node.pairs[node.count - 1].pointer.parent = node;

    // remove the element from the right neighbour and deal with the pointers
    right.pairs[0].pointer = right.pairs[1].pointer;
    deleteElement(right, 1);

  }

  public void steelLeft(ABTreeNode node, int key) {

    int ind = node.parent.findPlace(new Pair(key, null));

    ABTreeNode left = node.parent.pairs[ind - 1].pointer;

    // take the last element from the left node to the parent and the parent element to the right
    node.count++;
    Pair tmp1, tmp2 = node.pairs[0];
    for (int i = 0; i < node.count - 1; i++) {
      tmp1 = node.pairs[i + 1];
      node.pairs[i + 1] = tmp2;
      tmp2 = tmp1;
    }
    node.pairs[0] = new Pair(node.pairs[1].index, left.pairs[left.count - 1].pointer);

    node.pairs[1].index = node.parent.pairs[ind].index;

    node.pairs[0].pointer.parent = node;
    node.parent.pairs[ind].index = left.pairs[left.count - 1].index;

    // remove the element from the left neighbour
    left.count--;

  }


    /**
     *
     * @param node
     * @param key
     * @return '1', falls der rechte Nachbar hat mehr ELemente als a; '-1', falls das fuer den linken geht; sonst '0'
     */
  private int neighbourLookup(ABTreeNode node, int key) {

    int ind = node.parent.findPlace(new Pair(key, null));

    if (ind < node.parent.count - 1 && node.parent.pairs[ind + 1].pointer.count > a) return 1;

    if (ind > 0 && node.parent.pairs[ind - 1].pointer.count > a) return -1;

    return 0;

  }

  private void deleteElement(ABTreeNode node, int ind) {

    node.count--;

    for (int i = ind; i <= node.count; i++) {
      node.pairs[i] = node.pairs[i + 1];
    }

  }


  public class Pair implements Comparable<Pair> {
    public   int index;
    public   ABTreeNode pointer = null;
    Pair(int index, ABTreeNode pointer) {
      this.index = index;
      this.pointer = pointer;
    }
    Pair() {
      index = 0;
    }
    Pair(int index) {
      this.index = index;
    }
    public boolean equals(Pair other) {
      if (other.index == index) return true;
      return false;
    }
    @Override
    public int compareTo(Pair other) {
      if (index > other.index) return 1;
      if (index < other.index) return -1;
      return 0;
    }
  }

  /**
   * Diese Methode wandelt den Baum in das Graphviz-Format um.
   * 
   * @return der Baum im Graphiz-Format
   */
  String dot () {
    StringBuilder sb = new StringBuilder();
    sb.append("digraph {\n");
    sb.append("\tnode [shape=record];\n");
    if (root != null)
      root.dot(sb, 0);
    sb.append("}");
    return sb.toString();
  }

  public void traverse() {
    if (root == null) return;
    traverse(root);
    System.out.println();
  }

  private void traverse(ABTreeNode node) {

    if (node.pairs[0].pointer != null) traverse(node.pairs[0].pointer);

    for (int i = 1; i < node.count; i++) {

      System.out.print(node.pairs[i].index + " ");
      if (!(node.pairs[i].pointer instanceof ABTreeLeaf)) traverse(node.pairs[i].pointer);

    }

  }

  public static void main(String[] args) {

    ABTree tree = new ABTree(2, 4);

    for (int i = 0; i < 9; i++) tree.insert(i);


    System.out.println(tree.dot());

  }

}

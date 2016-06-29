import java.util.*;

/**
 * Diese Klasse repräsentiert den Graphen der Straßen und Wege aus
 * OpenStreetMap.
 */
public class MapGraph {

  Map<Long, OSMNode> nodes = new HashMap<>();

  KdTree tree;

  MapGraph(double minlat, double minlon, double maxlat, double maxlon) {
    tree = new KdTree(minlat - 1, minlon - 1, maxlat + 1, maxlon + 1);
  }


  /**
   * Ermittelt, ob es eine Kante im Graphen zwischen zwei Knoten gibt.
   * 
   * @param from der Startknoten
   * @param to der Zielknoten
   * @return 'true' falls es die Kante gibt, 'false' sonst
   */
  boolean hasEdge(OSMNode from, OSMNode to) {
    return from.isConnected(to);
  }

  /**
   * Diese Methode findet zu einem gegebenen Kartenpunkt den
   * nähesten OpenStreetMap-Knoten. Gibt es mehrere Knoten mit
   * dem gleichen kleinsten Abstand zu, so wird derjenige Knoten
   * von ihnen zurückgegeben, der die kleinste Id hat.
   * 
   * @param p der Kartenpunkt
   * @return der OpenStreetMap-Knoten
   */
  public OSMNode closest (MapPoint p) {

    return tree.nearest(p, null);

  }

  /**
   * Diese Methode sucht zu zwei Kartenpunkten den kürzesten Weg durch
   * das Straßen/Weg-Netz von OpenStreetMap.
   * 
   * @param from der Kartenpunkt, bei dem gestartet wird
   * @param to der Kartenpunkt, der das Ziel repräsentiert
   * 
   * @return eine mögliche Route zum Ziel und ihre Länge; die Länge
   * des Weges bezieht sich nur auf die Länge im Graphen, der Abstand
   * von 'from' zum Startknoten bzw. 'to' zum Endknoten wird
   * vernachlässigt.
   */
  public Optional<RoutingResult> route (MapPoint from, MapPoint to) {

    PriorityQueue<Vertex> pq = new PriorityQueue<>();

    Map<OSMNode, Integer> mapDistance = null;
    Map<OSMNode, OSMNode> mapPredcessor = null;
    Set<OSMNode> visited = new HashSet<>();

    OSMNode fromN = closest(from);
    OSMNode toN   = closest(to);

    pq.add(new Vertex(fromN, 0));

    Vertex currNode = pq.peek();

    while (!currNode.node.equals(toN)) {

      mapDistance = new HashMap<>();
      mapPredcessor = new HashMap<>();

      mapDistance.put(fromN, 0);

      while (!pq.isEmpty() && !currNode.node.equals(toN)) {

        currNode = pq.poll();
        visited.add(currNode.node);

        for (OSMNode node : currNode.node.connections) {
          if (visited.contains(node)) continue;

          int distance = (int) (currNode.node.getLocation().distance(node.getLocation())) + mapDistance.get(currNode.node);

          if (!mapDistance.containsKey(node)) {
            pq.add(new Vertex(node, distance));
            mapDistance.put(node, distance);
            mapPredcessor.put(node, currNode.node);
          }
          else if (mapDistance.get(node) > distance) {
            pq.remove(node);
            mapDistance.put(node, distance);
            mapPredcessor.put(node, currNode.node);
            pq.add(new Vertex(node, distance));
          }
        }

        //System.out.println(currNode.getLocation().distance(to));

      }

      // if it's a dead end
      if (pq.isEmpty()) {
        fromN = tree.nearest(from, visited);
        pq.add(new Vertex(fromN, 0));
      }

    }

    int distance = mapDistance.get(toN);

    List<OSMNode> path = new ArrayList<>();

    OSMNode pred = mapPredcessor.get(currNode.node);

    //path.add(new OSMNode(1, to.getLat(), to.getLon()));

    path.add(currNode.node);

    while (!pred.equals(fromN)) {
      path.add(pred);
      pred = mapPredcessor.get(pred);
    }

    path.add(pred);

    //path.add(new OSMNode(0, from.getLat(), from.getLon()));

    return Optional.of(new RoutingResult(toNodeArray(path), distance));

  }

  private static OSMNode[] toNodeArray(List<OSMNode> nodes) {

    OSMNode[] path = new OSMNode[nodes.size()];

    int k = 0;

    for (int i = nodes.size() - 1; i >= 0; i--) {
      path[k++] = nodes.get(i);
    }

    return path;

  }

  private static class Vertex implements Comparable<Vertex> {
    public int dist = Integer.MAX_VALUE;
    public OSMNode node;

    public Vertex(OSMNode node, int dist) {
      this.node = node;
      this.dist = dist;
    }

    @Override
    public int compareTo(Vertex other) {
      return Integer.compare(dist, other.dist);
    }

    @Override
    public boolean equals(Object o) {

      if (!(o instanceof OSMNode)) return false;

      return ((OSMNode)o).getId() == node.getId();

    }

  }


}

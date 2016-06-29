import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.*;

/**
 * Created by yuriyarabskyy on 28/06/16.
 */
public class KdTree
{
    double minlat, minlon, maxlat, maxlon;

    public void draw()
    {
        draw(root, Coordinate.X);
    }

    public KdTree(double a, double b, double c, double d)
    {
        root = null;
        size = 0;
        minlat = a;
        minlon = b;
        maxlat = c;
        maxlon = d;
    }

    public void insert(OSMNode point)
    {
        assertArgument(point);

        root = insert(root, root, point, Coordinate.X);

        ++size;
    }

    public boolean contains(OSMNode point)
    {
        assertArgument(point);

        return contains(root, point, Coordinate.X);
    }

    public Iterable<OSMNode> range(RectHV rect)
    {
        assertArgument(rect);

        ArrayList<OSMNode> points = new ArrayList<>();

        if (root != null)
        {
            range(root, rect, Coordinate.X, points);
        }

        return points;
    }

    public OSMNode nearest(MapPoint point, Set<OSMNode> excluded)
    {
        assertArgument(point);

        if (root == null) return null;

        Champion champion = new Champion();

        nearest(root, point, Coordinate.X, champion, excluded);

        return champion.point;
    }


    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    private enum Coordinate { X, Y }

    private class Node
    {
        public Node(Node root, OSMNode point, Coordinate coordinate)
        {
            this.point = point;

            if (root == null) rectangle = new RectHV(minlon, minlat, maxlon, maxlat);

            else
            {
                coordinate = other(coordinate);

                int comparison = compare(point, root.point, coordinate);

                double x, y;

                boolean isX = coordinate == Coordinate.X;

                if (comparison < 0)
                {
                    x = isX ? root.point.getLocation().getLon() : root.rectangle.xmax();
                    y = isX ? root.rectangle.ymax() : root.point.getLocation().getLat();

                    rectangle = new RectHV(
                            root.rectangle.xmin(),
                            root.rectangle.ymin(),
                            x,
                            y
                    );
                }

                else if (comparison > 0)
                {
                    x = isX ? root.point.getLocation().getLon() : root.rectangle.xmin();
                    y = isX ? root.rectangle.ymin() : root.point.getLocation().getLat();

                    rectangle = new RectHV(
                            x,
                            y,
                            root.rectangle.xmax(),
                            root.rectangle.ymax()
                    );
                }

                else rectangle = root.rectangle;
            }

        }

        public Node(OSMNode point, RectHV rectangle)
        {
            this.point = point;
            this.rectangle = rectangle;
        }

        public final OSMNode point;

        public final RectHV rectangle;

        public Node left = null;
        public Node right = null;
    }

    private static class Champion
    {
        public Champion()
        {
            this(null, Double.POSITIVE_INFINITY);
        }

        public Champion(OSMNode point, double distance)
        {
            this.point = point;
            this.distance = distance;
        }

        public OSMNode point;
        public double distance;
    }

    private static final class Pair<T, U>
    {
        public Pair(T first, U second)
        {
            this.first = first;
            this.second = second;
        }

        public final T first;
        public final U second;
    }

    private Node insert(Node root, Node node, OSMNode point, Coordinate coordinate)
    {
        if (node == null) return new Node(root, point, coordinate);

        int comparison = compare(point, node.point, coordinate);

        if (comparison < 0)
        {
            node.left = insert(node, node.left, point, other(coordinate));
        }

        else if (comparison > 0)
        {
            node.right = insert(node, node.right, point, other(coordinate));
        }

        else --size; // Incremented again at the end (delta = 0)

        return node;
    }

    private boolean contains(Node node, OSMNode point, Coordinate coordinate)
    {
        if (node == null) return false;

        int comparison = compare(point, node.point, coordinate);

        if (comparison < 0)
        {
            return contains(node.left, point, other(coordinate));
        }

        else if (comparison > 0)
        {
            return contains(node.right, point, other(coordinate));
        }

        else return true;
    }

    private void range(Node node,
                       RectHV rectangle,
                       Coordinate coordinate,
                       Collection<OSMNode> points)
    {
        if (node == null) return;

        Pair<Integer, Integer> comparison = compare(node.point, rectangle, coordinate);

        if (comparison.first <= 0)
        {
            range(node.left, rectangle, other(coordinate), points);
        }

        if (rectangle.contains(node.point.getLocation()))
        {
            points.add(node.point);
        }

        if (comparison.second <= 0)
        {
            range(node.right, rectangle, other(coordinate), points);
        }
    }

    private void nearest(Node node,
                         MapPoint point,
                         Coordinate coordinate,
                         Champion champion, Set<OSMNode> excluded)
    {
        if (node == null) throw new NullPointerException();

        double distance = node.point.getLocation().distance(point);

        if (distance <= champion.distance && (excluded == null || !excluded.contains(node.point)))
        {
            if (champion == null || distance < champion.distance || node.point.getId() < champion.point.getId()) {
                champion.point = node.point;
                champion.distance = distance;
            }
        }

        Pair<Node, Node> closer = findCloserSide(node, point, coordinate);

        if (closerPointPossible(closer.first, point, champion))
        {
            nearest(closer.first, point, other(coordinate), champion, excluded);
        }

        if (closerPointPossible(closer.second, point, champion))
        {
            nearest(closer.second, point, other(coordinate), champion, excluded);
        }
    }

    private Pair<Node, Node> findCloserSide(Node node, MapPoint point, Coordinate coordinate)
    {
        if (coordinate == Coordinate.X)
        {
            if (point.getLon() - node.point.getLocation().getLon() > 0)
            {
                return new Pair<>(node.right, node.left);
            }
        }

        else if (point.getLat() - node.point.getLocation().getLat() > 0)
        {
            return new Pair<>(node.right, node.left);
        }

        return new Pair<>(node.left, node.right);
    }

    private boolean closerPointPossible(Node node, MapPoint point, Champion champion)
    {
        if (node == null) return false;

        return node.rectangle.distanceSquaredTo(point) < champion.distance;
    }

    private int compare(OSMNode first, OSMNode second, Coordinate coordinate)
    {
        if (coordinate == Coordinate.X)
        {
            int result = compare(first.getLocation().getLon(), second.getLocation().getLon());

            if (result != 0) return result;

            return compare(first.getLocation().getLat(), second.getLocation().getLat());
        }

        else
        {
            int result = compare(first.getLocation().getLat(), second.getLocation().getLat());

            if (result != 0) return result;

            return compare(first.getLocation().getLon(), second.getLocation().getLon());
        }
    }

    private Pair<Integer, Integer> compare(OSMNode point, RectHV rectangle, Coordinate coordinate)
    {
        int minimumCompare;
        int maximumCompare;

        if (coordinate == Coordinate.X)
        {
            double x = point.getLocation().getLon();

            minimumCompare = compare(rectangle.xmin(), x);
            maximumCompare = compare(x, rectangle.xmax());
        }

        else
        {
            double y = point.getLocation().getLat();

            minimumCompare = compare(rectangle.ymin(), y);
            maximumCompare = compare(y, rectangle.ymax());
        }

        return new Pair<>(minimumCompare, maximumCompare);
    }

    private int compare(double first, double second)
    {
        if (first < second) return -1;

        else if (first > second) return +1;

        else return 0;
    }

    private Coordinate other(Coordinate coordinate)
    {
        if (coordinate == Coordinate.X) return Coordinate.Y;

        else return Coordinate.X;
    }

    private void draw(Node node, Coordinate coordinate)
    {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        StdDraw.point(node.point.getLocation().getLon(), node.point.getLocation().getLat());

        StdDraw.setPenRadius();

        if (coordinate == Coordinate.X)
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(
                    node.point.getLocation().getLon(),
                    node.rectangle.ymin(),
                    node.point.getLocation().getLon(),
                    node.rectangle.ymax()
            );
        }

        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(
                    node.rectangle.xmin(),
                    node.point.getLocation().getLat(),
                    node.rectangle.xmax(),
                    node.point.getLocation().getLat()
            );
        }

        coordinate = other(coordinate);

        draw(node.left, coordinate);
        draw(node.right, coordinate);
    }


    private void assertArgument(Object object)
    {
        if (object == null)
        {
            throw new NullPointerException("Argument cannot be null!");
        }
    }

    private Node root;

    private int size;

    public static void main(String[] args)
    {
        KdTree tree = new KdTree(0, 0, 1, 1);

        tree.insert(new OSMNode(123, .5, .5));
        tree.insert(new OSMNode(124, .5, .7));


        System.out.println(tree.nearest(new MapPoint(.5, 0.9), null));
    }

}
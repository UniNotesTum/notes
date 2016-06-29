import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GPXWriter {

  public static void write (String fileName, RoutingResult rr) throws FileNotFoundException {

    PrintWriter writer = new PrintWriter(fileName);

    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n <gpx version=\"1.1\" creator=\"Yuriy Arabskyy\">");

    OSMNode[] path = rr.getPath();

    for (OSMNode node : path) {
      drawPoint(writer, node);
      drawRte(writer, node);
    }

    writer.write("</gpx>\n");

    writer.flush();

    writer.close();

  }

  public static void drawPoint(PrintWriter writer, OSMNode node) {

    MapPoint p = node.getLocation();

    writer.write("<wpt lat=\"" + p.getLat() + "\" lon=\"" + p.getLon() + "\">\n");
    writer.write("<name>" + node.getId() + "</name>\n");
    writer.write("</wpt>");

  }

  public static void drawRte(PrintWriter writer, OSMNode node) {

    MapPoint p = node.getLocation();

    writer.write("<rte>");

    writer.write("<rtept lat=\"" + p.getLat() + "\" lon=\"" + p.getLon() + "\">\n");
    writer.write("<name>" + node.getId() + "</name>\n");
    writer.write("</rtept>");

    writer.write("</rte>\n");

  }

  public static void drawTrk(PrintWriter writer, OSMNode node) {

    MapPoint p = node.getLocation();

    writer.write("<trk>");
    writer.write("<trkseg>\n");

    writer.write("<trkpt lat=\"" + p.getLat() + "\" lon=\"" + p.getLon() + "\">\n");
    writer.write("<name>" + node.getId() + "</name>\n");
    writer.write("</trkpt>");

    writer.write("</trkseg>\n");
    writer.write("</trk>\n");

  }

  public static void main(String[] args) throws FileNotFoundException {

    PrintWriter writer = new PrintWriter("testing.gpx");

    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n <gpx version=\"1.1\" creator=\"Ersteller der Datei\">\n");

    writer.write("\nHallo");

    writer.flush();

    writer.close();

  }

}

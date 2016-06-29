import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.LongAccumulator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Diese Klasse erlaubt es, aus einer Datei im
 * OSM-Format ein MapGraph-Objekt zu erzeugen. Sie
 * nutzt dazu einen XML-Parser.
 */
public class MapParser {

  public static MapGraph parseFile(String fileName) {

    OsmHandler handler = new OsmHandler();

    try {
      File file = new File(fileName);
      if (file.length() > 20) handler.big = true;
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser parser = factory.newSAXParser();

      parser.parse(file, handler);
    }
    catch (Exception e) { e.printStackTrace(); }

    return handler.getMap();
  }


  private static class OsmHandler extends DefaultHandler {

    private MapGraph map = null;

    boolean big = false;
    private boolean node = false;
    private boolean way  = false, oneway = false, building = false;
    private long currentWayId = 0L;
    private List<Long> currentNodes = new ArrayList<>();
    private String currentName = "";

    public MapGraph getMap() {
      return map;
    }

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {

      if (qName.equals("node")) {
        node = true;
        way = false;

        long id = Long.parseLong(attributes.getValue("id"));
        double lat = Double.parseDouble(attributes.getValue("lat"));
        double lon = Double.parseDouble(attributes.getValue("lon"));

        OSMNode node = new OSMNode(id, lat, lon);

        map.nodes.put(id, node);
        map.tree.insert(node);

      } else if (qName.equals("way")) {
        way = true;
        node = false;

        currentWayId = Long.parseLong(attributes.getValue("id"));
      } else if (way && qName.equals("nd")) {
        currentNodes.add(Long.parseLong(attributes.getValue("ref")));
      } else if (way && qName.equals("tag")) {

        String key = attributes.getValue("k");

        if (key.equals("oneway") && attributes.getValue("v").equals("yes")) {
          oneway = true;
        }

        if (key.equals("name")) {
          currentName = attributes.getValue("v");
        }

        if (key.equals("building") && attributes.getValue("v").equals("yes")) {
          building = true;
        }

      } else if (qName.equals("bounds")) {

        double minlat = Double.parseDouble(attributes.getValue("minlat"));
        double minlon = Double.parseDouble(attributes.getValue("minlon"));
        double maxlat = Double.parseDouble(attributes.getValue("maxlat"));
        double maxlon = Double.parseDouble(attributes.getValue("maxlon"));

        map = new MapGraph(minlat, minlon, maxlat, maxlon);

      }

    }


    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

      if (qName.equals("way")) {

        if (building) {
          // cleaning
          building = false;
          way = false;
          currentName = "";
          currentNodes = new ArrayList<>();
          currentWayId = 0L;
          oneway = false;
          return;
        }

        way = false;

        Optional<String> name = Optional.empty();

        if (!currentName.isEmpty()) name = Optional.of(currentName);

        //OSMWay way1 = new OSMWay(currentWayId, currentNodes, oneway, name);

        //map.ways.put(currentWayId, way1);

        // connecting direct neighbours
        OSMNode tmpNode = map.nodes.get(currentNodes.get(0));
        for (Long nodeId : currentNodes) {
          if (tmpNode.getId() == nodeId) continue;
          OSMNode tmp = map.nodes.get(nodeId);
          tmpNode.connect(tmp);
          if (!oneway) tmp.connect(tmpNode);
          tmpNode = tmp;
        }

        // cleaning
        currentName = "";
        currentNodes = new ArrayList<>();
        currentWayId = 0L;
        oneway = false;

      }

    }

  }

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println("Reading OSM data...");
    MapGraph g = MapParser.parseFile("campus_garching.osm");
    System.out.println("Finished reading OSM data.");

    MapPoint p1 = new MapPoint(48.26313, 11.67459);
    MapPoint p2 = new MapPoint(48.26632, 11.66750);

    RoutingResult rr = g.route(p1, p2).get();

    System.out.println(rr.getDistance());

    GPXWriter.write("testing.gpx", rr);

  }

}

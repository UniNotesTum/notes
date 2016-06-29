import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class NogivanTest {
  private int lengthAndCheck(MapGraph g, OSMNode[] path) {
    int length = 0;
    for (int i = 1; i < path.length; i++) {
      assertTrue(g.hasEdge(path[i - 1], path[i]));
      length += path[i - 1].getLocation().distance(path[i].getLocation());
    }
    return length;
    
  }

  @Test
  public void testabcd1() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g = MapParser.parseFile("campus_garching.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(48.2626633, 11.6689035), new MapPoint(48.2622312, 11.6662273));
    
    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 277698459L);
    assertTrue(path[path.length - 1].getId() == 277698572L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
    
    assertTrue(rr.getDistance() == 254);
  }
  
  @Test
  public void testabcd2() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g = MapParser.parseFile("campus_garching.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(48.26313, 11.67459), new MapPoint(48.26632, 11.66750));
    
    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 2496758189L);
    assertTrue(path[path.length - 1].getId() == 277698564L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
    
    assertTrue(rr.getDistance() == 795);
  }
  
  @Test
  public void testabcd3() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g = MapParser.parseFile("oberbayern.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(48.26540, 11.67086), new MapPoint(48.13820, 11.55596));
    
    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 1895894825L);
    assertTrue(path[path.length - 1].getId() == 2944348393L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
   
    assertTrue(rr.getDistance() == 17567);
  }
  
  @Test
  public void testabcd4() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g = MapParser.parseFile("oberbayern.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(48.1108, 11.6962), new MapPoint(48.0717, 11.3759));
    
    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 18124220L);
    assertTrue(path[path.length - 1].getId() == 1823897856L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
    
    assertTrue(rr.getDistance() == 26448);
  }
  
  @Test
  public void testabcdf1() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g;
    g = MapParser.parseFile("niedersachsen.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(52.4362, 9.7544), new MapPoint(52.3768, 9.9762));

    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 2482921650L);
    assertTrue(path[path.length - 1].getId() == 309416890L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
    
    assertTrue(rr.getDistance() == 17794);
  }
  
  @Test
  public void testabcdf2() throws SAXException, IOException, ParserConfigurationException {
    System.out.println("Reading OSM data...");
    MapGraph g;
    g = MapParser.parseFile("niedersachsen.osm");
    System.out.println("Finished reading OSM data...");

    Optional<RoutingResult> rrOpt = g.route(new MapPoint(53.4392, 9.9674), new MapPoint(52.3950, 9.7311));

    assertTrue(rrOpt.isPresent());
    
    RoutingResult rr = rrOpt.get();
    OSMNode[] path = rr.getPath();
    assertTrue(path.length > 0);
    assertTrue(path[0].getId() == 1657540646L);
    assertTrue(path[path.length - 1].getId() == 1964073105L);
    int pathLength = lengthAndCheck(g, rr.getPath());
    assertTrue(pathLength == rr.getDistance());
    
    assertTrue(rr.getDistance() == 126427);
  }

}

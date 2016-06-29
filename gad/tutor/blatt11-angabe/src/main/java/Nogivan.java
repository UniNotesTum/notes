
import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Nogivan {

  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    System.out.println("Reading OSM data...");
    MapGraph g;
    if(args.length > 0)
      g = MapParser.parseFile(args[0]); 
    else
      g = MapParser.parseFile("map.osm");
    System.out.println("Finished reading OSM data.");

    Optional<RoutingResult> rr = g.route(new MapPoint(48.2690197, 11.6751468), new MapPoint(48.2638814, 11.6661943));
    
    if(!rr.isPresent()) {
      System.out.println("No route :-(");
    } else {
      System.out.println("Distance: " + rr.get().getDistance());
      System.out.println("Writing GPX track to route.gpx...");
      GPXWriter.write("route.gpx", rr.get());
    }
    
    System.out.println("Finished!");
  }
}

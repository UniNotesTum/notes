
/**
 * Diese Klasse implementiert einen Kartenpunkt. Ein
 * Kartenpunkt hat einen Position in Form eines Länge-
 * und Breitengrades.
 */
public class MapPoint {
  /**
   * Der Breitengrad
   */
  private double lat;
  
  public double getLat () {
    return lat;
  }
  
  /**
   * Der Längengrad
   */
  private double lon;
  
  public double getLon () {
    return lon;
  }
  
  public MapPoint (double lat, double lon) {
    this.lat = lat;
    this.lon = lon;
  }
  
  /**
   * Diese Methode berechnet den Abstand dieses Kartenpunktes
   * zu einem anderen Kartenpunkt.
   * 
   * @param other der andere Kartenpunkt
   * @return der Abstand in Metern
   */
  public double distance(MapPoint other) {
    final int R = 6371; // Radius of the earth

    Double latDistance = Math.toRadians(lat - other.lat);
    Double lonDistance = Math.toRadians(lon - other.lon);
    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(other.lat))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters

    return distance;
  }
  
  @Override public String toString () {
    return  "lat = " + lat + ", lon = " + lon;
  }

}

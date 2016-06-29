import java.util.List;
import java.util.Optional;

/**
 * Diese Klasse repräsentiert einen OSM-Weg.
 */
public class OSMWay {
  /**
   * Die ID des Weges
   */
  private long id;
  
  public long getId() {
    return id;
  }
  
  /**
   * Die Knoten, die der Weg enthält
   */
  private List<Long> nodes;
  
  public List<Long> getNodes() {
    return nodes;
  }
  
  /**
   * Gibt an, ob der Weg eine Einbahnstraße ist
   */
  private boolean oneWay;
  
  public boolean isOneWay() {
    return oneWay;
  }
  
  /**
   * Der Name des Weges, falls vorhanden
   */
  private Optional<String> name;
  
  public Optional<String> getName() {
    return name;
  }
  
  public OSMWay(long id, List<Long> nodes, boolean oneWay, Optional<String> name) {
    this.id = id;
    this.nodes = nodes;
    this.oneWay = oneWay;
    this.name = name;
  }
  
  @Override
  public String toString() {
    StringBuilder sB = new StringBuilder();
    sB.append("Way {id=").append(id).append(", oneWay=").append(oneWay).append(", name=").append(name).append("}").append('\n');
    sB.append('\t');
    for (int i = 0; i < nodes.size(); i++) {
      if(i > 0)
        sB.append("->");
      sB.append(nodes.get(i));
    }
    sB.append('\n');
    return sB.toString();
  }
  
  @Override public boolean equals (Object obj) {
    OSMWay objCasted = (OSMWay)obj;
    if(id != objCasted.id)
      return false;
    if(nodes.size() != objCasted.nodes.size())
      return false;
    for (int i = 0; i < nodes.size(); i++)
      if(!nodes.get(i).equals(objCasted.nodes.get(i)))
        return false;
    if(oneWay != objCasted.oneWay)
      return false;
    if(!name.equals(objCasted.name))
      return false;
    return true;
  }
}

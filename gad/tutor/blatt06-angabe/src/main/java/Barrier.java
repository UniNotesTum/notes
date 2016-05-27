/**
 * Diese Klasse implementiert eine Barriere für Threads.
 */
public class Barrier {
  private int size;
  
  private int waiting;
  
  /**
   * @param size die Anzahl an Threads, die synchronisirt werden
   * sollen
   */
  public Barrier(int size) {
    this.size = size;
    this.waiting = 0;
  }
  
  /**
   * Diese Methode wird zur Synchronisation der Threads verwendet.
   * Ein Thread wird von ihr aufgehalten, bis size viele Threads
   * sie aufgerufen haben.
   */
  public void barrierWait() {
    if(size == 1)
      return;
    synchronized (this) {
      waiting++;
      if(waiting < size)
        try {
          this.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
          throw new Error();
        }
      else {
        waiting = 0;
        this.notifyAll();
      }
    }

  }
  
}

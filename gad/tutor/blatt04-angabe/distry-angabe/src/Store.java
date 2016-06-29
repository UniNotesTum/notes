import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class Store {

  public static void main (String[] args) {
    /*
     * Configuration
     */
    String masterHost = "127.0.0.1";
    int masterStorePort = 5000;

    try {

      Socket socket = new Socket(masterHost, masterStorePort);
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

      Hashtable<String, Integer> hashtable = new Hashtable<>();

      RequestVisitor rv = new RequestVisitor();

      rv.__((ReadRequest readRequest) -> {

        String key = readRequest.getKey();

        IResponse response;

        if (hashtable.containsKey(key)) {
          response = new ReadResponse(hashtable.get(key));
        } else {
          response = new ReadResponse();
        }

        out.writeObject(response);

        out.flush();

      }, (StoreRequest storeRequest) -> {

        hashtable.put(storeRequest.getKey(), storeRequest.getValue());

        IResponse response = new StoreResponse();

        out.writeObject(response);

        out.flush();

      });

      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

      while (true) {

        IRequest request = (IRequest) in.readObject();

        request.accept(rv);

      }


    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}

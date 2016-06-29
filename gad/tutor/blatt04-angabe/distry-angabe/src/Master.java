import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Master {

  public static void main (String[] args) throws IOException {
    /*
     * Configuration
     */
    int storeCount = 2;
    int storeServerPort = 5000;
    int clientServerPort = 5555;

    final List<Socket> stores = new ArrayList<>();

    final ServerSocket storeListener = new ServerSocket(storeServerPort);
    final ServerSocket clientListener = new ServerSocket(clientServerPort);

    for (int i = 0; i < storeCount; i++) {
      stores.add(storeListener.accept());
    }

    List<ObjectInputStream> storesInput = new ArrayList<>();
    List<ObjectOutputStream> storesOutput = new ArrayList<>();

    for (int i = 0; i < storeCount; i++) {

      storesOutput.add(new ObjectOutputStream(stores.get(i).getOutputStream()));
      storesInput.add(new ObjectInputStream(stores.get(i).getInputStream()));

    }

    final HashString hashString = new HashString(storeCount);

    while (true) {
      Socket clientSocket = clientListener.accept();
      System.out.println("New client");
      new Thread(new ClientHandler(clientSocket, hashString, storesInput, storesOutput)).start();
    }

  }

  private static class ClientHandler implements Runnable {

    private Socket clientSocket;
    private HashString hashString;
    private List<ObjectInputStream> storeInput;
    private List<ObjectOutputStream> storeOutput;

    public ClientHandler(Socket clientSocket, HashString hashString, List<ObjectInputStream> storeInput, List<ObjectOutputStream> storeOutput) {
      this.clientSocket = clientSocket;
      this.hashString = hashString;
      this.storeInput = storeInput;
      this.storeOutput = storeOutput;
    }

    @Override
    public void run() {

      try {

        ObjectInputStream inClient = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream outClient = new ObjectOutputStream(clientSocket.getOutputStream());

        while (true) {

          IRequest request = (IRequest) inClient.readObject();

          int hash = hashString.hash(request.getKey());


          ObjectOutputStream outStore = storeOutput.get(hash);

          outStore.writeObject(request);
          outStore.flush();

          ObjectInputStream inStore = storeInput.get(hash);

          Object response = inStore.readObject();

          outClient.writeObject(response);
          outClient.flush();

        }

      } catch (Exception e) {
        System.out.println("Client has been disconnected");
      }

    }

  }

}

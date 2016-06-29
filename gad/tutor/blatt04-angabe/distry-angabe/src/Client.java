import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public static void main (String[] args) {
    /*
     * Configuration
     */
    String masterHost = "127.0.0.1";
    int masterClientPort = 5555;

    Socket socket = null;
    ObjectOutputStream masterOutputStream = null;
    ObjectInputStream masterInputStream = null;

    try {
      socket = new Socket(masterHost, masterClientPort);
      masterOutputStream = new ObjectOutputStream(socket.getOutputStream());
      masterInputStream = new ObjectInputStream(socket.getInputStream());
    } catch (Exception e) { e.printStackTrace(); }

    ResponseVisitor rv = new ResponseVisitor();

    rv.__( (ReadResponse readResponse) -> {
      SerializableOptional<Integer> result = readResponse.getValue();
      if (result.isPresent()) {
        System.out.print("Read result with value: " + result.get() + ".");
      } else {
        System.out.println("Read response: Unknown key!");
      }
    }, (StoreResponse storeResponse) -> {
      System.out.println("Store successful!");
    });

    System.out.println("1: Read\n2: Store\n3: Exit\n");

    Scanner scanner = new Scanner(System.in);

    int input = scanner.nextInt();

    try {

      while (input != 3) {

        IRequest request = new ReadRequest("Dummy request");

        switch (input) {

          case 1 :

            System.out.print("Key: ");

            request = new ReadRequest(scanner.next());

            break;
          case 2 :

            System.out.print("Key and value: ");

            request = new StoreRequest(scanner.next(), scanner.nextInt());

            break;
          default :
            break;

        }

        sendToMaster(masterOutputStream, request);

        IResponse response = waitForResponse(masterInputStream);
        response.accept(rv);

        input = scanner.nextInt();

      }

      socket.close();

    } catch(Exception e) { System.out.println("Should have entered a number"); }

  }

  private static void sendToMaster(ObjectOutputStream out, Object object) {

    try {

      out.writeObject(object);

      out.flush();

    } catch (Exception e) { e.printStackTrace(); }

  }

  private static IResponse waitForResponse(ObjectInputStream in) {

    try {
      return (IResponse) in.readObject();
    } catch (Exception e) { e.printStackTrace(); }

    return null;

  }

}

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MaubaTest {

  @Test
  public void testTestData() throws IOException {
    File folder = new File("testdata/");
    File[] listOfFiles = folder.listFiles();
    boolean atLeastOne = false;
    assertTrue("Folder does not exist!", listOfFiles != null);
    for (int i = 0; i < listOfFiles.length; i++) {
      Pattern p = Pattern.compile("testdata(/|\\\\)sample(?<sample>\\d*).in");//Danke @C. Neuhauser
      Matcher m = p.matcher(listOfFiles[i].toString());
      boolean matches = m.matches();
      if (!matches)
        continue;
      int sample = Integer.parseInt(m.group("sample"));
      atLeastOne = true;
      
      System.out.println("Testing " + listOfFiles[i].toString());

      Path sampleOutPath = FileSystems.getDefault().getPath("testdata/sample" + sample + ".out");
      String solutionTree = new String(
          Files.readAllBytes(sampleOutPath));

      Scanner scanner = new Scanner(listOfFiles[i]);

      int a = scanner.nextInt();
      int b = scanner.nextInt();
      ABTree abt = new ABTree(a, b);

      StringBuilder result = new StringBuilder();

      cmd_loop: while (true) {
        String cmd = scanner.next();
        switch (cmd) {
        case "exit":
          break cmd_loop;
        case "insert":
          int value = scanner.nextInt();
          abt.insert(value);
          result.append(abt.dot());
          result.append('\n');
          break;
        case "remove":
          value = scanner.nextInt();
          abt.remove(value);
          result.append(abt.dot());
          result.append('\n');
          break;
        default:
          throw new RuntimeException("Invalid command");
        }
      }
      scanner.close();
      assertEquals(solutionTree, result.toString());
    }
    assertTrue("No test cases!", atLeastOne);
  }
}

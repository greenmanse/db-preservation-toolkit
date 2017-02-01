import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.XML;

/**
 * Created on 1/27/17
 *
 * @author Scott Greenman
 */
public class Xml2Json {
  public static void main(String[] args) throws IOException {
    StringBuilder builder = new StringBuilder(2048);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line;
    do {
      line = reader.readLine();
      if (line != null) {
        builder.append(line);
      }
    }
    while(line != null);

    JSONObject jsonObject = XML.toJSONObject(builder.toString());
    System.out.println(jsonObject.toString(2));
  }
}

import java.util.Map;
import java.util.HashMap;
import com.amazonaws.services.lambda.runtime.Context;

public class Handler {
  public Map<String, Object> test(Map<String, Object> input, Context context) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("message", "Hello, " + input.get("name"));
    return response;
  }
}

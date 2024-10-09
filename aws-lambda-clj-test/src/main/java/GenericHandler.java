import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context;

import clojure.java.api.Clojure;
import clojure.lang.IFn;


public class GenericHandler implements RequestStreamHandler {
  public void handleRequest(InputStream inputStream,
                            OutputStream outputStream,
                            Context context) {

    System.out.println("Input class: " + inputStream.getClass().getName());

    IFn requireFn = Clojure.var("clojure.core", "require");
    requireFn.invoke(Clojure.read("aws-lambda-clj-test.core"));

    IFn handleFn = Clojure.var("aws-lambda-clj-test.core", "handle-request");
    handleFn.invoke(inputStream, outputStream, context);
  }
}

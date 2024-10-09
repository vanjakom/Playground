(ns aws-lambda-clj-test.handlers.test)

(defn handler [input-stream output-stream context]
  (println "executing aws-lambda-clj-test.handler/test")
  (.write
    output-stream
    (.getBytes "aws-lambda-clj-test.handler/test ok")))

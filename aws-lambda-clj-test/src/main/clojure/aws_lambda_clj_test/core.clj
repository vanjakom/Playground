(ns aws-lambda-clj-test.core)

(require '[clj-common.jvm :as jvm])

(defn handle-request [input-stream output-stream context]
  (println "Env variables")
  (doseq [[key value] (jvm/environment-variables)]
    (println "Env:" key "value:" value))

  (let [fn-to-exec-name (symbol (jvm/environment-variable "fn"))
        ns-to-exec-in (symbol (jvm/environment-variable "ns"))]
    (require ns-to-exec-in)
    (let [fn (ns-resolve ns-to-exec-in fn-to-exec-name)]
      (fn input-stream output-stream context))))


(ns aws-lambda-clj-test.classloader-test)

(require 'clj-common.localfs)
(require 'clj-common.path)
(require 'clj-common.http-server)
(require 'ring.middleware.params)
(require 'ring.middleware.keyword-params)

(comment
  (let [old-loader @clojure.lang.Compiler/LOADER]
    (with-bindings {
                     clojure.lang.Compiler/LOADER
                     (proxy
                       [java.lang.ClassLoader] []
                       (getResource
                         [name]
                         (println "getting:" name)
                         (.getResource old-loader name)))}
      (require 'aws-lambda-clj-test.simple-namespace :reload-all)))
)

(comment
  (require 'clj-common.localfs)
  (require 'clj-common.path)
  (let [old-loader @clojure.lang.Compiler/LOADER
        root-path ["Users" "vanja" "projects" "playground" "aws-lambda-clj-test" "src" "main" "clojure"]]
    (with-bindings {
                     clojure.lang.Compiler/LOADER
                     (proxy
                       [java.lang.ClassLoader] []
                       (getResource
                         [name]
                         (println "getting resource:" name)
                         (.getResource old-loader name))
                       (getResourceAsStream
                         [name]
                         (println "getting resource as stream:" name)
                         (let [path (apply
                                      clj-common.path/child
                                      root-path
                                      (clj-common.path/string->path name))]
                           (println "looking into:" (clj-common.path/path->string path))
                           (if
                             (clj-common.localfs/exists? path)
                             (do
                               (println "loading from dynamic storage")
                               (clj-common.localfs/input-stream path))
                             (.getResourceAsStream old-loader name)))))}
      (require 'aws-lambda-clj-test.simple-namespace :reload-all)
      (aws-lambda-clj-test.simple-namespace/a)))
)


; (prepare-fn
;   ["Users" "vanja" "projects" "playground" "aws-lambda-clj-test" "src" "main" "clojure"]
;   "aws-lambda-clj-test.simple-namespace"
;   "a")
(defn prepare-fn [root-live-path ns fn]
  (let [old-loader @clojure.lang.Compiler/LOADER]
    (with-bindings {
                     clojure.lang.Compiler/LOADER
                     (proxy
                       [java.lang.ClassLoader] []
                       ;(getResource
                       ;  [name]
                       ;  (println "getting resource:" name)
                       ;  (.getResource old-loader name))
                       (getResourceAsStream
                         [name]
                         (println "getting resource as stream:" name)
                         (let [path (apply
                                      clj-common.path/child
                                      root-live-path
                                      (clj-common.path/string->path name))]
                           (println "looking into:" (clj-common.path/path->string path))
                           (if
                             (clj-common.localfs/exists? path)
                             (do
                               (println "loading from dynamic storage")
                               (clj-common.localfs/input-stream path))
                             (.getResourceAsStream old-loader name)))))}
      (require (symbol ns) :reload-all)
      (ns-resolve (symbol ns) (symbol fn)))))

(def handler
  (compojure.core/routes
    (compojure.core/GET
      "/test"
      _
      (ring.middleware.json/wrap-json-params
        (ring.middleware.keyword-params/wrap-keyword-params
          (fn [request]

            (try
              (let [fn-to-exec
                    (aws-lambda-clj-test.classloader-test/prepare-fn
                      ["Users" "vanja" "projects" "playground" "aws-lambda-clj-test" "src" "main" "clojure"]
                      "aws-lambda-clj-test.simple-namespace"
                      "a")]
                {
                  :status 200
                  :body (fn-to-exec)})
              (catch Exception e
                (.printStackTrace e)
                {
                  :status 500}))))))))


(defn start-server []
  (clj-common.http-server/create-server
    7081
    #'handler))

(comment
  (def a
    (aws-lambda-clj-test.classloader-test/prepare-fn
      ["Users" "vanja" "projects" "playground" "aws-lambda-clj-test" "src" "main" "clojure"]
      "aws-lambda-clj-test.simple-namespace"
      "a"))
)

(comment
  ((aws-lambda-clj-test.classloader-test/prepare-fn
      ["Users" "vanja" "projects" "playground" "aws-lambda-clj-test" "src" "main" "clojure"]
      "aws-lambda-clj-test.simple-namespace"
      "a"))
)

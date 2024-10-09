(defproject aws-lambda-clj-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]

  :dependencies [
                  [org.clojure/clojure "1.8.0"]
                  [lein-light-nrepl "0.3.2"]

;                  [com.mungolab/clj-common "0.1.0-SNAPSHOT" :exclusions [*]]
;                  [com.mungolab/clj-aws "0.1.0-SNAPSHOT" :exclusions [*]]

                  [com.mungolab/clj-common "0.1.0-SNAPSHOT"]
                  [com.mungolab/clj-aws "0.1.0-SNAPSHOT"]

                  [com.amazonaws/aws-lambda-java-core "1.1.0"]]
  :repl-options {
                  :nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})

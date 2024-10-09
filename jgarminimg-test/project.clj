(defproject jgarminimg-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [
    ["Patrick Valsecchi's repository" "http://www.thus.ch/~patrick/maven"]]
  :dependencies [
                  [org.clojure/clojure "1.8.0"]
                  [lein-light-nrepl "0.3.2"]

                  [com.mungolab/clj-common "0.1.0-SNAPSHOT"]

                  [ch.thus/jgarminimg "1.2"]]
  :repl-options {
                  :nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})

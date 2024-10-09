(ns storm-feeds.TopologySubmitter
  (:require [storm-feeds.topology :refer [storm-topology]]
            [backtype.storm [config :refer :all]])
  (:import [backtype.storm StormSubmitter])
  (:gen-class))

(defn -main [& {debug "debug" workers "workers" :or {debug "false" workers "4"}}]
  (StormSubmitter/submitTopology
   "storm-feeds topology"
   {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
    TOPOLOGY-WORKERS (Integer/parseInt workers)}
   (storm-topology)))

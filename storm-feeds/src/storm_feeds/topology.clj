(ns storm-feeds.topology
  "Topology

More info on the Clojure DSL here:

https://github.com/nathanmarz/storm/wiki/Clojure-DSL"
  (:require [storm-feeds
             [spouts :refer [type-spout]]
             [bolts :refer [stormy-bolt storm-feeds-bolt]]]
            [backtype.storm [clojure :refer [topology spout-spec bolt-spec]] [config :refer :all]])
  (:import [backtype.storm LocalCluster LocalDRPC]))

(defn storm-topology []
  (topology
   {"spout" (spout-spec type-spout)}

   {"stormy-bolt" (bolt-spec {"spout" ["type"]} stormy-bolt :p 2)
    "storm-feeds-bolt" (bolt-spec {"stormy-bolt" :shuffle} storm-feeds-bolt :p 2)}))

(defn run! [& {debug "debug" workers "workers" :or {debug "true" workers "2"}}]
  (doto (LocalCluster.)
    (.submitTopology "my first topology"
                     {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
                      TOPOLOGY-WORKERS (Integer/parseInt workers)}
                     (storm-topology))))

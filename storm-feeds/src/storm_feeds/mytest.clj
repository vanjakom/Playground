(ns storm-feeds.mytest)

(require '[backtype.storm [clojure :refer [defspout spout emit-spout! emit-bolt! defbolt ack! bolt]]])
(require '[backtype.storm [clojure :refer [topology spout-spec bolt-spec]] [config :refer :all]])
(import [backtype.storm LocalCluster LocalDRPC])

(defspout event-spout ["event"]
  [conf context collector]
  (let [events [{:action :commented, :user :travis, :listing :red-shoes}
                {:action :liked, :user :jim, :listing :red-shoes}
                {:action :liked, :user :karen, :listing :green-hat}
                {:action :liked, :user :rob, :listing :green-hat}
                {:action :commented, :user :emma, :listing :green-hat}]]
    (spout
      (nextTuple []
                 (Thread/sleep 1000)
                 (emit-spout! collector [(rand-nth events)])))))

(defbolt active-user-bolt ["user" "event"] [{event "event" :as tuple} collector]
  (doseq [user [:jim :rob :karen :kaitlyn :emma :travis]]
    (emit-bolt! collector [user event]))
  (ack! collector tuple))


(defn storm-topology []
  (topology
    {"events" (spout-spec event-spout)}
    {"active users" (bolt-spec {"events" :shuffle} active-user-bolt :p 2)}))

(defn run! [& args]
  (doto (LocalCluster.)
    (.submitTopology "my first topology"
                     {TOPOLOGY-DEBUG true
                      TOPOLOGY-WORKERS 1}
                     (storm-topology))))

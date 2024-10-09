(ns cascalog-playground.core)

(require '[cascalog.api :refer :all])
(require '[cascalog.more-taps :refer [hfs-delimited]])
(require '[clojure.data.json :as json])


(defn get-vec
  "Wrap the result in a vector for Cascalog to consume." [m k]
  (vector
    (get m k)))


(defn vec->csv
  "Turn a vector into a CSV string. (Not production quality)." [v]
  (apply str (interpose "," v)))

(defmain test1 [in out & args]
  (?<-
    (hfs-textline out :sinkmode :replace)
    [?out-csv]
    ((hfs-textline in) ?in-json)
    (json/read-str ?in-json :> ?book-map)
    (get-vec ?book-map "authors" :> ?authors)
    (vec->csv ?authors :> ?out-csv)))

(comment
  (test1 "resources/books.json" "/tmp/out")
)

(defn init-aggregate-stats [date url user]
  (let [day (.substring date 0 8)]
    {"URL"  {url 1}
     "User" {user 1}
     "Day"  {date 1}}))


(def combine-aggregate-stats
  (partial merge-with (partial merge-with +)))

(defparallelagg aggregate-stats
  :init-var    #'init-aggregate-stats
  :combine-var #'combine-aggregate-stats)


(defmain test2 [in out & args]
  (?<-
    (hfs-textline out :sinkmode :replace)
    [?out]
    ((hfs-delimited in :delimiter ",") ?date ?url ?user)
    (aggregate-stats ?date ?url ?user :> ?out)))


(comment
  (test2 "resources/posts.csv" "/tmp/out")
)



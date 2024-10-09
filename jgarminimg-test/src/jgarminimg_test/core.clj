(ns jgarminimg-test.core)

(import java.io.File)
(import org.free.garminimg.ImgFilesBag)
(import org.free.garminimg.MapListener)
(import org.free.garminimg.ObjectKind)

(comment
  (def img-bag (new ImgFilesBag))

  ;(.addFile img-bag (new File "/Users/vanja/Downloads/geocaches/geocaches.img"))

  (.addDirectory img-bag (new File "/Users/vanja/Downloads/geocaches/"))

  ;(.addDirectory img-bag (new File "/Users/vanja/Downloads/confluence/"))


  (def listener (proxy
                  [MapListener]
                  []
                  (addPoint
                    [type sub-type longitude latitude label indexed]
                    (println "-> point: " longitude "/" latitude "label: " (.getName label)))
                  (addPoly
                    [type longitudes latitudes number-points label line]
                    (println "-> poly: " (if (some? label) (.getName label) "unknown") "num points: " number-points)
                    (doseq [longitude longitudes latitude latitudes]
                      (println "-> poly point: "  longitude " / " latitude)))
                  (startMap
                    [img-bag]
                    (println "-> map started"))
                  (startSubDivision
                    [sub-division]
                    (println "-> subdivision started"))))


  (println "->fresh start")
  (.readMap img-bag -90 -180 90 180 -1 ObjectKind/ALL nil listener)


)

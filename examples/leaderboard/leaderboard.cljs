(ns leaderboard
  (:require-macros 
    [mrhyde.jso :refer [jso]])
  (:require 
    [shower :refer [Meteor Collection Session Template]]))

(shower/bootstrap)

; Set up a collection to contain player information. On the server,
; it is backed by a MongoDB collection named "players".

(def Players (Collection. "players"))

(when (-> Meteor .-isClient)
  (aset (-> Template .-leaderboard) "players" (fn []
      (-> Players (.find (jso {}) {:sort {:score -1 
                                          :name   1}}))))
  (aset (-> Template .-leaderboard) "selected_name" (fn []
      (if-let [p (-> Players (.findOne (-> Session (.get "selected_player"))))]
        (aget p "name")
        nil)))

  (aset (-> Template .-player) "selected" (fn []
      (this-as ct
        (if (-> Session (.equals "selected_player" (-> ct .-_id)))
          "selected"
          ""))))

  (-> Template .-leaderboard (.events (clj->js {
    "click input.inc" (fn [] 
      (-> Players (.update (-> Session (.get "selected_player")) 
                           (jso {"$inc" {:score 5}}))))
    })))

  (-> Template .-player (.events {
    :click (fn [] (this-as ct (-> Session (.set "selected_player" (-> ct .-_id)))))
    }))
)

(def names ["Ada Lovelace"
            "Grace Hopper"
            "Marie Curie"
            "Carl Friedrich Gauss"
            "Nikola Tesla"
            "Claude Shannon"])

(when (-> Meteor .-isServer)
  (-> Meteor (.startup (fn []
      (when (zero? (-> Players (.find) (.count)))
        (doseq [n names]
          (-> Players (.insert (jso {:name n :score (* 5 (rand-int 10))})))))
    )))
)


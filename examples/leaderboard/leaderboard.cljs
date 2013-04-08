(ns leaderboard
  (:require-macros 
    [hiccups.core :as hiccups]
    [mrhyde.reader])
  (:require 
    [hiccups.runtime :as hiccupsrt]
    [mrhyde.extend-js :refer [assoc-in!]]
    [shower :refer [Meteor Collection Session Template]]))

(shower/bootstrap)

(def Players (Collection. "players"))

(when (:isClient Meteor)
  (assoc-in! Template [:leaderboard :players] (fn []
    (-> Players (.find (js-obj) {:sort {:score -1 :name 1}}))))

  (assoc-in! Template [:leaderboard :selected_name] (fn []
    (if-let [player (-> Players (.findOne 
                      (-> Session (.get "selected_player"))))]
      (:name player))))

  (assoc-in! Template [:player :selected] (fn []
    (if (-> Session (.equals "selected_player" 
        (this-as t (aget t "_id"))))
      "selected"
      "")))

  (-> Template :leaderboard (.events (clj->js {
    "click input.inc" (fn []
      (-> Players (.update (-> Session (.get "selected_player"))
                           {"$inc" {"score" 5}})))
    })))

  (-> Template :player (.events (clj->js {
    "click" (fn []
      (-> Session (.set "selected_player" (this-as t (aget t "_id")))))
    }))))

(def names ["Ada Lovelace"
            "Grace Hopper"
            "Marie Curie"
            "Carl Friedrich Gauss"
            "Nikola Tesla"
            "Claude Shannon"])
 
(when (:isServer Meteor)
  (-> Meteor (.startup (fn []
    (when (zero? (-> Players .find .count))
      (doseq [name names]
        (-> Players (.insert {
          :name name
          :score (* 5 (rand-int 10))
        }))))))))
; (when (-> Meteor .-isServer)
;   (-> Meteor (.startup (fn []
;     ; startup code...
;     )))

;   (-> Meteor (.methods {
;     :clear #(-> Game (.remove {:empty nil}))
;     :remove #(-> Game (.remove {:index %}))
;     }))
; )
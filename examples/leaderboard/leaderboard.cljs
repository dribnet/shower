(ns leaderboard
  (:require 
    [mrhyde.extend-js :refer [assoc-in! update-in!]]
    [shower :refer [make-collection template-events template-assoc Session]]))

(shower/bootstrap)

; Set up a collection to contain player information. On the server,
; it is backed by a MongoDB collection named "players".

(def players (make-collection "players"))

(when (shower/is-client?)
  (template-assoc [:leaderboard :players] 
    (fn []
      (.find players {} {:sort {:score -1 :name 1}})))

  (template-assoc [:leaderboard :selected_name]
    (fn []
      (if-let [p (.findOne players (.get Session "selected_player"))]
        (:name p)
        nil)))

  (template-assoc [:player :selected]
    (fn []
      (this-as ct
        (if (.equals Session "selected_player" (:_id ct))
          "selected"
          ""))))

  (template-events :leaderboard "click input.inc"
    (fn []
      (.update players (.get Session "selected_player") {:$inc {:score 5}})))

  (template-events :player "click"
    (fn [] 
      (this-as ct (.set Session "selected_player" (:_id ct))))))

(def names ["Ada Lovelace"
            "Grace Hopper"
            "Marie Curie"
            "Carl Friedrich Gauss"
            "Nikola Tesla"
            "Claude Shannon"])

(when (shower/is-server?)
  (shower/startup 
    (fn []
      (when (zero? (-> players (.find) (.count)))
        (doseq [n names]
          (.insert players {:name n :score (* 5 (rand-int 10))}))))))

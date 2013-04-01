(ns color-magic
  (:require-macros [hiccups.core :as hiccups])
  (:require [hiccups.runtime :as hiccupsrt]
            [shower :refer [bootstrap 
                            Meteor Collection Session Template]]))

(bootstrap)

(def Game (Collection. "game"))
(-> Game (.allow {
  :insert (fn [userId z] true)
  :remove (fn [userId z] true)
  :update (fn [userId z] true)}))

(defn grid-class-from-id [id]
  (if-let [element (-> Game (.findOne {:index id}))]
    (do ;(.log js/console (.-color element))
        (str "q" (.-color element) "-20"))
    "empty"))

(when (-> Meteor .-isClient)
  (-> Meteor (.startup (fn []
    (-> Session (.set "mycolor" (rand-int 20))))))

  (aset (-> Template .-grid) "gridMaker" (fn [a b classname]
    ; (.log js/console "gridmaker")
    (hiccups/html
      [:table {:class classname}
      (for [i (range a)]
        [:tr
          (for [j (range b)]
            (let [id (str i "-" j)]
              [:td {:id id
                    :class (grid-class-from-id id)}]))])])))

  (-> Template .-grid (.events (clj->js {
    "click .grid td" (fn [e]
      (let [id (-> e .-currentTarget .-id)
            time (.getTime (js/Date.))]
        (if (empty? (-> Game (.find {:index id}) (.fetch)))
          (-> Game (.insert {
            :index id
            :color (-> Session (.get "mycolor"))
            :time time }))
          (-> Meteor (.call "remove" id)))))
    "click #clear" (fn [e]
      ; (.log js/console "clear click")
      (-> Meteor (.call "clear")))
  })))
)

(when (-> Meteor .-isServer)
  (-> Meteor (.startup (fn []
    ; startup code...
    )))

  (-> Meteor (.methods {
    :clear #(-> Game (.remove {:empty nil}))
    :remove #(-> Game (.remove {:index %}))
    }))
)
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

(def colors [
  "#1f77b4" "#aec7e8" "#ff7f0e" "#ffbb78"
  "#2ca02c" "#98df8a" "#d62728" "#ff9896"
  "#9467bd" "#c5b0d5" "#8c564b" "#c49c94"
  "#e377c2" "#f7b6d2" "#7f7f7f" "#c7c7c7"
  "#bcbd22" "#dbdb8d" "#17becf" "#9edae5"
])

(when (-> Meteor .-isClient)
  (-> Meteor (.startup (fn []
    (-> Session (.set "mycolor" (rand-nth colors))))))

  (aset (-> Template .-grid) "gridMaker" (fn [a b classname]
    (hiccups/html
      [:table {:class classname}
      (for [i (range a)]
        [:tr
          (for [j (range b)]
            (let [id (str i "-" j)]
              [:td {:id id
                    :class (-> Template .-grid (.style id))
                    :style (str "background-color:" 
                      (-> Template .-grid (.color id)))}]))])])))

  (aset (-> Template .-grid) "style" (fn [id]
    (if (empty? (-> Game (.find {:index id}) (.fetch)))
      "selected"
      "")))

  (aset (-> Template .-grid) "color" (fn [id]
    (if-let [element (-> Game (.findOne {:index id}))]
      (.-color element)
      "white")))

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
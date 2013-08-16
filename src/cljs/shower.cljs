(ns shower
  (:require 
    [mrhyde.extend-js :refer [assoc-in! update-in!]]
    [mrhyde.core :as mrhyde]))

(def Meteor (this-as ct (aget ct "Meteor")))
(def Template (this-as ct (aget ct "Template")))
(def Session (this-as ct (aget ct "Session")))
(def Collection (aget Meteor "Collection"))

(defn make-collection [n]
  (Collection. n))

(defn is-client? [] (:isClient Meteor))
(defn is-server? [] (:isServer Meteor))
(defn startup [f] (.startup Meteor f))

(defn template-assoc [v f]
  (assoc-in! Template v f))

(defn template-events [k s f]
  (.events (k Template) {(keyword s) f}))

(defn session-get [k]
  (.get Session k))

(defn session-assoc [k o]
  (.set Session k o))

(defn session-assoc [k o]
  (.equals Session k o))

; (this-as ct (assoc! ct :checkit is-client?))

(defn ^:export bootstrap []
  (mrhyde/bootstrap))

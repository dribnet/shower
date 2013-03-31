(ns shower
  (:require [mrhyde.core :as mrhyde]))

(def Meteor (this-as ct (aget ct "Meteor")))
(def Template (this-as ct (aget ct "Template")))
(def Session (this-as ct (aget ct "Session")))
(def Collection (aget Meteor "Collection"))

(defn ^:export bootstrap []
  (mrhyde/bootstrap))

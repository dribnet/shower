(ns shower
  (:require [mrhyde.core :as mrhyde]))

(def Meteor (this-as ct (aget ct "Meteor")))

(defn ^:export bootstrap []
  (mrhyde/bootstrap))

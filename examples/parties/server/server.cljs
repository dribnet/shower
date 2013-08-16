(ns parties.server
  (:require 
    [mrhyde.extend-js :refer [assoc-in! update-in!]]
    [shower :refer [Meteor]]))

(shower/bootstrap)

(.publish Meteor "directory"
    (fn []
      (.find (:users Meteor) {} {:fields {:emails 1 :profile 1}})))

(.publish Meteor "parties"
    (fn []
      (this-as ct
        (.find (:Parties ct)
          {:$or [{:public true} {:invited (:userId ct)} {:owner (:userId ct)}]}))))

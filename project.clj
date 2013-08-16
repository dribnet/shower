(defproject shower "0.0.1-SNAPSHOT"
  :description "shower: experimental meteorjs library"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccups "0.2.0"]
                 [net.drib/mrhyde "0.5.3-SNAPSHOT"]]
  :min-lein-version "2.0.0"
  :source-paths ["src/clj" "src/cljs"]

  :plugins [[lein-cljsbuild "0.3.2"]]

  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler  {:optimizations :simple
                                    :pretty-print true
                                    :output-to "out/shower.js"}
                        :jar true}

                      ; examples
                       ; not working?
                       ; {:source-paths ["src/cljs" "examples/color-magic"]
                       ;  :compiler  {:optimizations :simple
                       ;              :pretty-print true
                       ;              :output-to "color-magic/color-magic.js"}}
                       {:source-paths ["src/cljs" "examples/leaderboard"]
                        :compiler  {:optimizations :simple
                                    :pretty-print true
                                    :output-to "leaderboard/leaderboard.js"}}                                    
                                    ]})

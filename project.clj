(defproject shower "0.0.1-SNAPSHOT"
  :description "shower: experimental meteorjs library"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [hiccups "0.2.0"]
                 [net.drib/strokes "0.4.1"]]
  :min-lein-version "2.0.0"
  :source-paths ["src/clj" "src/cljs"]

  :plugins [[lein-cljsbuild "0.3.0"]]

  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler  {:optimizations :simple
                                    :pretty-print true
                                    :output-to "out/shower.js"}
                        :jar true}

                      ; examples
                       {:source-paths ["src/cljs" "examples/color-magic"]
                        :compiler  {:optimizations :simple
                                    :pretty-print true
                                    :output-to "color-magic/color-magic.js"}}
                                    ]})

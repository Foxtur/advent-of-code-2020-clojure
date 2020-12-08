(ns aoc.day03
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:gen-class))

(defn read-input [path]
  (-> (slurp (io/resource path))
      s/split-lines))

(defn solve [input right down]
  (let [width (count (first input))
        columns (map #(mod % width) (iterate #(+ % right) 0))
        spots (map nth input (take-nth down columns))
        trees (filter #(= \# %) spots)]
    (count trees)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [input (read-input "input_day03.txt")]
    (println "Solution Part 01:" (solve input 3 1))
    (println "Solution Part 02:" (* (solve input 1 1)
                                    (solve input 3 1)
                                    (solve input 5 1)
                                    (solve input 7 1)
                                    (solve input 1 2)))))

(comment
  (-main)
  (solve (read-input "input_day03.txt") 3 1)
  (first (read-input "input_day03.txt")))

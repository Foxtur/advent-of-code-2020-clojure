(ns aoc.day03
  (:require [clojure.string :as s])
  (:gen-class))

(defn read-input [path]
  (-> (slurp path)
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
  (let [input (read-input "input01.txt")]
    (println "Solution Part 01:" (solve input 3 1))
    (println "Solution Part 02:" (* (solve input 1 1)
                                    (solve input 3 1)
                                    (solve input 5 1)
                                    (solve input 7 1)
                                    (solve input 1 2)))))

(comment
  (-main)
  (solve (read-input "input01.txt") 3 1)
  (first (read-input "input01.txt")))

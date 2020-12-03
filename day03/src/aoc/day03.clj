(ns aoc.day03
  (:require [clojure.string :as s])
  (:gen-class))

(defn read-input [path]
  (-> (slurp path)
      s/split-lines))

(defn solve [input right down]
  (let [rowcount (count input)]
    (loop [col 0
           row 0
           collisions 0]
      (if (< row rowcount)
        (let [current-row (nth input row)
              char (nth current-row col)
              tree? (= \# char)]
          (recur
           (rem (+ col right) (count (nth input row)))
           (+ row down)
           (if tree? (inc collisions) collisions)))
        collisions))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [input (read-input "input01.txt")]
    (println "Solution Part 01:" (solve input 3 1))
    (println "Solution Part 02:"
             (reduce *
                     (map
                      #(solve input (first %) (second %))
                      [[1 1] [3 1] [5 1] [7 1] [1 2]])))))

(comment
  (-main)
  (solve (read-input "input01.txt") 3 1)
  (first (read-input "input01.txt")))

(ns aoc.day01
  (:require [clojure.string :as s]
            [clojure.java.io :as io])
  (:gen-class))

(defn read-input
  "Reads and parses input from the given file `file-path`
   and returns a sequence of integers"
  [file-path]
  (->> (slurp (io/resource file-path))
       (s/split-lines)
       (map #(Integer/parseInt %))
       (vec)))

(def combinations-part01
  (let [input (read-input "input_day01_01.txt")]
    (for [x1 input
          x2 input]
      [x1 x2])))

(def combinations-part02
  (let [input (read-input "input_day01_02.txt")]
    (for [x1 input
          x2 input
          x3 input]
      [x1 x2 x3])))

(defn solve
  "Returns teh product of a combination which adds up to 2020"
  [combinations]
  (reduce * (first (filter #(= 2020 (reduce + %)) combinations))))

(defn -main
  "Prints out the solution of AoC 2020 day01"
  [& args]
  (println "Solution part01:" (solve combinations-part01))
  (println "Solution part02:" (solve combinations-part02)))

(comment
  (-main))
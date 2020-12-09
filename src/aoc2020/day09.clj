(ns aoc.day09
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-input []
  (->> (slurp (io/resource "input_day09.txt"))
       str/split-lines
       (map #(Long/parseLong %))
       vec))

(def actual-input (read-input))

(def test-input
  (->> "35\n20\n15\n25\n47\n40\n62\n55\n65\n95\n102\n117\n150\n182\n127\n219\n299\n277\n309\n576\n"
       str/split-lines
       (map #(Long/parseLong %))
       vec))

(defn valid-number? [preamble n]
  (let [combinations (vec (filter #(not= (first %) (second %)) (for [x preamble y preamble] [x y])))
        sums (set (map (fn [[x y]] (+ x y)) combinations))]
    (contains? sums n)))

(defn walk-input [input preamble-size]
  (->> input
       (partition (inc preamble-size) 1)
       (filter #((complement valid-number?) % (last %)))
       first
       last))

(defn solve-part01 []
  (walk-input actual-input 25))

(defn print-solution []
  (println "Solution part01:" (solve-part01)))

(defn -main [& args]
  (print-solution))

(comment
  (-main))

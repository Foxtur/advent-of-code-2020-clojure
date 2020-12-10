(ns aoc.day09
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-input []
  (->> (slurp (io/resource "input_day09.txt"))
       str/split-lines
       (mapv #(Long/parseLong %))))

(def actual-input (read-input))

(def test-input
  (->> "35\n20\n15\n25\n47\n40\n62\n55\n65\n95\n102\n117\n150\n182\n127\n219\n299\n277\n309\n576\n"
       str/split-lines
       (mapv #(Long/parseLong %))))

(defn valid-number? [preamble n]
  (let [combinations (vec (filter #(not= (first %) (second %)) (for [x preamble y preamble] [x y])))
        sums (set (map (fn [[x y]] (+ x y)) combinations))]
    (contains? sums n)))

(defn find-contiguous-set [input n]
  (loop [s 0]
    (let [end-element (reduce (fn [a b] (let [sum (+ a b)]
                                     (cond
                                       (< sum n) sum
                                       (= sum n) (reduced b)
                                       :else (reduced false))))
                         (drop s input))]
      (if end-element
        (set (subvec input s (inc (.indexOf input end-element))))
        (recur (inc s))))))

(defn find-invalid-input [input preamble-size]
  (->> input
       (partition (inc preamble-size) 1)
       (filter #((complement valid-number?) % (last %)))
       first
       last))

(defn solve-part01 []
  (find-invalid-input actual-input 25))

(defn solve-part02 []
  (let [contiguous-set (find-contiguous-set actual-input 104054607)
        min-val (apply min contiguous-set)
        max-val (apply max contiguous-set)]
    (+ min-val max-val)))

(defn print-solution []
  (println "Solution part01:" (solve-part01))
  (println "Solution part02:" (solve-part02)))

(defn -main [& args]
  (print-solution))

(comment
  (-main))

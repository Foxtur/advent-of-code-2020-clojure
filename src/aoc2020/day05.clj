(ns aoc2020.day05
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn read-input []
  (with-open [rdr (io/reader (io/resource "input_day05.txt"))]
    (->> rdr
         line-seq
         vec)))

(defn resolve-seat-id [code]
  (let [binary-str (->> code
                        (map #(case % \F 0 \B 1 \L 0 \R 1))
                        (apply str))
        row (Integer/parseInt (subs binary-str 0 7) 2)
        col (Integer/parseInt (subs binary-str 7) 2)]
    (+ col (* row 8))))

(defn solve-part01 []
  (->> (read-input)
       (map resolve-seat-id)
       (apply max)))

(defn solve-part02 []
  (->> (read-input)
       (map resolve-seat-id)
       (sort)
       (reduce #(if (= 1 (- %2 %1)) %2 %1))
       inc))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Solution part01:" (solve-part01))
  (println "Solution part01:" (solve-part02)))

(comment
  (-main)

  ;; part01 binary seq conversion alternative
  (->> (read-input)
       (first)
       (map #(case % \F 0 \B 1 \L 0 \R 1))
       (reduce (fn [acc b]
                 (-> (bit-shift-left acc 1)
                     (bit-or b)))))

  (->> (read-input)
       (first)
       (resolve-seat-id))

  ;; part02 alternative solution
  (->> (read-input)
       (map resolve-seat-id)
       (sort)
       (partition 2)
       (some (fn [[l h]] (when (= (- h l) 2) (inc l)))))

  ;; find missing number in sequence
  (reduce #(if (= 1 (- %2 %1)) %2 %1) [1 2 3 4 6]))

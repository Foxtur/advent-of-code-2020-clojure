(ns aoc.day05
  (:require [clojure.java.io :as io])
  (:gen-class))

;; 128 rows nubmered 0 - 127
;; 8 columns numbered 0 - 7

;; front -> row 0 - 63
;; back ->  row 64 - 127

;; L - lower half
;; R - upper half

(def test-input "FBFBBFFRLR")

(defn read-input []
  (with-open [rdr (io/reader "input.txt")]
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

  ;; find missing number in sequence
  (reduce #(if (= 1 (- %2 %1)) %2 %1) [1 2 3 4 6]))

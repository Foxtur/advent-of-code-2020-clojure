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

(defn resolve-seat-id [instructions]
  (loop [path instructions
         rmin 0
         rmax 127
         cmin 0
         cmax 7]
    (if (empty? path)
      (+ cmin (* rmin 8))
      (let [op (first path)]
        (condp = op
          \F (recur (rest path) rmin (+ rmin (quot (- rmax rmin) 2)) cmin cmax)
          \B (recur (rest path) (inc (+ rmin (quot (- rmax rmin) 2))) rmax cmin cmax)
          \L (recur (rest path) rmin rmax cmin (+ cmin (quot (- cmax cmin) 2)))
          \R (recur (rest path) rmin rmax (inc (+ cmin (quot (- cmax cmin) 2))) cmax))))))

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

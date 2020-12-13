(ns aoc.day13
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-input []
  (->> (slurp (io/resource "input_day13"))
       str/split-lines
       ((fn [[ts busstring]]
          (let [buses (->> (str/split busstring #",")
                           (filter #(not= "x" %))
                           (mapv #(Integer/parseInt %)))
                ts (Integer/parseInt ts)]
            {:ts ts :buses buses})))))

(def test-input
  (->> "939\n7,13,x,x,59,x,31,19"
       str/split-lines
       ((fn [[ts busstring]]
          (let [buses (->> (str/split busstring #",")
                           (filter #(not= "x" %))
                           (mapv #(Integer/parseInt %)))
                ts (Integer/parseInt ts)]
            {:ts ts :buses buses})))))

(defn time-slots [bus-id]
  (iterate #(+ bus-id %) 0))

(defn next-departure [time bus-id]
  (+ bus-id (last (take-while #(>= time %) (time-slots bus-id)))))

(defn assoc-departures [{:keys [ts buses] :as m}]
  (assoc m
         :next-departures
         (mapv #(next-departure ts %) buses)))

(defn bus-to-take [schedule]
  (let [schedule (assoc-departures schedule)
        wait-times (map #(- % (:ts schedule)) (:next-departures (assoc-departures schedule)))
        shortest-wait (apply min wait-times)]
    (println "wait times" wait-times)
    (->> wait-times
         (keep-indexed (fn [i x] (if (= shortest-wait x) [i x] nil)))
         first
         ((fn [[idx minutes]] [(nth (:buses schedule) idx) minutes])))))

(defn solve-part01 []
  (let [[bus minutes] (bus-to-take (read-input))]
    (* bus minutes)))

(defn print-solutions []
  (println "Solution part01:" (solve-part01)))

(ns aoc.day10
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def demo-input [16 10 15 5 1 11 7 19 6 12 4])

(def demo-input-2 [28 33 18 42 31 14 46 20 48 47 24 23 49 45 19 38 39 11 1 32 25 35 8 17 7 9 4 2 34 10 3])

(def actual-input (->> (slurp (io/resource "input_day10.txt"))
                       str/split-lines
                       (map #(Integer/parseInt %))))

(defn valid-candidates [current adapters]
  (let [candidates (-> (filter #(< 0 (- % current) 4) adapters)
                       sort)]
    candidates))

(defn next-adapter [current adapters]
  (first valid-candidates current adapters))

(defn find-adapter-chain [input]
  (loop [adapter-chain      [0]
         remaining-adapters input]
    (if (empty? remaining-adapters)
      adapter-chain
      (let [adapter (next-adapter (last adapter-chain) remaining-adapters)]
        (recur (conj adapter-chain adapter)
               (filterv (complement #(= % adapter)) remaining-adapters))))))

(defn count-chain-elements-with-interval [chain interval]
  (->> chain
       (partition 2 1)
       (map (fn [[a b]] (- b a)))
       (filter #(= interval %))
       count))

(defn solve-part01 []
  (let [adapters (find-adapter-chain actual-input)
        built-in-adapter (+ 3 (last adapters))
        complete-chain (conj adapters built-in-adapter)
        one-jolt-count (count-chain-elements-with-interval complete-chain 1)
        three-jolt-count (count-chain-elements-with-interval complete-chain 3)]
    (* one-jolt-count three-jolt-count)))

(solve-part01)


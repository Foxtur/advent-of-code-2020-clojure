(ns aoc2020.day06
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(def test-input "abc\n\na\nb\nc\n\nab\nac\n\na\na\na\na\n\nb")

(def actual-input (slurp (io/resource "input_day06.txt")))

(defn solve-part01 [input]
  (let [groups (str/split input #"\R\R")]
    (->> groups
         (map #(str/replace % #"\n" ""))
         (map (comp count set))
         (reduce +))))

(defn answered-by-all [group-answers]
  (->> group-answers
       (map set)
       (apply set/intersection)))

(defn solve-part02 [input]
  (let [groups (str/split input #"\R\R")
        group-answers (map str/split-lines groups)]
    (->> group-answers
         (map (comp count answered-by-all))
         (reduce +))))

(defn -main [& args]
  (println "Solution part01:" (solve-part01 actual-input))
  (println "Solution part02:" (solve-part02 actual-input)))

(comment
  (-main)

  (map set #{"ab" "ac"}) ;; => (#{\a \b} #{\a \c})
  (apply set/intersection '(#{\a \b} #{\a \c})) ;; => #{\a}

  (->> (str/split test-input #"\R\R")      ;; => ["abc" "a\nb\nc" "ab\nac" "a\na\na\na" "b"]
       (map str/split-lines)               ;; => (["abc"] ["a" "b" "c"] ["ab" "ac"] ["a" "a" "a" "a"] ["b"])
       (map #(map set %))                  ;; => ((#{\a \b \c}) (#{\a} #{\b} #{\c}) (#{\a \b} #{\a \c}) (#{\a} #{\a} #{\a} #{\a}) (#{\b}))
       (map #(apply set/intersection %)))) ;; => (#{\a \b \c} #{} #{\a} #{\a} #{\b})


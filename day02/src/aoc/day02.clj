(ns aoc.day02
  (:require [clojure.string :as s])
  (:gen-class))

(defn input->map
  "Converts a line of given puzzle input from day02
   into a map representation"
  [input]
  (let [[_ min max char password] (re-matches #"(\d+)-(\d+) (\w{1}): (\w+)" input)]
    {:password password
     :letter (nth char 0)
     :min (Integer/parseInt min)
     :max (Integer/parseInt max)}))

(defn read-input []
  (->> (slurp "input01.txt")
       (s/trim)
       (s/split-lines)
       (map input->map)))

(defn valid-part01?
  "Validates if a given password map `m` contains a valid password
   given the password policy of part01

   The password policy indicates the lowest and highest number
   of times a given letter must appear for the password to be valid.
   "
  [{:keys [password letter min max]}]
  (let [lcount (->> password
                    (filter #(= letter %))
                    count)]
    (<= min lcount max)))

(defn valid-part02?
  "Validates if a given password map `m` contains a valid password
   given the password policy of part02

   Each policy actually describes two positions in the password,
   where 1 means the first character, 2 means the second character,
   and so on. (Be careful; Toboggan Corporate Policies have no
   concept of 'index zero'!) Exactly one of these positions must
   contain the given letter. Other occurrences of the letter are
   irrelevant for the purposes of policy enforcement."

  [{:keys [password min max letter]}]
  (not= (= letter (nth password (dec min)))
        (= letter (nth password (dec max)))))

(defn -main
  "Prints out solutions of day02 of AoC 2020"
  [& args]
  (let [input (read-input)]
    (println "Solution Part 01:" (count (filter valid-part01? input)))
    (println "Solution Part 02:" (count (filter valid-part02? input)))))

(comment
  (-main)
  (valid-part01? {:password "aaaa" :letter \a :min 1 :max 3}))

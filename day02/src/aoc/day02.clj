(ns aoc.day02
  (:require [clojure.string :as s])
  (:gen-class))

(defn input->map
  "Converts a line of given puzzle input from day02
   into a map representation"
  [input-line]
  (let [parts (s/split input-line #" ")
        password (last parts)
        letter (second parts)
        min-max (s/split (first parts) #"-")]
    {:password password
     :letter (.charAt letter 0)
     :min (Integer/parseInt (first min-max))
     :max (Integer/parseInt (second min-max))}))

(defn read-input []
  (->> (slurp "input01.txt")
       (s/trim)
       (s/split-lines)
       (map input->map)))

(defn string->chars
  "Converts a given string `s` to a seq of chars"
  [s]
  (map #(.charAt % 0) (s/split s #"")))

(defn valid-part01?
  "Validates if a given password map `m` contains a valid password
   given the password policy of part01

   The password policy indicates the lowest and highest number
   of times a given letter must appear for the password to be valid.
   "
  [m]
  (let [chars (string->chars (:password m))
        filtered (filter #(= (:letter m) %) chars)
        lcount (count filtered)]
    (and (>= lcount (:min m))
         (<= lcount (:max m)))))

(defn valid-part02?
  "Validates if a given password map `m` contains a valid password
   given the password policy of part02
   
   Each policy actually describes two positions in the password,
   where 1 means the first character, 2 means the second character,
   and so on. (Be careful; Toboggan Corporate Policies have no
   concept of 'index zero'!) Exactly one of these positions must 
   contain the given letter. Other occurrences of the letter are
   irrelevant for the purposes of policy enforcement."

  [m]
  (let [chars (string->chars (:password m))
        filtered (->>
                  (conj [] (nth chars (dec (:min m))))
                  (#(conj % (nth chars (dec (:max m)))))
                  (filter #(= (:letter m) %)))
        lcount (count filtered)]
    (= lcount 1)))

(defn -main
  "Prints out solutions of day02 of AoC 2020"
  [& args]
  (let [input (read-input)]
    (println "Solution Part 01:" (count (filter valid-part01? input)))
    (println "Solution Part 02:" (count (filter valid-part02? input)))))

(comment
  (-main)
  (string->chars "hello")
  (valid-part01? {:password "aaaa" :letter \a :min 1 :max 3}))
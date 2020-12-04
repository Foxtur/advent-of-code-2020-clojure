(ns aoc.day04
  (:require [clojure.java.io :as io]
            [clojure.walk :as w]
            [clojure.string :as s]
            [clojure.spec.alpha :as sp])
  (:gen-class))

(defn join-lines [line1 line2]
  (if (empty? line2)
    (str line1 ";")
    (str line1 " " line2)))

(defn keywordize-ns-keys
  "Recursively transforms all map keys from strings to namespaced keywords."
  [ns m]
  (let [f (fn [[k v]] (if (string? k) [(keyword ns k) v] [k v]))]
    ;; only apply to maps
    (w/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn entry->map [entry]
  (->>
   (map #(s/split % #":") entry)
   (into {})
   (keywordize-ns-keys "aoc.day04")))

(defn read-input [filename]
  (with-open [rdr (io/reader filename)]
    (->> rdr
         line-seq
         vec
         (reduce join-lines)
         (#(s/split % #";"))
         (map #(s/trim %))
         (map #(s/split % #" "))
         (map entry->map))))

(defn valid-year? [year min max]
  (and (re-matches #"\d{4}" year)
       (<= min (Integer/parseInt year) max)))

(defn valid-height? [height]
  (let [[_ number unit] (re-matches #"(\d+)(cm|in)" height)]
    (condp = unit
      "cm" (<= 150 (Integer/parseInt number) 193)
      "in" (<= 59 (Integer/parseInt number) 76)
      nil false
      :else false)))

(defn valid-hair-color? [color]
  (re-matches #"#[0-9a-f]{6}" color))

(defn valid-eye-color? [color]
  (re-matches #"amb|blu|brn|gry|grn|hzl|oth" color))

(defn valid-passport-id? [id]
  (re-matches #"\d{9}" id))

(sp/def ::byr #(valid-year? % 1920 2002))
(sp/def ::iyr #(valid-year? % 2010 2020))
(sp/def ::eyr #(valid-year? % 2020 2030))
(sp/def ::hgt valid-height?)
(sp/def ::hcl valid-hair-color?)
(sp/def ::ecl valid-eye-color?)
(sp/def ::pid valid-passport-id?)
(sp/def ::cid string?)
(sp/def ::passport (sp/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                            :opt [::cid]))

(defn solve-part01 []
<<<<<<< HEAD
  (sp/def ::byr string?)
  (sp/def ::iyr string?)
  (sp/def ::eyr string?)
  (sp/def ::hgt string?)
  (sp/def ::hcl string?)
  (sp/def ::ecl string?)
  (sp/def ::pid string?)
  (sp/def ::cid string?)
  (sp/def ::passport (sp/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                              :opt [::cid]))
  (->> (read-input "input01.txt")
       (filter #(sp/valid? ::passport %))
       count))

(defn solve-part02 []
  (sp/def ::byr #(valid-year? % 1920 2002))
  (sp/def ::iyr #(valid-year? % 2010 2020))
  (sp/def ::eyr #(valid-year? % 2020 2030))
  (sp/def ::hgt valid-height?)
  (sp/def ::hcl valid-hair-color?)
  (sp/def ::ecl valid-eye-color?)
  (sp/def ::pid valid-passport-id?)
  (sp/def ::cid string?)
  (sp/def ::passport (sp/keys :req [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                              :opt [::cid]))
=======
>>>>>>> 21765d0b85ace545ca067617f42e8d218d9d24d9
  (->> (read-input "input01.txt")
       (filter #(sp/valid? ::passport %))
       count))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
<<<<<<< HEAD
  (println "Solution part01:" (solve-part01))
  (println "Solution part02:" (solve-part02)))
=======
  (println "Solution part01:" (solve-part01)))
>>>>>>> 21765d0b85ace545ca067617f42e8d218d9d24d9

(comment
  (-main)

  (join-lines "ecl:amb byr:1943 iyr:2014 eyr:2028" "")
  (join-lines "ecl:amb byr:1943 iyr:2014 eyr:2028" "pid:333051831")

  (let [input ["ecl:amb byr:1943 iyr:2014 eyr:2028" "pid:333051831" ""]]
    (reduce join-lines input))

  (sp/valid? ::byr "2002")
  (sp/valid? ::byr "2003")
  (sp/valid? ::hgt "60in")
  (sp/valid? ::hgt "60")

  (->> (read-input "input01.txt")
       (filter #(sp/valid? ::passport %))))


(ns aoc2020.day04
  (:require [clojure.java.io :as io]
            [clojure.walk :as w]
            [clojure.string :as s]
            [clojure.spec.alpha :as sp])
  (:gen-class))

(defn keywordize-ns-keys
  "Recursively transforms all map keys from strings to namespaced keywords."
  [ns m]
  (let [f (fn [[k v]] (if (string? k) [(keyword ns k) v] [k v]))]
    ;; only apply to maps
    (w/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn parse-entry [entry]
  (->> entry
       (re-seq #"(\w{3}):(\S+)")
       (map (comp vec next))
       (into {})
       (keywordize-ns-keys "aoc.day04")))

(defn read-input [filename]
  (->> (slurp (io/resource filename))
       (#(s/split % #"\R\R"))
       (map parse-entry)))

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

(defn solve-part01 []
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
  (->> (read-input "input_day04.txt")
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
  (->> (read-input "input_day04.txt")
       (filter #(sp/valid? ::passport %))
       count))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Solution part01:" (solve-part01))
  (println "Solution part02:" (solve-part02)))

(comment
  (-main))
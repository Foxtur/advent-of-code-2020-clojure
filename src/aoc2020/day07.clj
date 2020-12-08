(ns aoc2020.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn gen-bags [type n] (take n (repeat type)))

(defn read-input []
  (->> (slurp (io/resource "input_day07.txt"))
       str/split-lines))

(defn bagstr->keyword [s]
  (->> (str/replace s #"\s" "-")
       keyword))

(defn parse-input [line]
  (let [components (str/split line #"contain")
        bagtype (->> components
                     first
                     (#(re-find #"(.+? bag)" %))
                     first
                     bagstr->keyword)
        contains (if (str/includes? line "no other bags")
                   {}
                   (->> (second components)
                        (#(str/split % #","))
                        (map #(re-find #"(\d+)\s(.+? bag)" %))
                        (reduce (fn [m [_ cnt bagstr]]
                                  (assoc m (bagstr->keyword bagstr) (Integer/parseInt cnt))) {})))]
    {bagtype contains}))

(def test-input
  (let [inputstr "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags."]
    (->> inputstr
         str/split-lines
         (map parse-input))))


(def test2-input
  (let [inputstr "shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags."]
    (->> inputstr
         str/split-lines
         (map parse-input))))

(def actual-input (map parse-input (read-input)))

(defn build-lookup-map [input-map]
  (reduce merge input-map))

(defn contains-bag? [lookup-map sbag lfbag visited]
  (let [sbag-content (sbag lookup-map)]
    (cond
      (empty? sbag-content) false
      (lfbag sbag-content) true
      (sbag lfbag) false
      :else (some (fn [[k v]] (contains-bag? lookup-map k lfbag (conj visited k))) sbag-content))))

(defn solve-part01 []
  (let [lookup-map (build-lookup-map actual-input)]
    (->> lookup-map
         (map (fn [[k v]] (contains-bag? lmap k :shiny-gold-bag #{})))
         (filter true?)
         count)))

(defn calculate-total-bags [lookup-map starting-bag-key sum]
  (let [bag-content (cbag-key lookup-map)
        cbag-count (count cb)]
    (if (= 0 cbag-count)
      0
      (map (fn [[k v]] )))))

(defn calc [lookup-map snode-key]
  (println snode-key)
  (let [node-bags (snode-key lookup-map)]
    (if (empty? node-bags)
      1
      (reduce + (map (fn [[k v]]
                       (println "(*" v "(calc lookup" k "))" )
                       (* v (calc lookup-map k))) node-bags)))))

(defn calc-first-level [lookup-map snode-key]
                  (map (fn [[k v]] (reduce + v (calc-first-level lookup-map k))) (snode-key lookup-map)))

(calc-first-level (build-lookup-map test-input) :shiny-gold-bag)

(build-lookup-map test-input)
;; => {:light-red-bag {:bright-white-bag 1, :muted-yellow-bag 2}, :muted-yellow-bag {:shiny-gold-bag 2, :faded-blue-bag 9}, :dark-orange-bag {:bright-white-bag 3, :muted-yellow-bag 4}, :vibrant-plum-bag {:faded-blue-bag 5, :dotted-black-bag 6}, :dotted-black-bag {}, :bright-white-bag {:shiny-gold-bag 1}, :faded-blue-bag {}, :shiny-gold-bag {:dark-olive-bag 1, :vibrant-plum-bag 2}, :dark-olive-bag {:faded-blue-bag 3, :dotted-black-bag 4}}

(calc (build-lookup-map test-input) :shiny-gold-bag) ;; we-re missing 3 items - getting 29 need 32 (include bags without including them in the accumulator?)

(calc-first-level (build-lookup-map test-input) :shiny-gold-bag)

(let [lookup-map (build-lookup-map test-input)]
  (->> lookup-map
       (map (fn [[k v]] (contains-bag? lmap k :shiny-gold-bag #{})))))

(comment
  (solve-part01)
  (first actual-input) ;; => {:bag :posh-crimson-bag, :contains {:mirrored-tan-bag 2, :faded-red-bag 1, :striped-gray-bag 1}}
  )

;;-----------------------------------------------------------------

(defn ->branch [nm] [children]
  {:name nm
   :children children})

(defn ->leaf [nm]
  {:name nm
   :children nil})



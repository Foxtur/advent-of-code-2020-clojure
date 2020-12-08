(ns aoc2020.main
  (:require [aoc2020.day01 :as day01]
            [aoc2020.day02 :as day02]))

(defn -main 
  "Print all soultions of thechallanges"
  [& args]
  (println "Advent of Code 2020 Solutions")
  (println "Day01:")
  (day01/print-solution)
  (println "Day02:")
  (day02/print-solution))

(-main)
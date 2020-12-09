(ns aoc2020.day08
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-input []
  (->> (slurp (io/resource "input_day08.txt"))
       str/split-lines
       vec))

(defn handle-acc [{:keys [acc ip instructions] :as state} value]
  (assoc state
         :acc (+ acc value)
         :ip (inc ip)))

(defn handle-nop [{:keys [ip] :as state}]
  (assoc state :ip (inc ip)))

(defn handle-jmp [{:keys [ip] :as state} offset]
  (assoc state :ip (+ ip offset)))

(defn handle-opcode [state opstr]
  (let [[_ op v] (re-find #"(\w+)\s([-\+]?\d+)" (str/trim opstr))
        value (Integer/parseInt v)]
    (case op
      "nop" (handle-nop state)
      "jmp" (handle-jmp state value)
      "acc" (handle-acc state value)
      :else nil)))

(defn run-program [initial-state]
  (loop [state initial-state
         executed-instructions #{}]
    (if (< (:ip state) (count (:instructions state)))
      (let [opstr (nth (:instructions state) (:ip state))
            follow-up-state (handle-opcode state opstr)
            follow-up-ip (:ip follow-up-state)]
        (if (contains? executed-instructions follow-up-ip) ;; don't run op on same address twice
          (assoc state :state :stopped)
          (recur follow-up-state (conj executed-instructions (:ip state)))))
      (assoc state :state :terminated))))

(defn alter-and-run-program [state alter-inst-idx]
  (let [instructions (:instructions state)
        original (nth instructions alter-inst-idx)
        nop? (str/includes? original "nop")
        jmp? (str/includes? original "jmp")
        altered (cond nop? (str/replace original #"nop" "jmp")
                      jmp? (str/replace original #"jmp" "nop")
                      :else original)]

    (run-program (assoc state :instructions (assoc instructions alter-inst-idx altered)))))

(defn alter-and-run-programm-till-termination [state]
  (let [instructions (:instructions state)]
    (loop [idx 0 results []]
      (let [terminated? (filter #(= :terminated (:state %)) results)]
        (if (and (< idx (count instructions))
                 (empty? terminated?))
          (recur (inc idx) (conj results (alter-and-run-program state idx)))
          terminated?)))))

(def start-state {:acc 0, :ip 0, :state :ok, :instructions (read-input)})

(defn solve-part01 []
  (:acc (run-program start-state)))

(defn solve-part02 []
  (:acc (first (alter-and-run-programm-till-termination start-state))))

(defn -main [& args]
  (println "Solution part01: " (solve-part01))
  (println "Solution part02: " (solve-part02)))

(comment
  (-main))

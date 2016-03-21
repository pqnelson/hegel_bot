(ns hegel-bot.util
  (:require [clojure.string :as string]))

(defn ->camel-case [s]
  (->> (string/split (string/trim s) #" ")
       (map string/capitalize)
       string/join))

;; "thisIsCamelCase" and "PascalCaseShouldWorkToo" both acceptable
(defn camel-case? [s]
  (= (first
      (re-find #"^[a-z0-9]*([A-Z][a-z0-9]*)+$" s))
     s))

(defn lower-case? [s]
  (= (string/lower-case s)
     s))

(defn upper-case? [s]
  (= (string/upper-case s)
     s))

(ns hegel-bot.trends
  (:refer-clojure :exclude [load update])
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [clj-time.core :as time]
            [hegel-bot.util :as util]
            [hegel-bot.twitter :as twitter]))

(def ^:const ^:private trends-file "resources/trends.edn")

(defn- remove-old-topics [topic-hash]
  (let [yesterday (time/minus (time/now) (time/days 1))]
    (into {}
          (for [[topic discussed-dt] topic-hash
                :when (time/after? discussed-dt yesterday)]
            [topic discussed-dt]))))

(defn- load-old-topics []
  (try (->> (load-file trends-file)
            edn/read-string
            remove-old-topics)
       (catch Exception e {})))

(defn load []
  (let [old-topics (load-old-topics)]
    (->> (twitter/trends)
         (map :name)
         (remove (set (map name (keys old-topics)))))))

(defn update [trends]
  (spit trends-file
        (merge (zipmap trends (repeat (time/now)))
               (load-old-topics))))

(defn- hash-tagged? [trend-str]
  (.startsWith trend-str "#"))

;; TODO: transform lowercased string into actual phrase
;;       e.g., "#askgigi" => "ask gigi"
(defn sanitize [trend-str]
  (let [un-tagged-str (if (hash-tagged? trend-str)
                        (subs trend-str 1)
                        trend-str)]
    (cond
      (util/upper-case? un-tagged-str) un-tagged-str
      (util/lower-case? un-tagged-str) un-tagged-str ; miracle needed
      (util/camel-case? un-tagged-str) (->> un-tagged-str
                                            (re-seq #"([A-Z][a-z0-9]*)")
                                            (map first)
                                            (string/join " "))
      :else un-tagged-str)))

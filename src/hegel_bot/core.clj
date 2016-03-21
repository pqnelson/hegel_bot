(ns hegel-bot.core
  (:require [clojure.edn :as edn]
            [clj-time.core :as time]
            [hegel-bot.twitter :as twitter]
            [hegel-bot.status :as status]
            [hegel-bot.trends :as trends]))


(defn -main []
  (let [topics (trends/load)]
    (doseq [topic topics]
      (twitter/post-status (status/generate topic :hashtag-topic)))
    (trends/update topics)))


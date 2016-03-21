(ns hegel-bot.twitter
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as string]
            [twitter.oauth :as oauth]
            [twitter.api.restful :as twitter]))

(def ^:const usa-code 23424977)

;; twitter_credentials.edn defines consumer-key, consumer-secret,
;; access-token, and access-secret
(load-file "resources/twitter_credentials.edn")

(def my-creds (oauth/make-oauth-creds consumer-key
                                      consumer-secret
                                      access-token
                                      access-secret))

;; returns [{:name ..., :urls ..., :promoted_content ..., :query ..., :tweet_volume ...}]
(defn trends []
  (->> (twitter/trends-place :oauth-creds my-creds
                             :params {:id usa-code})
       :body
       first
       :trends))

(defn post-status [status-string]
  (twitter/statuses-update :oauth-creds my-creds
                           :params {:status status-string}))


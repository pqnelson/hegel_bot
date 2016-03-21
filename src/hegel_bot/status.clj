(ns hegel-bot.status
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as string]
            [hegel-bot.util :as util]
            [hegel-bot.trends :as trends]))

(def ^:const char-rnn-dir "/home/alex/src/char-rnn/")
(def ^:const torch-bin-dir "/home/alex/torch/install/bin/")
(def ^:const hegel-bot-brain "cv/lm_hegel_epoch5.12_0.9782.t7")

(defn- trim-output [raw-status-output]
  (subs raw-status-output (+ (.indexOf raw-status-output "-\t\n") 3)))

(defn- make-hashtag [status topic-string]
  (str "#"
       (util/->camel-case topic-string)
       (subs status (count (string/trim topic-string)))))

(defn generate
  ([topic-string] (generate topic-string false))
  ([topic-string hashtag-topic?]
   (->> (sh (str torch-bin-dir "th")
            "sample.lua"
            hegel-bot-brain
            "-gpuid" "-1"
            "-primetext" (str (trends/sanitize topic-string) " ")
            "-length" (str (- 140 (count topic-string)))
            :dir char-rnn-dir)
        :out
        trim-output
        (#(if-not hashtag-topic?
            %
            (make-hashtag % topic-string))))))

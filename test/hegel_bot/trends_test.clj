(ns hegel-bot.trends-test
  (:refer-clojure :exclude [load update])
  (:require [clojure.test :refer :all]
            [hegel-bot.trends :refer :all]))

(deftest sanitize-test
  (testing "Prefixed with hashtag"
    (is (= (sanitize "#RockTheVote")
           "Rock The Vote"))
    (is (= (sanitize "#KBBL")
           "KBBL"))
    (is (= (sanitize "#askhegel")
           "askhegel")))
  (testing "Prefixed without hashtag"
    (is (= (sanitize "RockTheVote")
           "Rock The Vote"))
    (is (= (sanitize "KBBL")
           "KBBL"))
    (is (= (sanitize "askhegel")
           "askhegel"))))

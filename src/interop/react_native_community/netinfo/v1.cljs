(ns interop.react-native-community.netinfo.v1
  (:require ["@react-native-community/netinfo" :as netinfo]))

(assert netinfo)

(defn massage-state [state]
  (js->clj state :keywordize-keys true))

(defn fetch [cb]
  (-> (netinfo/fetch)
      (.then (fn [state] (cb (massage-state state)))
             (fn [err] (cb (ex-info (str ::fetch " error") err))))))

(defn add-event-listener [cb]
  (netinfo/addEventListener (comp cb massage-state)))

(comment
  (fetch #(println ::fetch %))
  (def unsub (add-event-listener #(println ::listener %)))
  (unsub))

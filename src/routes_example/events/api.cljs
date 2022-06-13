(ns routes-example.events.api 
  (:require 
   [re-frame.core :as re-frame] 
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::failure-http-result
 (fn-traced [db [_ result]]
    ;; result is a map containing details of the failure
   (-> db
       (assoc :loading false)
       (assoc :failure-http-result result))))
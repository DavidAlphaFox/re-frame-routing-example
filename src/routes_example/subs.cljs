(ns routes-example.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (get-in db [:route :panel])))


(re-frame/reg-sub
 ::route-params
 (fn [db _]
   (get-in db [:route :route :route-params])))

(re-frame/reg-sub
 ::loading 
 (fn [db _] 
   (get-in db [:loading])))
(re-frame/reg-sub
 ::users
 (fn [db _]
   (get-in db [:users])))
(ns routes-example.events
  (:require
   [re-frame.core :as re-frame]
   [routes-example.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [cofx [_ handler]]
            (println cofx)
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))

(re-frame/reg-event-fx
 ::set-route
 (fn-traced [{:keys [db]} [_ route]]
            {:db (assoc db :route route)}))

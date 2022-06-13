(ns routes-example.events.users
  (:require 
   [re-frame.core :as re-frame] 
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [routes-example.events.api :as api]))

(re-frame/reg-event-fx                             ;; note the trailing -fx
 ::fetch-users                      ;; usage:  (dispatch [:handler-with-http])
 (fn-traced [{:keys [db]} _]                    ;; the first param will be "world"
   {:db   (assoc db :loading true)   ;; causes the twirly-waiting-dialog to show??
    :http-xhrio {:method          :get
                 :uri             "https://reqresc.in/api/u?page=2"
                 :timeout         8000                                           ;; optional see API docs
                 :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                 :on-success      [::fetch-users-success]
                 :on-failure      [::api/failure-http-result]}}))

(re-frame/reg-event-db
 ::fetch-users-success
 (fn-traced [db [_ {:keys [data]}]]
   (-> db
       (assoc :loading false)
       (assoc :users data))))

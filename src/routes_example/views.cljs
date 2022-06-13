(ns routes-example.views
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.events.users :as users]
   [routes-example.routes :as routes]
   [routes-example.subs :as subs]
   [re-echarts.core :refer [ECharts]]))


;; home

(defn the-chart []
  [:> ECharts
   {:style {:width "100%" :height "90vh"}
    :theme "dark"
    :option
    {:title {:text "Echarts is here"}
     :dataset {:dimention [:Week :Value]
               :source [{:Week "Mon" :Value 820} {:Week "Tue" :Value 932} {:Week "Wed" :Value 901}
                        {:Week "Thu" :Value 934} {:Week "Fri" :Value 1220} {:Week "Sat" :Value 820}
                        {:Week "Sun" :Value 990}]}
     :xAxis {:type "category"}
     :yAxis {:type "value"}
     :series [{:type "line"
               :smooth true}]}}])

(defn display-user [{:keys [id avatar email] first-name :first_name}]
  [:div.horizontal {:key id}
   [:img.pr-15 {:src avatar}]
   [:div
    [:h2 first-name]
    [:p  (str "(" email ")")]]])

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])
        loading (re-frame/subscribe [::subs/loading])
        users (re-frame/subscribe [::subs/users])]
    [:div
     [:h1
      (str "Hello from " @name ". This is the Home Page.")]
     [:h2 (when @loading "Loading...")]
     ;;[:div (the-chart)] 
     (map display-user @users)
     [:button {:on-click #(re-frame/dispatch [::users/fetch-users])} "Make API Call"]
     [:div
      [:a {:on-click #(re-frame/dispatch [::events/navigate [:about]])}
       "go to About Page"]]]))

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:on-click #(re-frame/dispatch [::events/navigate [:home]])}
     "go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div "this is on every page"
     [:div (routes/panels @active-panel)]]))

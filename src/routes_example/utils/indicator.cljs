(ns routes-example.utils.indicator
  (:require
    [routes-example.utils.ma :as MA]))

;; 使用多空比率净额进行修正的obv
;; 多空比率净额= [（收盘价－最低价）－（最高价-收盘价）] ÷（ 最高价－最低价）×V
(defn obv [item]
  (let [highest (:highest item)
         lowest (:lowest item)
         close (:close item)
         vol (:vol item)]
  (* vol (/ (- (* 2 close) lowest highest)
           (- highest lowest)))))

;; 全换手时间，所有的流通股全部换手一次所用时间
;; 换手率(HSL) = Vol / 流通盘 * 100%
;; 全换手时间(DD) = 流通盘 / Vol
;; 全换手周期内的市场成本(HS) = MA(收盘价格,DD)
;; 全换手周期内的市场平滑成本(HSX) = EMA(收盘价格,DD)
(defn lei-cost
  [tradable-vol coll]
  (let [n (count coll)
         dd-coll (map #(Math/ceil (/ tradable-vol (:vol %))) coll)
         close-coll (map :close coll)]
    (loop [i 0 acc (transient [])]
      (if (< i n)
        (let [dd (nth dd-coll i)
               c (+ 1 i)
               close (take c close-coll)
               hs (MA/moving-average dd close)
               hsx (MA/exponential-moving-average dd close)]
          (recur c (conj! acc {:dd dd :hs hs :hsx hsx })))
        (persistent! acc)))))

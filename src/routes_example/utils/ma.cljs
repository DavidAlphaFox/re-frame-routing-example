(ns routes-example.utils.ma)

(defn  moving-average 
  "简单移动平均线"
  [period coll]
  (lazy-cat (repeat (dec period) nil)
            (map #(/ (reduce + %) period))))
(defn exponential-moving-average
  "指数移动平均值"
  [period coll]
  (let [k (/ 2 (+ period 1))
        p (- 1 k)]
    (reduce (fn [acc item]
              (let [ema-last (last acc)
                    ema-now (if (first acc)
                             (+ (* k item) (* p ema-last))
                              item)]
                (conj acc ema-now)))
            [] coll)))
;;(def data '(10.94 12.03 13.23 14.55 16.01))
;;period 5
;;(def result '(10.940 11.303 11.946 12.814 13.879))

;; (try 
;;  (moving-average 5 (range 20)) 
;;   (catch js/Error e 
;;     (println e)))
(ns routes-example.utils.ma)

(defn moving-average 
  "计算简单MA其性能好于partaition"
  [tick-window tick-list] 
  (assert (<= 0 (- tick-window 1)) "tick-window should be bigger than 1")
  (let [init (vec (repeat (- tick-window 1) "-"))
        r (conj init (/ (apply + (take tick-window tick-list)) tick-window))
        s (count tick-list)]
    (loop [index tick-window
           acc (transient r)]
      (if (< index s)
        (let [f (- index tick-window)
              p (- index 1)
              ma (+ (nth acc p) (/ (- (nth tick-list index) (nth tick-list f)) tick-window))]
          (recur (+ 1 index)
                 (conj! acc ma)))
        (persistent! acc)))))

;; (defn moving-average 
;;   "计算简单MA"
;;   [tick-window tick-list]
;;   (assert (<= 0 (- tick-window 1)) "tick-window should be bigger than 1")
;;   (let [r (vec (repeat (- tick-window 1) "-"))
;;         s (partition tick-window 1 tick-list)] (
;;      (reduce (fn [acc items]
;;                (conj! acc (/ (apply + items) tick-window))) r s))))
;; (try 
;;  (moving-average 5 (range 20)) 
;;   (catch js/Error e 
;;     (println e)))
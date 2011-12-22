(ns clojure-vagina.core)

(defn- reader-macro [c f]
  (let [dm (.get (doto (.getDeclaredField clojure.lang.LispReader "macros") (.setAccessible true)) nil)]
    (aset dm (int c) f)))

(defn- undo
  ([rdr c & more]
    (.unread rdr (char-array more)) (undo rdr c))
  ([rdr c]
    (.invoke (clojure.lang.LispReader$ListReader.) rdr c)))

(reader-macro \( (fn [rdr _]
  (let [a (char (.read rdr))]
    (if (= \' a)
      (let [b (char (.read rdr))]
        (if (= \) b)
          "vagina"
          (undo rdr \( a b)))
      (undo rdr \( a)))))

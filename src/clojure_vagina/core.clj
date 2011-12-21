(ns clojure-vagina.core)

(defn- reader-macro [c f]
  (let [dm (.get (doto (.getDeclaredField clojure.lang.LispReader "macros") (.setAccessible true)) nil)]
    (aset dm (int c) f)))

(defn- undo
  ([rdr c & more]
    (.unread rdr (char-array (map char more)))
    (.invoke (clojure.lang.LispReader$ListReader.) rdr c))
  ([rdr c]
    (.invoke (clojure.lang.LispReader$ListReader.) rdr c)))

(reader-macro \( (fn [rdr _]
  (let [a (.read rdr)]
    (if (= (int \') a)
      (let [b (.read rdr)]
        (if (= (int \)) b)
          "vagina"
          (undo rdr \( a b)))
      (undo rdr \) a)))))

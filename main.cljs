(ns main
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(defonce state (r/atom nil))

(defn keydown
  [ev]
  (js/console.log ev)
  (let [k (aget ev "keyCode")]
    (case k
      37
      (swap! state update :slide dec)
      39
      (swap! state update :slide inc)
      nil)))

(defn app []
  [:<>
   [:style (str "section:nth-child("
                (inc (mod (:slide @state)
                          (aget (js/document.querySelectorAll "section") "length")))
                ") { display: block; }")]
   [:main
    [:section
     [:h1 "What's the point of LISP?"]
     [:img {:src "emacs.png"}]]
    [:section
     [:h1 "Orange Site Question"]
     [:img {:src "hn.png"}]]
    [:section
     [:h1 "Faster editing"]]
    [:section
     [:h1 "Less syntax to remember"]]
    [:section
     [:h1 "nREPL (fast iteration)"]]
    [:section
     [:h1 "Metaprogramming"]]
    [:section
     [:h1 "Try it"]
     [:h2
     [:ul
      [:li [:code "pip install basilisk"]]
      [:li [:code "npm install nbb"]]]]]]])

(rdom/render [app] (.getElementById js/document "app"))
(defonce keylistener (aset js/window "onkeydown" #(keydown %)))

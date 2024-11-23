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
                          (aget
                            (js/document.querySelectorAll "section")
                            "length")))
                ") { display: block; }")]
   [:main
    [:section
     [:h1 "What's the point of LISP?"]
     [:img {:src "emacs.png"}]]
    [:section
     [:h1 "Orange Site Question"]
     [:img {:src "hn.png"}]]
    [:section
     [:h1 "What's LISP?"]
     [:h2
      [:p [:code "."]]]]
    [:section
     [:h1 "What is LISP?"]
     [:h2
      [:p [:code " print(42)"]]]]
    [:section
     [:h1 "What is LISP?"]
     [:h2
      [:p [:code "(print 42)"]]]]
    [:section
     [:h1 "What is LISP?"]
     [:h2
      [:p "Very simple to implement"]]]
    [:section
     [:h1 "What's the point of LISP?"]
     [:h2
      [:p "Four features"]]]
    [:section
     [:h1 "1. Faster editing & refactoring"]
     [:h2
      [:p "Your editor understands the language"]]]
    [:section
     [:h1 "2. Less syntax to remember"]
     [:h2
      [:p "Con: so many brackets!"]
      [:p "Pro: so many brackets!"]]]
    [:section
     [:h1 "2. Less syntax to remember"]
     [:h2
      [:p [:code "q = 42"]]]]
    [:section
     [:h1 "2. Less syntax to remember"]
     [:h2
      [:p [:code "(def q 42)"]]]]
    [:section
     [:h1 "3. nREPL (fast iteration)"]
     [:img {:src "cider-debug.gif"
            :style {:width "80vw"}}]]
    [:section
     [:h1 "4. Metaprogramming"]
     [:p "I am glad the big brained have access to this."]]
    [:section
     [:h1 "Try it"]
     [:h2
      [:ul
       [:li [:code "pip install hy"]]
       [:li [:code "npm install nbb"]]]]]]])

(rdom/render [app] (.getElementById js/document "app"))
(defonce keylistener (aset js/window "onkeydown" #(keydown %)))

(ns main
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(defonce state (r/atom nil))

(defn get-slide-count []
  (aget
    (js/document.querySelectorAll "section")
    "length"))

(defn keydown
  [ev]
  (js/console.log ev)
  (let [k (aget ev "keyCode")]
    (cond
      (contains? #{37 38 33} k)
      (swap! state update :slide dec)
      (contains? #{39 40 32 13 34} k)
      (swap! state update :slide inc)
      (contains? #{27 72 36} k)
      (swap! state assoc :slide 0)
      (contains? #{35} k)
      (swap! state assoc :slide (dec (get-slide-count))))))

(defn app []
  [:<>
   [:style (str "section:nth-child("
                (inc (mod (:slide @state)
                          (get-slide-count)))
                ") { display: block; }")]
   [:main
    [:section
     [:h1 "What's the point of LISP?"]
     [:img {:src "emacs.png"}]]
    [:section
     [:h1 "Orange Site Question"]
     [:img {:src "hn.png"}]]
    [:section
     [:h1 "What is LISP?"]
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
      [:p "The code is data (AST)"]]]
    [:section
     [:h1 "What is LISP?"]
     [:h2
      [:p "Very simple to implement."]]]
    [:section
     [:h1 "What's the point of LISP?"]
     [:h2
      [:p "Four features."]]]
    [:section
     [:h1 "1. Faster editing & refactoring"]
     [:h2
      [:p "Your editor understands the language."]]]
    [:section
     [:h1 "2. Less syntax to remember."]
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
     [:p "Macros. Code that modifies the code."]]
    [:section
     [:h1 "4. Metaprogramming"]
     [:p "Add new language features."]]
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
; trigger a second render so we get the sections count
(swap! state assoc :slide 0)

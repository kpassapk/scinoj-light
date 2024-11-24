(ns slides 
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

(defn component:show-slide [state]
  [:style (str "section:nth-child("
               (inc (mod (:slide @state)
                         (get-slide-count)))
               ") { display: block; }")])

(defn app [state]
  [:<>
   [:main
    [:section
     [:h1 "Hello"]
     [:h2 "Your first slide."]
     [:footer
      [:small
       [:a {:href "https://github.com/chr15m/scittle-tiny-slides"}
        "Made with Scittle Tiny Slides"]]]]
    [:section
     [:h1 "Slide Two"]
     [:img {:src "https://w.wiki/CAvg"}]
     [:h3 "It's the moon."]]
    [:section
     [:h1 "Slide Three"]
     [:h2
      [:p [:code "Thank you for watching."]]]]
    [component:show-slide state]]])

(rdom/render [app state] (.getElementById js/document "app"))
(defonce keylistener (aset js/window "onkeydown" #(keydown %)))
; trigger a second render so we get the sections count
(swap! state assoc :slide 0)

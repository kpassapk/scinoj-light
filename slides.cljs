(ns slides 
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(defn slides []
  [:<>
   ; your slides start here
   ; each slide is a :section
   ; you can add whatever hiccup you like

   [:section
    [:h1 "Hello"]
    [:h2 "Your first slide."]
    [:footer
     [:small
      [:a {:href "https://github.com/chr15m/scittle-tiny-slides"
           :target "_BLANK"}
       "Made with Scittle Tiny Slides bla bla bla bla bla bla bla bla"]]]]

   [:section
    [:h1 "Slide Two"]
    [:img {:src "https://w.wiki/CAvg"}]
    [:h3 "It's the moon."]]

   [:section
    [:h1 "Slide Three"]
    [:h2
     [:p [:code "Thank you for watching."]]]]])

; *** implementation details *** ;

(defonce state (r/atom nil)) ; re-initialized below

(defn get-slide-count []
  (aget
    (js/document.querySelectorAll "section")
    "length"))

(defn move-slide! [state ev dir-fn]
  (.preventDefault ev)
  (swap! state update :slide dir-fn))

(defn clickable? [ev]
  (let [tag-name (.toLowerCase (aget ev "target" "tagName"))]
    (contains? #{"button" "label" "select"
                 "textarea" "input" "a"
                 "details" "summary"}
               tag-name)))

(defn keydown
  [ev]
  (when (not (clickable? ev))
    (let [k (aget ev "keyCode")]
      (cond
        (contains? #{37 38 33} k)
        (move-slide! state ev dec)
        (contains? #{39 40 32 13 34} k)
        (move-slide! state ev inc)
        (contains? #{27 72 36} k)
        (swap! state assoc :slide 0)
        (contains? #{35} k)
        (swap! state assoc :slide (dec (get-slide-count)))))))

(defn component:show-slide [state]
  [:style (str "section:nth-child("
               (inc (mod (:slide @state) (get-slide-count)))
               ") { display: block; }")])

(defn component:touch-ui [state]
  [:div#touch-ui
   {:style {:opacity
            (if (:touch-ui @state) 0 1)}}
   [:div {:on-click #(move-slide! state % dec)} "⟪"]
   [:div {:on-click #(move-slide! state % inc)} "⟫"]])

(defn component:slide-viewer [state]
  [:<>
   [:main {:on-click
           #(when (not (clickable? %))
              (js/console.log (aget % "detail"))
              (swap! state update :touch-ui not))}
    [slides]]
   [component:show-slide state]
   [component:touch-ui state]])

(rdom/render
  [component:slide-viewer state]
  (.getElementById js/document "app"))
(defonce keylistener (aset js/window "onkeydown" #(keydown %)))
; trigger a second render so we get the sections count
(swap! state assoc :slide 0 :touch-ui true)

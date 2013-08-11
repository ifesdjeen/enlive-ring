(ns com.ifesdjeen.enlive-ring.core
  (:use compojure.core)
  (:require [ring.middleware.params :as params]
            [ring.util.response     :as response]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [net.cgrand.enlive-html :as html]))


(def navigation-items
  {"Home" "/"
   "About" "/about"
   "Contact" "/contact"})

(html/defsnippet header "templates/header.html"
  [:body :div.navbar]
  [current-path]
  [:a.brand] (html/content "Enlive starter kit")
  [:ul.nav [:li html/first-of-type]] (html/clone-for [[caption url] navigation-items]
                                                     [:li] (if (= current-path url)
                                                             (html/set-attr :class "active")
                                                             identity)
                                                     [:li :a] (html/content caption)
                                                     [:li :a] (html/set-attr :href url)))



(html/defsnippet content "templates/content.html"
  [:#content]
  [replacements]
  [:#content html/any-node] (html/replace-vars replacements))

(html/deftemplate main-template "templates/application.html"
  [{:keys [path]}]
  [:head :title] (html/content "Enlive starter kit")
  [:body] (html/do-> (html/append (header path))
                     (html/append (content {:header "This is an interpolated header"
                                            :content_part_1 "Use this document as a way to quick start any new project"
                                            :content_part_2 "All you get is this message and a barebones HTML document"}))))

(defn index-page
  [request]
  (main-template {:path (:uri request)}))

(defroutes main-routes
  (GET "/" request (index-page request))
  (route/resources "/assets")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)))

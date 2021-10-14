(ns hello-reitit.core
  (:require [reitit.core :as r]))

;; Simple test

(def router
  (r/router
   [["/api/ping" ::ping]
    ["/api/orders/:id" ::order]]))

(comment
  (r/match-by-path router "/api/ping")

  (r/match-by-name router ::order {:id 2}))

;; Different routers

(comment
  (r/router-name
   (r/router
    [["/ping" ::ping]
     ["/api/users" ::users]]))

  (r/router-name
   (r/router
    [["/ping" ::ping]
     ["/api/:users" ::users]]
    {:router r/linear-router})))

;; Route data

(defn api-interceptor [])

(defn db-handler [])

(defn ping-handler [])

(defn user-handler [])

(defn db-interceptor [])

(def nested-router
  (r/router
   ["/api" {:interceptors [api-interceptor]}
    ["/ping" {:handler ping-handler}]
    ["/admin" {:roles #{:admin}}
     ["/users" {:interceptors [db-interceptor]
                :roles ^:replace #{:db-admin}
                :handler db-handler}]]]))

(def flat-router
  (r/router
   ["/api/ping" {:interceptors [api-interceptor]
                 :handler ping-handler}
    ["/api/admin/users" {:interceptors [api-interceptor]
                         :roles #{:admin}
                         :handler user-handler}]
    ["/api/admin/db" {:interceptors [api-interceptor db-interceptor]
                      :roles #{:db-admin}
                      :handler db-handler}]]))

(comment
  (r/match-by-path nested-router "/api/admin/users"))

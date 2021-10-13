(ns hello-reitit.core
  (:require [reitit.core :as r]))

(def router
  (r/router
   [["/api/ping" ::ping]
    ["/api/orders/:id" ::order]]))

(comment
  (r/match-by-path router "/api/ping")

  (r/match-by-name router ::order {:id 2}))

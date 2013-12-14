(ns noir-auth-app.models.mailer
  (:require [postal.core :as postal]))

(defn- send-email [from to subject body]
  (postal/send-message #^{ :host (get (System/getenv) "SMTP_SERVER")
                           :user (get (System/getenv) "SMTP_USERNAME")
                           :pass (get (System/getenv) "SMTP_PASSWORD")
                           :ssl true }
                       { :from from
                         :to to
                         :subject subject
                        :body body }))

(defn- log-send [from to subject body]
  (println "****SENDING NOT CONFIGURED")
  (println "\tTO: " to)
  (println "\tFrom: " from)
  (println "\tSubject: " subject)
  (println "\tBody: " body "\n\n"))

; TODO: handle the case when sending fails (ex. when SMTP_SERVER, etc are not configured)
(defn send-email [{:keys [from to subject body]}]
  ; If your destination supports TLS instead, you can use :tls.
  ; This will default to port 25, however, so if you need a
  ; different one make sure you supply :port. (It's common for ISPs
  ; to block outgoing port 25 to relays that aren't theirs. Gmail
  ; supports SSL & TLS but it's easiest to just use SSL since
  ; you'll likely need port 465 anyway.)
  ; https://github.com/drewr/postal
                                        ;
  (if (System/getenv "SMTP_SERVER")
    (do
      (println "actually sending")
      (send-email from to subject body))
    (do
      (println "logging the send")
      (log-send from to subject body))))

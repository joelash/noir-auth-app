(ns noir-auth-app.authorization)

(defn authorized? [user-roles auth-roles]
  (some true?
        (for [auth-role (if (coll? auth-roles) auth-roles [auth-roles])
              user-role user-roles]
          (isa? user-role auth-role))))

(defmacro authorized-for [roles user func & [failure-func]]
  `(if (authorized? (:roles ~user) ~roles)
     ~func
     (when ~failure-func ~failure-func)))


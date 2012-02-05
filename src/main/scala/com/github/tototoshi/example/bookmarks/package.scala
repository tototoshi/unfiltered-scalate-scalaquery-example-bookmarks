package com.github.tototoshi.example

import org.scalaquery.session.Database

package object bookmarks {

  val db = Database.forURL("jdbc:hsqldb:mem:hsqldb:bookmarks", driver="org.hsqldb.jdbc.JDBCDriver")

}

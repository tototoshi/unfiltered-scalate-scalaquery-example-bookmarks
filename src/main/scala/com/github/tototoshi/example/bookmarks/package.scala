package com.github.tototoshi.example

import scalikejdbc._

package object bookmarks {

  Class.forName("org.hsqldb.jdbc.JDBCDriver")
  ConnectionPool.add('db1, "jdbc:hsqldb:mem:hsqldb:bookmarks", null, null)

  def conn = ConnectionPool('db1).borrow()
  def db = new DB(conn)

}

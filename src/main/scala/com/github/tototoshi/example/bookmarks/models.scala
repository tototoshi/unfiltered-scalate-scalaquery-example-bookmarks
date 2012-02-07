package com.github.tototoshi.example.bookmarks

import scalikejdbc._
import LoanPattern._

case class Bookmark(id: Int, title: String, url: String)

object Bookmark {

  def findAll(): List[Bookmark] = using(ConnectionPool('db1).borrow()) { conn =>
    new DB(conn) readOnly { session =>
      session.asList("select * from bookmark") { rs =>
        (Bookmark(rs.getInt("id"), rs.getString("title"), rs.getString("url")))
      }
    }
  }

  def create(title: String, url: String): Unit = {
    using (ConnectionPool('db1).borrow()) { conn =>
      new DB(conn) localTx { session =>
        session.update("insert into bookmark (title, url) values (?, ?)", title, url)
      }
    }
  }

  def delete(id: Int): Unit = {
    using (ConnectionPool('db1).borrow()) { conn =>
      new DB(conn) localTx { session =>
        session.update("delete from bookmark where id = ?", id)
      }
    }
  }

}

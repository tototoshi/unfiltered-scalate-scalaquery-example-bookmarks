package com.github.tototoshi.example.bookmarks

case class Bookmark(title: String, url: String)

object Bookmark {
  def findAll(): List[Bookmark] = db readOnly { session =>
    session.asList("select * from bookmark") { rs =>
      Some(Bookmark(rs.getString("title"), rs.getString("url")))
    }
  }
}

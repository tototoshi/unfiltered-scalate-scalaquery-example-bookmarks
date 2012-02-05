package com.github.tototoshi.example.bookmarks

import org.scalaquery._
import ql._
import basic.{ BasicTable => Table, _ }
import basic.BasicDriver.Implicit._
import session._


case class Bookmark(title: String, url: String)

object BookmarkTable extends Table[Bookmark]("bookmark") {
  def title = column[String]("title", O NotNull)
  def url = column[String]("url", O NotNull)
  def * = title ~ url <> (Bookmark, Bookmark.unapply _)

  def findAll(): List[Bookmark] = db withSession { implicit s: Session =>
    (for { b <- BookmarkTable } yield b).list()
  }

}


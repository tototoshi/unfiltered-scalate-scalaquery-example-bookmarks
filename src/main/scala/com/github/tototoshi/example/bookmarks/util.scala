package com.github.tototoshi.example.bookmarks

import scala.util.control.Exception._

object AsInt {
  def unapply(s: String): Option[Int] = catching(classOf[NumberFormatException]).opt {
    s.toInt
  }
}

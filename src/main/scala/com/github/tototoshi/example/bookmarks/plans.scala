package com.github.tototoshi.example.bookmarks

import unfiltered.filter._
import unfiltered.request._
import unfiltered.response._
import unfiltered.scalate._

import org.fusesource.scalate.{ TemplateEngine, Binding}

import org.scalaquery.session.Session
import org.scalaquery.ql.basic.BasicDriver.Implicit._

import java.io.File

class BookmarkPlan extends Plan {

  /* Display template compilation error message on browser */
  implicit val defaultTemplate = new TemplateEngine(new File("src/main/resources/templates") :: Nil , "dev")

  def intent = {

    case req @ Path(Seg("bookmark" :: "list" :: Nil)) => {
      val q = for { b <- BookmarkTable } yield b
      Scalate(req, "list.ssp", "bookmarks" -> BookmarkTable.findAll)
    }

    case req @ Path(Seg("bookmark" :: "create" :: Nil)) => req match {
      case GET(_) => {
        Scalate(req, "create.ssp")
      }
      case POST(_) & Params(params)=> {
        for {
          title <- params("title").headOption
          url <- params("url").headOption
        } {
          db withSession { implicit s: Session =>
            BookmarkTable.insert(Bookmark(title, url))
          }
        }

        Redirect("/bookmark/list")
      }
    }

    case req @ Path("/") => {
      Scalate(req, "index.ssp")
    }

  }

}



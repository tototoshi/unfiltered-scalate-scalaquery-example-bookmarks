package com.github.tototoshi.example.bookmarks

import unfiltered.filter._
import unfiltered.request._
import unfiltered.response._
import unfiltered.scalate._

import org.fusesource.scalate.{ TemplateEngine, Binding}

import java.io.File

import scalikejdbc._
import LoanPattern._

class BookmarkPlan extends Plan {

  /* Display template compilation error message on browser */
  implicit val defaultTemplate = new TemplateEngine(new File("src/main/resources/templates") :: Nil , "dev")

  def intent = {

    case req @ Path(Seg("bookmark" :: "list" :: Nil)) => {
      Scalate(req, "list.ssp", "bookmarks" -> Bookmark.findAll())
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
          Bookmark.create(title, url)
        }
        Redirect("/bookmark/list")
      }
    }

    case POST(Path(Seg("bookmark" :: "delete" :: Nil))) & Params(params)=> {
      // TODO Validation
      params("id").headOption.foreach {
        case AsInt(id) => Bookmark.delete(id)
        case _ => throw new Exception
      }
      Redirect("/bookmark/list")
    }

    case req @ Path("/") => {
      Scalate(req, "index.ssp")
    }

  }
}

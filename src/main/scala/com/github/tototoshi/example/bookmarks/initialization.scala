package com.github.tototoshi.example.bookmarks

import javax.servlet._
import org.scalaquery.session.Session
import org.scalaquery.ql.basic.BasicDriver.Implicit._

class InitializationListener extends ServletContextListener {

  def contextInitialized(event: ServletContextEvent): Unit = {
    val banner = """|
                    | ##      ## ######## ##        ######   #######  ##     ## ######## ####
                    | ##  ##  ## ##       ##       ##    ## ##     ## ###   ### ##       ####
                    | ##  ##  ## ##       ##       ##       ##     ## #### #### ##       ####
                    | ##  ##  ## ######   ##       ##       ##     ## ## ### ## ######    ##
                    | ##  ##  ## ##       ##       ##       ##     ## ##     ## ##
                    | ##  ##  ## ##       ##       ##    ## ##     ## ##     ## ##       ####
                    |  ###  ###  ######## ########  ######   #######  ##     ## ######## ####
                    |""".stripMargin

    db withSession { implicit s: Session =>
      BookmarkTable.ddl.create

      BookmarkTable.insertAll(Bookmark("google", "http://www.google.co.jp"),
                           Bookmark("twitter", "http://twitter.com"),
                           Bookmark("facebook", "http://www.facebook.com")
                         )
    }

    println(banner)
  }

  def contextDestroyed(event: ServletContextEvent): Unit = {
    db withSession { implicit s: Session =>
      BookmarkTable.ddl.drop
    }

    val banner = """|
                    | ########  ##    ## ######## ####
                    | ##     ##  ##  ##  ##       ####
                    | ##     ##   ####   ##       ####
                    | ########     ##    ######    ##
                    | ##     ##    ##    ##
                    | ##     ##    ##    ##       ####
                    | ########     ##    ######## ####
                    |""".stripMargin

    println(banner)
  }

}

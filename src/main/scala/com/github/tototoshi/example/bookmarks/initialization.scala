package com.github.tototoshi.example.bookmarks

import javax.servlet._

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

    db autoCommit { session =>
      session.execute("create table bookmark (id integer primary key identity, title varchar(100), url varchar(200))")
      session.update("insert into bookmark (title, url) values ('google', 'http://www.google.com')")
      session.update("insert into bookmark (title, url) values ('twitter', 'http://twitter.com')")
      session.update("insert into bookmark (title, url) values ('facebook', 'http://www.facebook.com')")
    }

    println(banner)
  }

  def contextDestroyed(event: ServletContextEvent): Unit = {
    db autoCommit { session =>
      session.execute("drop table bookmark")
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

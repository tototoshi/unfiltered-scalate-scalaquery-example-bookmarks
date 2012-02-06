package com.github.tototoshi.example.bookmarks

import javax.servlet._

import scalikejdbc._
import LoanPattern._

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

    Class.forName("org.hsqldb.jdbc.JDBCDriver")
    ConnectionPool.add('db1, "jdbc:hsqldb:mem:hsqldb:bookmarks", null, null)

    using(ConnectionPool('db1).borrow()) { conn =>
      new DB(conn) autoCommit { session =>
        session.execute("create table bookmark (id integer primary key identity, title varchar(100), url varchar(200))")
        session.update("insert into bookmark (title, url) values ('google', 'http://www.google.com')")
        session.update("insert into bookmark (title, url) values ('twitter', 'http://twitter.com')")
        session.update("insert into bookmark (title, url) values ('facebook', 'http://www.facebook.com')")
      }
    }

    println(banner)
  }

  def contextDestroyed(event: ServletContextEvent): Unit = {
    using (ConnectionPool('db1).borrow()) { conn =>
      new DB(conn) autoCommit { session =>
        session.execute("drop table bookmark")
      }
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

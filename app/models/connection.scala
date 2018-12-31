package models

import slick.basic.DatabaseConfig
import slick.driver.JdbcProfile

object connection {
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("db.postgres")
  val db = dbConfig.db
}

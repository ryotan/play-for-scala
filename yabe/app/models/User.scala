package models

import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._

/**
 * Model class for User.
 *
 * @author ryotan
 * @since 1.0
 */
case class User(email: String, password: String, fullname: String, isAdmin: Boolean) {
  def save() = DB.withConnection { implicit connection =>
    SQL("insert into Users(email, password, fullname, is_admin) values ({email}, {password}, {fullname}, {isAdmin})").on(
      'email -> this.email,
      'password -> this.password,
      'fullname -> this.fullname,
      'isAdmin -> (if (isAdmin) 1 else 0)
    ).executeInsert()
  }
}

object User {
  def findByEmail(email: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where email = {email}").on(
      'email -> email
    ).as(user singleOpt)
  }

  def connect(email: String, password: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where email = {email} and password = {password}").on(
      'email -> email,
      'password -> password
    ).as(user singleOpt)
  }

  val user = {
    str("email") ~ str("password") ~ str("fullname") ~ str("is_admin") map {
      case email ~ password ~ fullname ~ isAdmin => User(email, password, fullname, if ("1" == isAdmin) true else false)
    }
  }
}


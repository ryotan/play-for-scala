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
case class User(id: Pk[Long], email: String, password: String, fullname: String, isAdmin: Boolean) {

  def save(): Option[Long] = DB.withConnection { implicit connection =>
    SQL("insert into Users(email, password, fullname, is_admin) values ({email}, {password}, {fullname}, {isAdmin})")
      .on(
        'email -> this.email,
        'password -> this.password,
        'fullname -> this.fullname,
        'isAdmin -> (if (isAdmin) 1 else 0)
      ).executeInsert()
  }
}

object User {
  def apply(email: String, password: String, fullname: String, isAdmin: Boolean) = new User(NotAssigned, email, password, fullname, isAdmin)

  def findById(id: Long) = DB.withConnection { implicit connection =>
    SQL("select * from Users where id = {id}").on(
      'id -> id
    ).as(user singleOpt)
  }

  def findByEmail(email: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where email = {email} order by id asc").on(
      'email -> email
    ).as(user *)
  }

  def connect(email: String, password: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where email = {email} and password = {password}").on(
      'email -> email,
      'password -> password
    ).as(user singleOpt)
  }

  val user = {
    get[Pk[Long]]("id") ~ str("email") ~ str("password") ~ str("fullname") ~ str("is_admin") map {
      case id ~ email ~ password ~ fullname ~ isAdmin =>
        User(id, email, password, fullname, "1" == isAdmin)
    }
  }
}


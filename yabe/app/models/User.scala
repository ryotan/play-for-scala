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
case class User(userId: Pk[Long], email: String, password: String, fullname: String, isAdmin: Boolean) {

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

  def findById(userId: Long) = DB.withConnection { implicit connection =>
    SQL("select * from Users where user_id = {userId}").on(
      'userId -> userId
    ).as(user singleOpt)
  }

  def findByEmail(email: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where email = {email} order by user_id asc").on(
      'email -> email
    ).as(user *)
  }

  def connect(userId: Long, password: String) = DB.withConnection { implicit connection =>
    SQL("select * from Users where user_id = {userId} and password = {password}").on(
      'userId -> userId,
      'password -> password
    ).as(user singleOpt)
  }

  val user = {
    get[Pk[Long]]("user_id") ~ str("email") ~ str("password") ~ str("fullname") ~ str("is_admin") map {
      case userId ~ email ~ password ~ fullname ~ isAdmin =>
        User(userId, email, password, fullname, "1" == isAdmin)
    }
  }
}


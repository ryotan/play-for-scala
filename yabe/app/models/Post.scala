package models

import java.util.Date
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._

/**
 *
 * @author ryotan
 * @since 1.0
 */
case class Post(title: String, content: String, author: User, postedAt: Date = new Date) {
  def save = DB.withConnection { implicit connection =>
    SQL("insert into Post(title, content, author_email, posted_at) values({title}, {content}, {author_email}, {posted_at})").on (
      'title -> this.title,
      'content -> this.content,
      'author_email -> this.author.email,
      'posted_at -> this.postedAt
    ).executeInsert()
  }
}

object Post {
}

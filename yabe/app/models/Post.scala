package models

import java.util.Date
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._

/**
 * Model class for blog post.
 *
 * @author ryotan
 * @since 1.0
 */
case class Post(title: String, content: String, author: User, postedAt: Date = new Date, id: Option[Long] = None) {
  def save(): Option[Long] = DB.withConnection { implicit connection =>
    SQL("insert into Posts(title, content, author_id, posted_at) values({title}, {content}, {authorId}, {postedAt})").on (
      'title -> this.title,
      'content -> this.content,
      'authorId -> this.author.id,
      'postedAt -> this.postedAt
    ).executeInsert()
  }
}

object Post {
  def findById(id: Long) = DB.withConnection { implicit connection =>
    SQL("select * from Posts where id = {id}").on (
      'id -> id
    ).as(post *)
  }

  val post = {
    long("id") ~ str("title") ~ str("content") ~ long("author_id") ~ date("posted_at") map {
      case id ~ title ~ content ~ authorId ~ postedAt =>
        new Post(title, content, User.findById(authorId).get, postedAt, Option(id))
    }
  }
}

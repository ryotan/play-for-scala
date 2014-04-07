package models

import anorm._
import anorm.SqlParser._
import java.util.Date
import play.api.db.DB
import play.api.Play.current

/**
 * Model for comments of blog post.
 *
 * @author ryotan
 * @since 1.0
 */
case class Comment(commentId: Pk[Long], author: String, postedAt: Date, content: String, postedOn: Long) {
  def save(): Option[Long] = DB.withConnection { implicit connection =>
    SQL("insert into Comments(author, posted_at, content, posted_on) values({author}, {postedAt}, {content}, {postedOn})").on(
      'author -> this.author,
      'postedAt -> this.postedAt,
      'content -> this.content,
      'postedOn -> this.postedOn
    ).executeInsert()
  }
}

object Comment {
  def apply(author: String, postedAt: Date = new Date, content: String,
    postedOn: Long) = new Comment(NotAssigned, author, postedAt, content, postedOn)

  def findById(commentId: Long) = DB.withConnection { implicit connection =>
    SQL("select * from Comments where comment_id = {commentId}").on(
    'commentId -> commentId
    ).singleOpt(simple)

  }

  val simple = {
    get[Pk[Long]]("comment_id") ~ str("author") ~ date("posted_at") ~ str("content") ~ long("posted_on") map {
      case commentId ~ author ~ postedAt ~ content ~ postedOn =>
        Comment(commentId, author, postedAt, content, postedOn)
    }
  }
}

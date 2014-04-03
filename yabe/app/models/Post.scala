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
case class Post(postId: Pk[Long], title: String, content: String, author: User, postedAt: Date) {
  def save(): Option[Long] = DB.withConnection { implicit connection =>
    SQL("insert into Posts(title, content, author_id, posted_at) values({title}, {content}, {authorId}, {postedAt})").on(
      'title -> this.title,
      'content -> this.content,
      'authorId -> this.author.userId,
      'postedAt -> this.postedAt
    ).executeInsert()
  }
}

object Post {
  def apply(title: String, content: String, author: User, postedAt: Date = new Date) = new Post(NotAssigned, title, content, author, postedAt)

  def findById(postId: Long) = DB.withConnection { implicit connection =>
    SQL("select * from Posts where post_id = {postId}").on(
      'postId -> postId
    ).as(post *)
  }

  val post = {
    get[Pk[Long]]("post_id") ~ str("title") ~ str("content") ~ long("author_id") ~ date("posted_at") map {
      case postId ~ title ~ content ~ authorId ~ postedAt =>
        new Post(postId, title, content, User.findById(authorId).get, postedAt)
    }
  }
}

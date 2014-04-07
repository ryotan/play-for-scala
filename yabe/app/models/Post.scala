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
  def addComment(author: String, content: String): Option[Long] = {
    Comment(author, new Date, content, this.postId.get).save()
  }

  def findComments() = DB.withConnection { implicit connection =>
    SQL("select * from Posts join Comments on Posts.post_id = Comments.posted_on where Posts.post_id = {postId} order by comment_id asc").on(
      'postId -> this.postId.get
    ).list(Comment.simple)
  }

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
    SQL("select * from Posts join Users on Posts.author_id = Users.user_id where post_id = {postId}").on(
      'postId -> postId
    ).singleOpt(simple)
  }

  def newest() = DB.withConnection { implicit connection =>
    SQL(" select *" +
        " from Posts" +
        "   join Users" +
        "     on Posts.author_id = Users.user_id" +
        " order by posted_at desc" +
        " limit 1"
    ).asSimple(simple).singleOpt()
  }

  val simple = {
    get[Pk[Long]]("post_id") ~ str("title") ~ str("content") ~ date("posted_at") ~ User.simple map {
      case postId ~ title ~ content ~ postedAt ~ user =>
        new Post(postId, title, content, user, postedAt)
    }
  }
}

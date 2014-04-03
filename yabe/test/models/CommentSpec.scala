package models

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import java.util.Date

/**
 *
 * @author ryotan
 * @since 1.0
 */
class CommentSpec extends Specification {
  "Comment" should {
    "persist comments and select comment by id" in new WithApplication {
      val current = new Date()
      val authorId = User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val postId = Post("title", "contents", User.findById(authorId).get, current).save().get
      val commentId = Comment("comment_author", current, "content", postId).save().get

      val comment = Comment.findById(commentId).get
      comment.author === "comment_author"
      comment.postedAt.getTime === current.getTime
      comment.content === "content"
      comment.commentId.get === commentId
    }
  }
}

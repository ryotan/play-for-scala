package models

import org.specs2.mutable.Specification
import play.api.test.WithApplication
import java.util.Date

/**
 * Spec class for [[Post]].
 *
 * @author ryotan
 * @since 1.0
 */
class PostSpec extends Specification {
  "Post" should {
    "persist blog post and find post by id" in new WithApplication {
      val current = new Date
      val authorId = User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val postId = Post("title", "contents", User.findById(authorId).get, current).save().get

      val post = Post.findById(postId).get
      post.title === "title"
      post.content === "contents"
      post.postedAt.getTime === current.getTime
      post.author.userId.get === authorId
    }

    "add a comment" in new WithApplication {
      val current = new Date
      val authorId = User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val postId = Post("title", "contents", User.findById(authorId).get, current).save().get

      val post = Post.findById(postId).get
      post.findComments().size === 0

      post.addComment("comment author 1", "comment")
      post.addComment("comment author 2", "comment")

      val comments = post.findComments()
      comments.size === 2

      comments(0).commentId.get === 1
      comments(1).commentId.get === 2

      comments(0).author === "comment author 1"
      comments(1).author === "comment author 2"
    }
  }
}

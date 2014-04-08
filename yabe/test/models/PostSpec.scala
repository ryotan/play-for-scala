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

    "find the newest Post" in new WithApplication {
      val current = new Date
      val authorId = User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val user = User.findById(authorId).get
      val second = Post("title", "contents2", user, current).save().get
      val first = Post("title", "contents1", user, new Date(current.getTime + 10000)).save().get
      val third = Post("title", "contents3", user, new Date(current.getTime - 10000)).save().get

      val post = Post.newest().get
      post.postId.get === first
    }

    "find older Posts" in new WithApplication {
      val current = new Date
      val authorId = User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val user = User.findById(authorId).get
      val third = Post("title", "contents3", user, new Date(current.getTime - 10000)).save().get
      val first = Post("title", "contents1", user, new Date(current.getTime + 10000)).save().get
      val second = Post("title", "contents2", user, current).save().get

      val posts = Post.older(1, 2)
      posts(0).postId.get === second
      posts(1).postId.get === third
    }
  }
}

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
    "persist blog post" in new WithApplication {
      val current = new Date()
      val authorId = new User("author@example.com", "pwd", "Blog Author", isAdmin = false).save().get
      val postId = new Post("title", "contents", User.findById(authorId).get, current).save().get

      val post = Post.findById(postId).head
      post.title === "title"
      post.content === "contents"
      post.postedAt.getTime === current.getTime
    }
  }
}

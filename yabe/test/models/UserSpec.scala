package models

import org.specs2.mutable.Specification
import play.api.test.WithApplication

/**
 * Spec class for [[User]].
 *
 * @author ryotan
 * @since 1.0
 */
class UserSpec extends Specification {
  "User" should {
    "save user information" in new WithApplication {
      new User("bob@example.com", "secret", "Bob", isAdmin = false).save()

      val bob = User.findByEmail("bob@example.com").get
      bob.email === "bob@example.com"
      bob.fullname must_== "Bob"
    }
  }
}

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
      new User("bob@example.com", "secret", "Bob", isAdmin = false).save().get === 1
      new User("admin@example.com", "secret", "Admin", isAdmin = true).save().get === 2

      val bob = User.findByEmail("bob@example.com").get
      bob.email === "bob@example.com"
      bob.fullname === "Bob"
      bob.isAdmin === false

      val admin = User.findByEmail("admin@example.com").get
      admin.isAdmin === true

      val none = User.findByEmail("none")
      none.isDefined === false
    }

    "provide connection as a user" in new WithApplication {
      new User("bob@example.com", "secret", "Bob", isAdmin = false).save().get === 1

      User.connect("bob@example.com", "secret").isDefined === true
      User.connect("bob@example.com", "not secret").isDefined === false
      User.connect("not a user email", "secret").isDefined === false
    }
  }
}

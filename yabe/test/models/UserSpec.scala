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
      User("bob@example.com", "secret", "Bob", isAdmin = false).save().get === 1
      User("admin@example.com", "secret", "Admin", isAdmin = true).save().get === 2
      User("bob@example.com", "secret", "Admin", isAdmin = true).save().get === 3

      val found = User.findByEmail("bob@example.com")
      found.size === 2

      val bob = found.head
      bob.userId.get === 1
      bob.email === "bob@example.com"
      bob.fullname === "Bob"
      bob.isAdmin === false

      val admin = User.findByEmail("admin@example.com").head
      admin.isAdmin === true

      val none = User.findByEmail("none").headOption
      none.isDefined === false
    }

    "find user by id" in new WithApplication {
      val id = User("bob@example.com", "secret", "Bob", isAdmin = false).save().get

      val bob = User.findById(id).get
      bob.email === "bob@example.com"
    }

    "provide connection as a user" in new WithApplication {
      val id = User("bob@example.com", "secret", "Bob", isAdmin = false).save().get

      User.connect(id, "secret").isDefined === true
      User.connect(id, "not secret").isDefined === false
      User.connect(id + 1, "secret").isDefined === false
    }
  }
}

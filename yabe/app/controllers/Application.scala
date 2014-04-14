package controllers

import play.api.mvc._
import models.Post

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(Post.newest(), Post.older(1, 10)))
  }

  def show(id: Long) = Action {
    val post = Post.findById(id).get
    Ok(views.html.show(post, post.previous(), post.next()))
  }

}

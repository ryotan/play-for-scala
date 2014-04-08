package controllers

import play.api.mvc._
import models.Post

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(Post.newest(), Post.older(1, 10)))
  }

}

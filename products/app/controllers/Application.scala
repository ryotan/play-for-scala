package controllers

import play.api._
import play.api.mvc._
import play.api.i18n.Messages

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(s"Your new application is ready. Application name is ${Messages("application.name")}"))
  }

}

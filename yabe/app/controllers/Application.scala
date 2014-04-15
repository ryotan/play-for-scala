package controllers

import play.api.mvc._
import models.Post
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(Post.newest(), Post.older(1, 10)))
  }

  def show(id: Long) = Action {
    val post = Post.findById(id).get
    Ok(views.html.show(post, post.previous(), post.next(), commentForm))
  }

  val commentForm = Form(
    mapping(
      "author" -> nonEmptyText,
      "content" -> nonEmptyText
    )(CommentData.apply)(CommentData.unapply)
  )

  def postComment(postId: Long) = Action { implicit request =>
    val commentData = commentForm.bindFromRequest.get

    val post = Post.findById(postId).get
    post.addComment(commentData.author, commentData.content)
    Redirect(routes.Application.show(postId))
  }

}

case class CommentData(author: String, content: String)


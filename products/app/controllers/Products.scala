package controllers

import play.api.mvc.{Flash, Action, Controller}
import models.Product
import play.api.data.Form

import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages


object Products extends Controller {
  def list = Action { implicit request =>

    val products = Product.findAll

    Ok(views.html.products.list(products = products))
  }

  def show(ean: Long) = Action { implicit request =>
    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)
  }

  def save = Action { implicit request =>
    val newProductForm = productForm.bindFromRequest()

    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct())
          .flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
      },

      success = { newProduct =>
        Product.add(newProduct)
        Redirect(routes.Products.show(newProduct.ean))
          .flashing("success" -> Messages("products.new.success", newProduct.name))
      }
    )
  }

  def newProduct = Action {implicit request =>
    val form = if (flash.get("error").isDefined)
      productForm.bind(flash.data)
    else {
      productForm
    }

    Ok(views.html.products.edit(form))
  }

  private val productForm: Form[Product] = Form (
    mapping(
      "ean" -> longNumber.verifying("validation.ean.duplicate", Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    ) (Product.apply) (Product.unapply)
  )

}

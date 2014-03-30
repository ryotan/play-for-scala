package models

import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Product(ean: Long, name: String, description: String)

object Product {

  def findAll = DB.withConnection {
    implicit conn =>
      SQL("select * from PRODUCTS").as(product *)
  }

  def findByEan(ean: Long) = DB.withConnection {
    implicit conn =>
      SQL("select * from PRODUCTS where ean = {ean}").on(
        'ean -> ean
      ).as(product singleOpt)
  }

  def add(product: Product) = DB.withConnection {
    implicit conn =>
      SQL("insert into PRODUCTS(EAN, NAME, DESCRIPTION) values({ean}, {name}, {description})").on(
        'ean -> product.ean,
        'name -> product.ean,
        'description -> product.description
      ).executeInsert()
  }

  val product = {
    long("ean") ~ str("name") ~ str("description") map {
      case ean ~ name ~ description => Product(ean, name, description)
    }
  }
}

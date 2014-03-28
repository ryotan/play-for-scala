package controllers

import play.api.mvc.{Action, Controller}
import java.io.ByteArrayOutputStream
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
import java.awt.image.BufferedImage
import org.krysalis.barcode4j.impl.upcean.EAN13Bean

object Barcodes extends Controller {

  val ImageResolution = 144

  def barcode(ean: Long) = Action {
    val MimeType = "image/png"
    try {
      val imageData = ean13Barcode(ean, MimeType)
      Ok(imageData).as(MimeType)
    } catch {
      case e: IllegalArgumentException =>
        BadRequest("Couldn't generate bar code. Error " + e.getMessage)
    }
  }

  def ean13Barcode(ean: Long, mimeType: String) = {
    val output: ByteArrayOutputStream = new ByteArrayOutputStream()
    val canvas: BitmapCanvasProvider = new BitmapCanvasProvider(
      output, mimeType, ImageResolution, BufferedImage.TYPE_BYTE_BINARY, false, 0
    )
    val barcode = new EAN13Bean()
    barcode.generateBarcode(canvas, String valueOf ean)
    canvas.finish()
    output.toByteArray

  }

}

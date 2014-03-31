package models

case class Warehouse(id: Long, name: String)

case class StockItem(id: Long, productId: Long, warehouseId: Long, quantity: Long)

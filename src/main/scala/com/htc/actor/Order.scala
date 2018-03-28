package com.htc.actor

import java.util.UUID

sealed trait Item{
  val price:Int
  val spendSeconds:Int
}

sealed trait Drink extends Item

case class Coke(price: Int,spendSeconds: Int=3) extends Drink

sealed trait MainMeal extends Item

case class Sandwich(price:Int,spendSeconds:Int=10) extends MainMeal

trait SubMeal extends Item

case class Fries(price:Int,spendSeconds:Int=7) extends SubMeal


case class Order(id:UUID=UUID.randomUUID(),content:MenuItem)
sealed trait MenuItem
case class MealSet(mainMeal: MainMeal,subMeal: SubMeal,drink: Drink) extends MenuItem
case class MealSingle(item: Item) extends MenuItem
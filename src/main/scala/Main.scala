import java.util.UUID

import akka.actor.{Actor, ActorSystem, Props}
import com.htc.actor._
import com.typesafe.config.ConfigFactory

object Main extends App {
  val system=ActorSystem("order-client",ConfigFactory.load("application.conf"),getClass.getClassLoader)

  val clientActor=system.actorOf(Props[ClientActor],"client-actor")
  clientActor ! com.htc.actor.Order(content = com.htc.actor.MealSet(com.htc.actor.Sandwich(20),com.htc.actor.Fries(10),com.htc.actor.Coke(3)))
  clientActor! Order(content=MealSingle(Coke(4)))
  clientActor ! com.htc.actor.Order(content = com.htc.actor.MealSet(com.htc.actor.Sandwich(20),com.htc.actor.Fries(10),com.htc.actor.Coke(3)))
  clientActor ! com.htc.actor.Order(content=com.htc.actor.MealSingle(com.htc.actor.Coke(4)))
  clientActor ! com.htc.actor.Order(content=com.htc.actor.MealSingle(com.htc.actor.Sandwich(25)))
  clientActor ! com.htc.actor.Order(content = com.htc.actor.MealSet(com.htc.actor.Sandwich(20),com.htc.actor.Fries(10),com.htc.actor.Coke(3)))

}


class ClientActor extends Actor{

  val remote = context.actorSelection("akka.tcp://order-post@127.0.0.1:2552/user/order-receiver")
  override def receive: Receive = {
    case order :Order=>
      println(s"I send an order ${order.id}")
      remote ! order
    case finish:Finish=>
      println(finish)

  }
}
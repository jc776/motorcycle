package com.jc776.motorcycle
import me.shadaj.slinky.web.html._
import org.scalajs.dom

object Program {
  type Model = Int
  sealed trait Msg
  case object Increment extends Msg
  case object Decrement extends Msg
  type Cmd = Option[String]
  
  def init: Model = 0
  
  def update(model: Model, action: Msg): (Model, Cmd) = action match {
    case Increment => (model + 1, None)
    case Decrement => (model - 1, None)
  }
  
  def view(model: Model, send: Msg => Unit) = {
    div(
      button("-", onClick := {(e: dom.Event) => send(Decrement)})," ",
      span(s"Count - ${model}")," ",
      button("+", onClick := {(e: dom.Event) => send(Increment)})
    )
  }
}
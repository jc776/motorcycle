package com.jc776.motorcycle.dev
import com.jc776.motorcycle.Program 
import me.shadaj.slinky.core.facade.ReactElement
import me.shadaj.slinky.web.html._
import org.scalajs.dom
//import scalajs.js

object ElmCounter extends Program {
  type Model = Int
  sealed trait Msg
  case object Increment extends Msg
  case object Decrement extends Msg
  type Cmd = Unit
  
  def init: Model = 0
  def update(model: Model, action: Msg): (Model, Cmd) = action match {
    case Increment => (model + 1, ())
    case Decrement => (model - 1, ())
  }
  def view(model: Model, send: Msg => Unit): ReactElement = {
    div(
      button("-", onClick := {(e: dom.Event) => send(Decrement)})," ",
      span(s"Count - ${model}")," ",
      button("+", onClick := {(e: dom.Event) => send(Increment)})
    )
  }
  def run(cmd: Cmd): Unit = {}
}
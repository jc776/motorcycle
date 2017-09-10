package com.jc776.motorcycle.dev
import me.shadaj.slinky.core.Component
import me.shadaj.slinky.web.html._
import org.scalajs.dom
import scalajs.js

// Works normally, but on hot-reload it fails with MatchError
object ReactState extends Component {
  type Props = Unit
  sealed trait State
  case object A extends State
  case object B extends State

  class Def(jsProps: js.Object) extends Definition(jsProps) {
    def initialState = A
    
    def render() = {
      state match {
        case A => button("B!!", onClick := {(e: dom.Event) => setState(B)})
        case B => button("A!!", onClick := {(e: dom.Event) => setState(A)}) 
      }
    }
  }
}
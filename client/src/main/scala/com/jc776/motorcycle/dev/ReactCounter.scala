package com.jc776.motorcycle.dev
import me.shadaj.slinky.core.Component
import me.shadaj.slinky.web.html._
import org.scalajs.dom
import scalajs.js

object ReactCounter extends Component {
  type Props = Unit
  type State = Int

  class Def(jsProps: js.Object) extends Definition(jsProps) {
    def initialState = 0
    def render() = div(
      p(s"Count is now $state"),
      button("+", onClick := {(e: dom.Event) => setState(state + 1)})
    )
  }
}
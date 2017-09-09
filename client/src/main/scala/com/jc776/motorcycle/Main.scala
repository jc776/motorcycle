package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}
import me.shadaj.slinky.web.ReactDOM
import org.scalajs.dom
import me.shadaj.slinky.web.html._

@JSExportTopLevel("Motorcycle")
object Motorcycle {
  @JSExport
  def start(): Unit = {
    val _ = ReactDOM.render(div("Hello, react?"), dom.document.getElementById("app"))
  }

  // multiple bundles? maybe.
  // def devcards(): Unit = { ... }
}
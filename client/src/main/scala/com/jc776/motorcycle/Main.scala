package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}
import me.shadaj.slinky.web.ReactDOM
import org.scalajs.dom
import me.shadaj.slinky.web.html._
import dev.Atom 

@JSExportTopLevel("Motorcycle")
object Motorcycle {
  @JSExport
  def start(): Unit = {
    val atom = dev.defonce { new Atom(1) }
    atom.value += 1
    
    val _ = ReactDOM.render(div(s"Updated! ${atom.value}"), dom.document.getElementById("app"))
  }

  // multiple bundles? maybe.
  // def devcards(): Unit = { ... }
}
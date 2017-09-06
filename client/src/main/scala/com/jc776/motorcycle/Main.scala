package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}

@JSExportTopLevel("Motorcycle")
object Motorcycle {
  @JSExport
  def start(): Unit = {
    println("Runs on change...?")
  }

  // def devcards(): Unit = { ... }
}
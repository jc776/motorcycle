package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}
import me.shadaj.slinky.web.ReactDOM
import me.shadaj.slinky.hot
import org.scalajs.dom
import slogging.LazyLogging
import slogging.LoggerConfig
import slogging.ConsoleLoggerFactory

@JSExportTopLevel("Motorcycle")
object Main extends LazyLogging {
  LoggerConfig.factory = ConsoleLoggerFactory()
  hot.initialize()

  //val app = new App(Test)()
  
  @JSExport
  def start(): Unit = {
    val _ = ReactDOM.render(dev.ReactState(), dom.document.getElementById("app"))
  }

  // multiple bundles? maybe.
  // def devcards(): Unit = { ... }
}
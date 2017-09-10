package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}
import me.shadaj.slinky.web.ReactDOM
import me.shadaj.slinky.hot
import org.scalajs.dom
import slogging.LazyLogging
import slogging.LoggerConfig
import slogging.ConsoleLoggerFactory

sealed trait Thing
case object One extends Thing 
case object Two extends Thing 

@JSExportTopLevel("Motorcycle")
object Main extends LazyLogging {
  LoggerConfig.factory = ConsoleLoggerFactory()
  //hot.initialize()
  
  @JSExport
  def start(): Unit = {
    
    val x = dev.defonce[Thing]{ One }
    try {
      x match {
        case One => println("One.")
        case Two => println("Two.")
      }
    } catch {
      case ex: MatchError => ex.printStackTrace()
    }
    
  }

  // multiple bundles? maybe.
  // def devcards(): Unit = { ... }

  
  //val app = new App(Test)()
  //val _ = ReactDOM.render(dev.ReactCounter(), dom.document.getElementById("app"))
}
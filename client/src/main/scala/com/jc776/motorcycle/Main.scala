package com.jc776.motorcycle
import scalajs.js
import js.annotation.{JSExportTopLevel, JSExport}
import me.shadaj.slinky.web.ReactDOM
import org.scalajs.dom
import dev.Atom 
import Program.{Model, Msg, Cmd}
import slogging.LazyLogging
import slogging.LoggerConfig
import slogging.ConsoleLoggerFactory

@JSExportTopLevel("Motorcycle")
object Motorcycle extends LazyLogging {
  LoggerConfig.factory = ConsoleLoggerFactory()
  
  val atom: Atom[Model] = dev.defonce { new Atom(Program.init) }
  
  def send(action: Msg) = {
    val (next, cmd) = Program.update(atom.value, action)
    atom.value = next
    run(cmd)
    render()
  }
  
  def run(cmd: Cmd) = {
    val _ = cmd
  }
  
  def render(): Unit = {
    val _ = ReactDOM.render(Program.view(atom.value, send), dom.document.getElementById("app"))
  }
  
  @JSExport
  def start(): Unit = {
    logger.info("Hello!")
    render()
  }

  // multiple bundles? maybe.
  // def devcards(): Unit = { ... }
}
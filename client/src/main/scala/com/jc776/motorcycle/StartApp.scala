package com.jc776.motorcycle
import scalajs.js 
import me.shadaj.slinky.core.Component
import slogging.LazyLogging

import me.shadaj.slinky.core.facade.ReactElement

trait Program {
  type Model
  type Msg
  type Cmd
  
  def init: Model
  def update(model: Model, action: Msg): (Model, Cmd)
  def view(model: Model, send: Msg => Unit): ReactElement
  def run(cmd: Cmd): Unit
}

// You don't *need* React to hold the state (e.g. dev.defonce)
// but this uses slinky's type <-> jsliteral
// to avoid e.g. MatchError - hot-update.js LoggedOut != bundle.js LoggedOut"

class App[P <: Program](val program: P) extends Component with LazyLogging {
  type Props = Unit
  type State = program.Model 

  class Def(jsProps: js.Object) extends Definition(jsProps) {
    def initialState = program.init
    def render() = program.view(state, send)
    
    def send(action: program.Msg): Unit = {
      val (next, cmd) = program.update(state, action)
      logger.info(s"(${next}, ${cmd})")
      setState(next)
      program.run(cmd)
    }
    
    /*
    def run(msgs: program.Cmd) = {
      for(msg <- msgs) {
        ws.send(model.encodeClientMsg(msg))
      }
    }
    */
    
    /*
    //TODO: GLOBAL STATE!
    // https://github.com/mehmetkose/react-websocket ?
    val ws = dev.defonce {new ReconnectingWebSocket(websocketURI)}
    // wrap - mkMsg(read[In]), statusMsg(WebsocketOpen), send[Out]
    ws.onopen = (e: dom.Event) => send(Program.WebsocketOpen)
    ws.onerror = (e: dom.ErrorEvent) => send(Program.ClientError(e.toString))
    ws.onclose = (e: dom.CloseEvent) => send(Program.WebsocketClosed(e.reason))
    ws.onmessage = (e: dom.MessageEvent) => onMessage(e.data.toString)
    def onMessage(json: String) = {
      model.decodeServerMsg(json) match {
        case Left(err) => send(Program.ClientError(err.toString))
        case Right(msg) => send(Program.Server(msg))
      }
    }
    
    def websocketURI: String = {
      val wsProtocol = if (dom.document.location.protocol == "https:") "wss" else "ws"
      s"$wsProtocol://${dom.document.location.host}/ws"
    }
    */
  }
}
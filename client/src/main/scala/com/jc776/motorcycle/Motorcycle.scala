package com.jc776.motorcycle
import me.shadaj.slinky.web.html._
import org.scalajs.dom
import com.jc776.motorcycle.model.ServerMessage
import slogging.LazyLogging
import com.jc776.motorcycle.model.ClientMessage
import com.jc776.motorcycle.model.Connect
import me.shadaj.slinky.core.facade.ReactElement

object Motorcycle extends Program with LazyLogging {
  case class Model(
    connectInfo: Connect = Connect("",0,""),
    connected: Connected = Disconnected("Connecting to proxy...")
  )
  sealed trait Connected
  case class Disconnected(msg: String) extends Connected {
    def view = div(
      h2(msg)
    )
  }
  def evText[T](send: T => Unit, msg: String => T): (dom.Event => Unit) = (e: dom.Event) => {
    send(msg(e.target.asInstanceOf[dom.html.Input].value))
  }
    
  case object LoggedOut extends Connected {
    def view(model: Connect, send: Msg => Unit) = {
      def sendC(m: Connect) = send(ConnectInfo(m))
      div(
        h2("Remote Server"),
        p(label("Host"),input(value := model.host, onChange := evText(sendC, s => model.copy(host = s)))),
        p(label("Name"),input(value := model.name, onChange := evText(sendC, s => model.copy(name = s)))),
        p(button("Connect"), onClick := {() => send(ConnectButton)})
      )
    }
  }
  case object LoggedIn extends Connected
  
  //sealed trait Screen
  //case object Login extends Screen
  
  sealed trait Msg
  case class ConnectInfo(info: Connect) extends Msg
  case object ConnectButton extends Msg
  case class ClientError(message: String) extends Msg 
  case object WebsocketOpen extends Msg
  case class WebsocketClosed(reason: String) extends Msg
  case class Server(msg: ServerMessage) extends Msg
  type Cmd = Option[ClientMessage]
  
  def init: Model = Model()
  
  def update(model: Model, action: Msg): (Model, Cmd) = {
    logger.info(action.toString())
    action match {
      case ConnectInfo(info) => (model.copy(connectInfo = info), None)
      case ConnectButton => (model, Some(model.connectInfo))
      case ClientError(msg) => (model, None)
      case WebsocketOpen => (model.copy(connected = LoggedOut), None)
      case WebsocketClosed(msg) => (model.copy(connected = Disconnected(msg)), None)
      case Server(_) => (model.copy(connected = LoggedIn), None)
    }
  }
  
  def view(model: Model, send: Msg => Unit): ReactElement = {
    // MatchError - hot-update.js's LoggedOut != bundle.js's LoggedOut, etc
    // model.view(...) - then it never updates
    model.connected match {
      case x: Disconnected => x.view
      case x: LoggedOut.type => x.view(model.connectInfo, send)
      case _: LoggedIn.type => div("Yay!")
    }
  }
  
  def run(msgs: Cmd) = {
    for(msg <- msgs) {
      println(model.encodeClientMsg(msg))
      //ws.send(model.encodeClientMsg(msg))
    }
  }
}
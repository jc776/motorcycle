package com.jc776.motorcycle
import scalajs.js
import js.annotation.JSImport
import org.scalajs.dom

@js.native
@JSImport("reconnecting-websocket", JSImport.Namespace)
class ReconnectingWebSocket(url: String) extends dom.WebSocket(url) 
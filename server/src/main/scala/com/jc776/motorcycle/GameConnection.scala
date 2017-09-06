package com.jc776.motorcycle
import slogging.LazyLogging
import io.undertow.websockets.core.WebSocketChannel
//import io.undertow.websockets.core.WebSockets

class GameConnection(val client: WebSocketChannel) extends LazyLogging {
  def onClientText(text: String): Unit = {
    logger.info(text)
  }
}
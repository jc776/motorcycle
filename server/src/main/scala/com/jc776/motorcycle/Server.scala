package com.jc776.motorcycle

import io.undertow.Undertow
import io.undertow.Handlers.{path, resource, websocket}
import io.undertow.server.handlers.resource.ClassPathResourceManager
import io.undertow.websockets.core.{AbstractReceiveListener, BufferedTextMessage, StreamSourceFrameChannel, WebSocketChannel}
import io.undertow.websockets.spi.WebSocketHttpExchange
import collection.mutable
import slogging.LazyLogging

import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

class Server extends LazyLogging {
  def listen(host: String, port: Int): Unit = {
    val publicFiles = new ClassPathResourceManager(getClass().getClassLoader(), "public")
            
    Undertow.builder()
      // https? leave it to e.g. nginx?
      .addHttpListener(port, host)
      .setHandler(path()
        .addPrefixPath("/ws", websocket(onConnect))
        .addPrefixPath("/", resource(publicFiles)
          .addWelcomeFiles("index.html")
          .setDirectoryListingEnabled(true)
        )
        .addPrefixPath("/hello", (ctx: HttpServerExchange) => {
          ctx.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain")
          ctx.getResponseSender().send("Hello Changed")
        })
      )
      .build()
      .start()
    logger.info(s"Listening on $host:$port.")
  }
  
  val connections = mutable.Map[WebSocketChannel, GameConnection]()

  def onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel): Unit = {
    val _ = exchange // ignore
    //removeDeadConnections()
    logger.info(s"Someone connected.")
    connections(channel) = new GameConnection(channel)
    channel.getReceiveSetter().set(new AbstractReceiveListener() {
      override def onFullTextMessage(channel: WebSocketChannel, message: BufferedTextMessage) = {
        val text = message.getData()
        connections.get(channel).foreach { conn =>
          conn.onClientText(text)
        }
      }

      override def onClose(channel: WebSocketChannel, streamSourceChannel: StreamSourceFrameChannel): Unit = {
        connections.remove(channel)
        logger.info("Someone disconnected.")
      }
    })
    channel.resumeReceives()
  }
}
package com.jc776.motorcycle
import slogging.LazyLogging
import slogging.LoggerConfig
import slogging.PrintLoggerFactory

object Main extends LazyLogging {
  LoggerConfig.factory = PrintLoggerFactory()
  
  def main(args: Array[String]): Unit = {
    new Server().listen("localhost", 3000)
  }
}
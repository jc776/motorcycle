package com.jc776.motorcycle.dev
import scalajs.js 
import scala.scalajs.js.Any.wrapDictionary

class Atom[T](var value: T)

object `package` {
  def defonce[T](name: String)(init: => T): T = {
    val dict = js.Dynamic.global.asInstanceOf[js.Dictionary[T]]
    dict.get(name) match {
      case Some(inst) => inst 
      case None => {
        val inst: T = init 
        dict(name) = inst
        inst
      }
    }
  }
  
  def defonce[T](init: => T)(implicit path: sourcecode.Name): T = {
    defonce(path.value)(init)
  }
}
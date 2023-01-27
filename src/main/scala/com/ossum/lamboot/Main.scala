package com.ossum.lamboot

import com.raquo.domtypes.generic.Modifier
import org.scalajs.dom
import dom.html.Div
import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.document

/** The main program for the JavaScript application */
object Main:
  def main(args: Array[String]): Unit =
    println("Lambot Main.main is running!")
    document.addEventListener("DOMContentLoaded", { (e: dom.Event) =>
      setupUI()
    })

  def setupUI(): Unit =
    Bootstrap.main()


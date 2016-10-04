package net.paulgray.raspberry

import Digit._
import net.paulgray.raspberry.Main.{Display, Columns, Segments}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nicole on 9/29/16.
 */
object App {

  def run(display: Display) = {

    val inputMap: Map[String, Digit] = Map(
      "X" -> blank,
      "0" -> zero,
      "1" -> one,
      "2" -> two,
      "3" -> three,
      "4" -> four,
      "5" -> five,
      "6" -> six,
      "7" -> seven,
      "8" -> eight,
      "9" -> nine
    )

    var state: (Boolean, FourDigitDisplay) = (true, FourDigitDisplay())

    val renderThread = Future {
      var running = true
      while(running) {
        val (stillRun, fdDisplay) = state

        fdDisplay.render(display)

        running = stillRun
      }
      true
    }

    while(state._1){
      val input = scala.io.StdIn.readLine().trim()
      val (running, display) = state
      input match {
        case "kill" =>
          state = (false, display)
        case a =>
          val columns =  a.split(" ").toStream append Stream.continually("X")

          val inputs = columns.take(4).map(inputMap.get).toList

          println(s"parsed inputs: $inputs")

          state = (true, display.copy(
            column1 = inputs(0).getOrElse(blank),
            column2 = inputs(1).getOrElse(blank),
            column3 = inputs(2).getOrElse(blank),
            column4 = inputs(3).getOrElse(blank)
          ))
          println(s"state is now: $state")
      }
    }
    display.segments.foreach { case (_, p) => p.low() }
    println("Goodbye...")
  }

}

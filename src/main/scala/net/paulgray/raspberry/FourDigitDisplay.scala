package net.paulgray.raspberry

import net.paulgray.raspberry.Digit._
import net.paulgray.raspberry.Main.{Display, Columns, Segments}

/**
 * Created by nicole on 10/2/16.
 */
case class FourDigitDisplay(
  column1: Digit = blank,
  column2: Digit = blank,
  column3: Digit = blank,
  column4: Digit = blank
) {

  val Interval = 1

  def renderColumn(d: Digit, c: Column)(implicit display: Display) = {
    //clear all the segments
    display.segments foreach { case (_, pin) => pin.low() }

    val (colOn, colOff) = display.columns.partition(a => c == a._1)
    colOn.foreach(_._2.low())
    colOff.foreach(_._2.high())

    renderDigit(d, c)

    Thread.sleep(Interval)
  }

  def renderDigit(d: Digit, c: Column)(implicit display: Display) = {
    //set all other columns to high, except the one we're displaying on
    val (on, off) = display.segments.partition(a => d.segments.contains(a._1))
    on.foreach(_._2.high())
    off.foreach(_._2.low())
  }

  def render(implicit display: Display) = {
    renderColumn(column1, C1)
    renderColumn(column2, C2)
    renderColumn(column3, C3)
    renderColumn(column4, C4)
  }

}

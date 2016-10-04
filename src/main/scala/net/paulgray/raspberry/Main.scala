package net.paulgray.raspberry

import com.pi4j.io.gpio._
import com.pi4j.io.gpio.RaspiBcmPin._

/**
  * Created by paul on 7/20/16.
  */
object Main {

  type Segments = Map[Segment, GpioPinDigitalOutput]
  type Columns = Map[Column, GpioPinDigitalOutput]

  case class Display(segments: Segments, columns: Columns)

  def gpio = GpioFactory.getInstance()

  /**
   *
   *    ---A---
   *   |       |
   *   F       B
   *   |       |
   *   |---G---|
   *   |       |
   *   E       C
   *   |       |
   *    ---D---  DP
   *
   * @return
   */
  def segments(): Segments = Map(
    A  -> raspiPin(GPIO_23),
    B  -> raspiPin(GPIO_08),
    C  -> raspiPin(GPIO_15),
    D  -> raspiPin(GPIO_24),
    E  -> raspiPin(GPIO_07),
    F  -> raspiPin(GPIO_18),
    G  -> raspiPin(GPIO_25),
    DP -> raspiPin(GPIO_14)
  )

  def columns(): Columns = Map(
    C1 -> raspiPin(GPIO_12),
    C2 -> raspiPin(GPIO_16),
    C3 -> raspiPin(GPIO_03),
    C4 -> raspiPin(GPIO_21)
  )

  def raspiPin(pin: Pin, pinState: PinState = PinState.LOW): GpioPinDigitalOutput =
    gpio.provisionDigitalOutputPin(
      pin,
      pinState)

  def main(args: Array[String]): Unit = {
    println("initializing..")
    GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING))

    println("Setup broadcom.")
    val segs = segments()
    println("setup segments.")
    val cols = columns()
    println("setup columns.")

    App.run(Display(segs, cols))

  }

}




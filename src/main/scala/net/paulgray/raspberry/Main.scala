package net.paulgray.raspberry

import com.pi4j.io.gpio._
import com.pi4j.io.gpio.RaspiBcmPin._

/**
  * Created by paul on 7/20/16.
  */
object Main {

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
  def segments(): Map[String, (GpioPinDigitalOutput)] = Map(
    "A"  -> raspiPin(GPIO_23),
    "B"  -> raspiPin(GPIO_08),
    "C"  -> raspiPin(GPIO_15),
    "D"  -> raspiPin(GPIO_24),
    "E"  -> raspiPin(GPIO_07),
    "F"  -> raspiPin(GPIO_18),
    "G"  -> raspiPin(GPIO_25),
    "DP" -> raspiPin(GPIO_14),
    "C1" -> raspiPin(GPIO_12),
    "C2" -> raspiPin(GPIO_16),
    "C3" -> raspiPin(GPIO_03),
    "C4" -> raspiPin(GPIO_21)
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
    while(true){
      println("ready for input...")

      val ln = io.StdIn.readLine()

      val segment = ln.trim.toUpperCase

      segs.get(segment) match {
        case Some(pin) =>
          println(s"Changing segment: $segment, from ${pin.getState} to: ${pin.getState.reverse}")
          pin.setState(pin.getState.reverse)
        case None =>
          if(segment == "Z") {
            println("Goodbye.")
            segs foreach {
              case (key, pin) =>
                if(pin.getState.equals(PinState.HIGH)){
                  println(s"  shutting down segment $key")
                  pin.setState(PinState.LOW)
                }
            }
            gpio.shutdown()
            System.exit(0)
          }
      }
      //Thread.sleep(5000)
    }

  }

  implicit class PinStateOps(p: PinState){
    def reverse: PinState =
      p.getValue match {
        case 0 => PinState.HIGH
        case _ => PinState.LOW
      }
  }

}

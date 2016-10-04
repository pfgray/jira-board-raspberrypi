package net.paulgray.raspberry

/**
 * Created by nicole on 9/29/16.
 */
case class Digit(segments: List[Segment] = Nil)
object Digit {

  def apply(segments: Segment*) = new Digit(segments.toList)

  val blank = Digit()
  val zero  = Digit(A, B, C, D, E, F)
  val one   = Digit(B, C)
  val two   = Digit(A, B, G, E, D)
  val three = Digit(A, B, G, C, D)
  val four  = Digit(F, G, B, C)
  val five  = Digit(A, F, G, C, D)
  val six   = Digit(A, F, G, C, D, E)
  val seven = Digit(A, B, C)
  val eight = Digit(A, B, C, D, E, F, G)
  val nine  = Digit(A, B, C, F, G)
}

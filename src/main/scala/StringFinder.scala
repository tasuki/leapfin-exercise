import scala.annotation.tailrec

case class Result(elapsed: Int, bytes: Int, status: String)

object StringFinder {
  val needle: String = "Lp"

  def find(stream: Stream[Char]): Result =
    findNeedle(stream, 0, 0)

  // we assume:
  // - the stream is infinite
  // - each of the characters of the needle is unique
  @tailrec
  private def findNeedle(stream: Stream[Char], found: Int, count: Int): Result =
    if (found == needle.length)
      Result(0, count, "SUCCESS")
    else if (stream.head == needle(found))
      findNeedle(stream.tail, found + 1, count + 1)
    else if (stream.head == needle(0))
      findNeedle(stream.tail, 1, count + 1)
    else
      findNeedle(stream.tail, 0, count + 1)
}

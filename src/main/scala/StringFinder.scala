import scala.annotation.tailrec

trait Result {
  val elapsed: Long
}
case class ResultSuccess(elapsed: Long, bytes: Int) extends Result {
  override def toString: String =
    "%10d %10d %10s".format(elapsed, bytes, "SUCCESS")
}
case object ResultTimeout extends Result {
  val elapsed = 1000000L
  override def toString: String =
    "%10s %10s %10s".format("", "", "TIMEOUT")
}

object StringFinder {
  val needle: List[Char] = "Lpfn".toList
  val needlength: Int = needle.length

  def find(stream: Stream[Char]): ResultSuccess =
    findNeedle(stream, 0, 0, System.currentTimeMillis())

  // we assume:
  // - the stream is infinite
  // - each of the characters of the needle is unique
  @tailrec
  private def findNeedle(
    stream: Stream[Char],
    found: Int,
    count: Int,
    startedAt: Long
  ): ResultSuccess =
    if (found == needlength)
      ResultSuccess(System.currentTimeMillis() - startedAt, count)
    else if (stream.head == needle(found))
      findNeedle(stream.tail, found + 1, count + 1, startedAt)
    else if (stream.head == needle(0))
      findNeedle(stream.tail, 1, count + 1, startedAt)
    else
      findNeedle(stream.tail, 0, count + 1, startedAt)
}

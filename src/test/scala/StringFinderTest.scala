import org.scalatest.{ FlatSpec, Matchers }

class StringFinderTest extends FlatSpec with Matchers {
  it should "find needle at the beginning" in {
    val stream = "Lpfngivemeajob".toStream
    val result = StringFinder.find(stream)
    assert(result.status == "SUCCESS")
    assert(result.bytes == 4)
  }

  it should "find needle at the middle" in {
    val stream = "imightwanttoworkatLpfnifyougivemeajob".toStream
    val result = StringFinder.find(stream)
    assert(result.status == "SUCCESS")
    assert(result.bytes == 22)
  }

  it should "err on finite stream without needle" in {
    val stream = "thisonedoesnotcontainthekeyword".toStream
    an [NoSuchElementException] should be thrownBy StringFinder.find(stream)
  }
}

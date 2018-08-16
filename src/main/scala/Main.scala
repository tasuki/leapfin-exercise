import scala.concurrent.Await
import scala.concurrent.duration._

import scopt.OptionParser

object Main {
  case class Args(timeout: Int = 60)

  val parser: OptionParser[Args] =
    new OptionParser[Args]("leapfin") {
      opt[Int]('t', "timeout")
        .action((x, c) => c.copy(timeout = x))
        .text("timeout in seconds")
      help("help")
        .text("prints this usage text")
    }

  private def run(timeout: Int): Unit = {
    // allow longer before crashing with outer await
    val results = Await.result(
      Parallel.run(timeout),
      (timeout + 10).seconds
    )

    ResultPrinter.printResults(results)
  }

  def main(args: Array[String]): Unit = {
    parser.parse(args, Args()) match {
      case Some(config) => run(config.timeout)
      case None => Unit
    }
  }
}

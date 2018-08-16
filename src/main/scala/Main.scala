import scala.concurrent.Await
import scala.concurrent.duration._

import scopt.OptionParser

object Main {
  case class Args(timeout: Int = 60)

  private def printResults(results: Seq[Result]): Unit = {
    results.sortBy(_.elapsed).foreach(println)

    val overall = results
      .collect { case result: ResultSuccess => result }
      .reduce((a, b) => ResultSuccess(a.elapsed + b.elapsed, a.bytes + b.bytes))

    println("Bytes read per second: " + overall.bytes / (overall.elapsed / 1000))
  }

  private def run(timeout: Int): Unit = {
    val results = Await.result(
      Parallel.run(timeout),
      (timeout + 10).seconds
    )

    printResults(results)
  }

  def main(args: Array[String]): Unit = {
    val parser = new OptionParser[Args]("leapfin") {
      opt[Int]('t', "timeout")
        .action((x, c) => c.copy(timeout = x))
        .text("timeout in seconds")

      help("help")
        .text("prints this usage text")
    }

    parser.parse(args, Args()) match {
      case Some(config) => run(config.timeout)
      case None => Unit
    }
  }
}

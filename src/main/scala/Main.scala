import scala.concurrent.Await
import scala.concurrent.duration._

object Main {
  private def printResults(results: Seq[Result]): Unit = {
    results.sortBy(_.elapsed).foreach(println)

    val overall = results
      .collect { case result: ResultSuccess => result }
      .reduce((a, b) => ResultSuccess(a.elapsed + b.elapsed, a.bytes + b.bytes))

    println("Bytes read per second: " + overall.bytes / (overall.elapsed / 1000))
  }

  def main(args: Array[String]): Unit = {
    val timeout = 60

    val results = Await.result(Parallel.run(timeout), (timeout * 2).seconds)
    printResults(results)
  }
}

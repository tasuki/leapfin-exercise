import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService

object Main {
  val parallelTasks = 10

  implicit val scheduler: SchedulerService =
    Scheduler.computation(parallelism = parallelTasks)

  private def randomStream: Stream[Char] = Random.alphanumeric

  private def getTask(timeout: Int): Task[Result] =
    Task(StringFinder.find(randomStream))
      .timeoutTo(timeout.seconds, Task.now(ResultTimeout))

  private def printResults(results: Seq[Result]): Unit = {
    results.sortBy(_.elapsed).foreach(println)

    val overall = results
      .collect { case result: ResultSuccess => result }
      .reduce((a, b) => ResultSuccess(a.elapsed + b.elapsed, a.bytes + b.bytes))

    println("Bytes read per second: " + overall.bytes / (overall.elapsed / 1000))
  }

  private def run(timeout: Int): Unit = {
    val task = Task
      .gatherUnordered((1 to parallelTasks)
      .map(_ => getTask(timeout)))

    val results = Await.result(task.runAsync, (timeout * 2).seconds)
    printResults(results)
  }

  def main(args: Array[String]): Unit = {
    run(10)
  }
}

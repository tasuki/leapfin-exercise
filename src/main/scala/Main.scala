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

  def randomStream: Stream[Char] = Random.alphanumeric

  def getTask(timeout: Int): Task[Result] =
    Task(StringFinder.find(randomStream))
      .timeoutTo(timeout.seconds, Task.now(ResultTimeout))

  private def run(timeout: Int): Unit = {
    val task = Task
      .gatherUnordered((1 to parallelTasks)
      .map(_ => getTask(timeout)))

    Await.result(task.runAsync, (timeout * 2).seconds)
      .sortBy(_.elapsed)
      .foreach(println)
  }

  def main(args: Array[String]): Unit = {
    run(10)
  }
}

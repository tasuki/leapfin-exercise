import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random

import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService
import monix.execution.ExecutionModel.AlwaysAsyncExecution

object Parallel {
  val parallelTasks = 10

  implicit val scheduler: SchedulerService =
    Scheduler.computation(
      parallelism = parallelTasks,
      executionModel = AlwaysAsyncExecution
    )

  private def randomStream: Stream[Char] = Random.alphanumeric

  private def getTask(timeout: Int): Task[Result] =
    Task(StringFinder.find(randomStream))
      .timeoutTo(timeout.seconds, Task.now(ResultTimeout))

  def run(timeout: Int): Future[List[Result]] = {
    val task = Task
      .gatherUnordered((1 to parallelTasks)
      .map(_ => getTask(timeout)))

    task.runAsync
  }
}

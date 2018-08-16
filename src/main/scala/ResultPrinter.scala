object ResultPrinter {
  def printResults(results: Seq[Result]): Unit = {
    results.sortBy(_.elapsed).foreach(println)

    val overall = results
      .collect { case result: ResultSuccess => result }
      .reduce((a, b) => ResultSuccess(
        a.elapsed + b.elapsed,
        a.bytes + b.bytes
      ))

    val bytesPerSecond = 1000 * (overall.bytes / overall.elapsed)
    println("Bytes read per second: " + bytesPerSecond)
  }
}

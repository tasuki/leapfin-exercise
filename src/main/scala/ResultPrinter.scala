object ResultPrinter {
  def printResults(results: Seq[Result]): Unit = {
    results.sortBy(_.elapsed).foreach(println)

    val overall = results
      .collect { case result: ResultSuccess => result }
      .reduce((a, b) => ResultSuccess(a.elapsed + b.elapsed, a.bytes + b.bytes))

    println("Bytes read per second: " + overall.bytes / (overall.elapsed / 1000))
  }
}

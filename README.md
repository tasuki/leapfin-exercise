# Leapfin coding exercise

Run the application:

    java -jar leapfin.jar

Run the application with 10 second timeout:

    java -jar leapfin.jar --timeout 10

## Run with sbt

Install [sbt](https://www.scala-sbt.org/), then run:

    sbt "runMain Main"

To see help, run:

    sbt "runMain Main --help"

To set timeout to 10 seconds, run:

    sbt "runMain Main --timeout 10"

## Run tests

    sbt test

## Warts

1. Help message is not available under `-h` as per the instructions. I don't
   want to spend more time with the `scopt` library than necessary.
2. When running through sbt, the string finder continues running after the
   timeout. The workaround is running the `.jar` file directly. I'd never do
   that in production but hey it's just an exercise!

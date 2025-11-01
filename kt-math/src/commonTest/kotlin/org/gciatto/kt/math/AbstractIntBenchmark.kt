package org.gciatto.kt.math

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

expect val REPETITIONS: Int

expect val N: Int

abstract class AbstractIntBenchmark<T>(
    private val algos: IntAlgos<T>,
) {
    protected abstract fun bigInt(value: Int): T

    @ExperimentalTime
    private inline fun <R> chrono(f: () -> R): Pair<R, Duration> {
        val start = TimeSource.Monotonic.markNow()
        val result = f()
        return result to start.elapsedNow()
    }

    @ExperimentalTime
    private inline fun <R> benchmark(
        times: Int,
        f: () -> R,
    ): Duration {
        var cumulative = Duration.ZERO
        repeat(times) {
            cumulative += chrono(f).second
        }
        return cumulative / times
    }

    @ExperimentalTime
    open fun benchmarkSumsViaFibonacci() {
        val avgTime =
            benchmark(REPETITIONS) {
                algos.fibonacciBasedBenchmark(bigInt(N))
            }
        println("[Fibonacci] Average duration on $REPETITIONS repetitions: $avgTime")
    }

    @ExperimentalTime
    open fun benchmarkMultiplicationsViaFactorial() {
        val avgTime =
            benchmark(REPETITIONS) {
                algos.factorialBasedBenchmark(bigInt(N))
            }
        println("[Factorial] Average duration on $REPETITIONS repetitions: $avgTime")
    }
}

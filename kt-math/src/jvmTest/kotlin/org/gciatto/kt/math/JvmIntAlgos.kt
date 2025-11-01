package org.gciatto.kt.math

import java.math.BigInteger
import kotlin.test.assertEquals

object JvmIntAlgos : IntAlgos<BigInteger> {
    private fun bigIntRange(
        min: Int,
        maxIncl: BigInteger,
    ): Sequence<BigInteger> =
        generateSequence(BigInteger.valueOf(min.toLong())) { it + BigInteger.ONE }.takeWhile { it <= maxIncl }

    override fun fibonacci(n: BigInteger): BigInteger =
        when {
            n < BigInteger.ZERO -> throw IllegalArgumentException("Cannot compute the fibonacci($n)")
            n <= BigInteger.ONE -> BigInteger.ONE
            else -> {
                var prev = BigInteger.ONE
                var curr = BigInteger.ONE
                for (i in bigIntRange(2, n)) {
                    val next = curr + prev
                    prev = curr
                    curr = next
                }
                curr
            }
        }

    override fun factorial(n: BigInteger): BigInteger =
        when {
            n < BigInteger.ZERO -> throw IllegalArgumentException("Cannot compute the factorial($n)")
            else -> {
                var curr = BigInteger.ONE
                for (i in bigIntRange(2, n)) {
                    curr *= i
                }
                curr
            }
        }

    override fun fibonacciBasedBenchmark(max: BigInteger) {
        val sequence = bigIntRange(0, max).map(this::fibonacci).toList()
        for (i in sequence.indices
            .reversed()
            .drop(1)
            .takeWhile { it > 1 }) {
            assertEquals(sequence[i], sequence[i + 1] - sequence[i - 1])
        }
    }

    override fun factorialBasedBenchmark(max: BigInteger) {
        val sequence = bigIntRange(0, max).map(this::factorial).toList()
        for (i in sequence.indices.reversed().takeWhile { it > 0 }) {
            assertEquals(BigInteger.valueOf(i.toLong()), sequence[i] / sequence[i - 1])
        }
    }
}

package org.gciatto.kt.math

import java.math.BigInteger
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class JvmIntBenchmark : AbstractIntBenchmark<BigInteger>(JvmIntAlgos) {
    override fun bigInt(value: Int): BigInteger = BigInteger.valueOf(value.toLong())

    @Test
    @ExperimentalTime
    override fun benchmarkSumsViaFibonacci() {
        super.benchmarkSumsViaFibonacci()
    }

    @Test
    @ExperimentalTime
    override fun benchmarkMultiplicationsViaFactorial() {
        super.benchmarkMultiplicationsViaFactorial()
    }
}

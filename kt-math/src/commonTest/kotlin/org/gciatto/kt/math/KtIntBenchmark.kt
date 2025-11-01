package org.gciatto.kt.math

import kotlin.test.Test
import kotlin.time.ExperimentalTime

class KtIntBenchmark : AbstractIntBenchmark<BigInteger>(KtIntAlgos) {
    override fun bigInt(value: Int): BigInteger = BigInteger.of(value)

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

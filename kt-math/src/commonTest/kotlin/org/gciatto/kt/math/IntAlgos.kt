package org.gciatto.kt.math

interface IntAlgos<T> {
    fun fibonacci(n: T): T

    fun factorial(n: T): T

    fun fibonacciBasedBenchmark(max: T)

    fun factorialBasedBenchmark(max: T)
}

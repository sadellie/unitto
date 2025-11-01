package org.gciatto.kt.math

@Suppress("NON_EXPORTABLE_TYPE")
data class BigIntegerRange(override val start: BigInteger, override val endInclusive: BigInteger) :
  ClosedRange<BigInteger>, Iterable<BigInteger> {
  override fun iterator(): Iterator<BigInteger> =
    object : Iterator<BigInteger> {
      private var current = start

      override fun hasNext(): Boolean = current <= endInclusive

      override fun next(): BigInteger {
        val temp = current
        current += BigInteger.ONE
        return temp
      }
    }
}

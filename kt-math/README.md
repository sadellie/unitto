## kt-math wasmJs port
- Included to simplify build logic and quickly apply changes
- jvm target was kept for kmp and tests
- Based on: `9b109e624a188805ba22b1201cc08065ac35d4cc`

#### Applied changes

- Replaced JS target with WasmJS
  - removed `src/jsMain/kotlin/org/gciatto/kt/math/Factory.kt`
  - `logImpl` replaced with `println`
  - `castTo` replaced with type check
- `override fun divideAndRemainder(divisor: BigDecimal): Pair<CommonBigDecimal, CommonBigDecimal>`
  - added missing subtraction
- `internal fun StringBuilder.insertChar(index: Int, char: Char): StringBuilder`
  - replaced with built-in `insert` (original was not working correctly)
- `fun divide(divisor: BigDecimal, scale: Int, roundingMode: RoundingMode): BigDecimal`
  - added method to interface and mapped to private method in `CommonBigDecimal`
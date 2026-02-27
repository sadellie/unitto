/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025-2026 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.core.common

sealed interface Token {
  val symbol: String

  /** Token only for math calculator */
  sealed interface Math : Token

  /** Token only for programmer calculator */
  sealed interface Programmer : Token

  sealed interface Formatter : Token {
    companion object {
      fun from(symbol: String) =
        when (symbol) {
          Space.symbol -> Space
          Period.symbol -> Period
          Comma.symbol -> Comma
          else -> error("Unexpected formatter symbol: $symbol")
        }
    }
  }

  // Used only in formatter, don't use internally
  sealed interface DisplayOnly : Token

  sealed interface Digit : Token {
    companion object {
      val all by lazy {
        listOf(Digit1, Digit2, Digit3, Digit4, Digit5, Digit6, Digit7, Digit8, Digit9, Digit0)
      }
      val allSymbols by lazy { all.map(Token::symbol) }
    }
  }

  sealed interface Letter : Token

  sealed interface Operator : Token {

    enum class Associativity {
      LEFT,
      RIGHT,
    }

    /** High value for earlier processing */
    val precedence: Int
    val associativity: Associativity
    val isUnary: Boolean
  }

  sealed interface MathOperator : Operator, Math

  sealed interface ProgrammerOperator : Operator, Programmer

  sealed interface MathFunc : Func, Math

  sealed interface Func : Token {
    /**
     * Same [Func] but with an opening bracket. Use it only for input from button clicks and in text
     * field transformation for correct cursor positions.
     */
    sealed interface WithBracket : Func

    companion object {
      val allMathSymbolsWithBracket by lazy {
        listOf<Math>(
            ArSin.WithBracket,
            ArCos.WithBracket,
            ArTan.WithBracket,
            Sin.WithBracket,
            Cos.WithBracket,
            Tan.WithBracket,
            Log.WithBracket,
            Exp.WithBracket,
            Ln.WithBracket,
          )
          .sortedByDescending { it.symbol.length }
          .map { it.symbol }
      }
    }
  }

  sealed interface Const : Token

  data object Space : Formatter {
    override val symbol = " "
  }

  data object Period : Formatter {
    override val symbol = "."
  }

  data object Comma : Formatter {
    override val symbol = ","
  }

  data object Digit1 : Digit {
    override val symbol = "1"
  }

  data object Digit2 : Digit {
    override val symbol = "2"
  }

  data object Digit3 : Digit {
    override val symbol = "3"
  }

  data object Digit4 : Digit {
    override val symbol = "4"
  }

  data object Digit5 : Digit {
    override val symbol = "5"
  }

  data object Digit6 : Digit {
    override val symbol = "6"
  }

  data object Digit7 : Digit {
    override val symbol = "7"
  }

  data object Digit8 : Digit {
    override val symbol = "8"
  }

  data object Digit9 : Digit {
    override val symbol = "9"
  }

  data object Digit0 : Digit {
    override val symbol = "0"
  }

  data object Dot : Token {
    override val symbol = "."
  }

  data object LetterA : Letter {
    override val symbol = "A"
  }

  data object LetterB : Letter {
    override val symbol = "B"
  }

  data object LetterC : Letter {
    override val symbol = "C"
  }

  data object LetterD : Letter {
    override val symbol = "D"
  }

  data object LetterE : Letter {
    override val symbol = "E"
  }

  data object LetterF : Letter {
    override val symbol = "F"
  }

  data object Plus : Operator, Math, Programmer {
    override val symbol = "+"
    override val precedence = 0
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Minus : Operator, Math, Programmer {
    override val symbol = "−"
    override val precedence = 0
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object UnaryMinus : Operator, Math, Programmer {
    override val symbol = "−"
    // unary first
    override val precedence = Int.MAX_VALUE
    override val associativity = Operator.Associativity.RIGHT
    override val isUnary = true
  }

  data object Multiply : Operator, Math, Programmer {
    override val symbol = "×"
    override val precedence = 1
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Divide : Operator, Math, Programmer {
    override val symbol = "÷"
    override val precedence = 1
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Power : MathOperator {
    override val symbol = "^"
    override val precedence = 2
    override val associativity = Operator.Associativity.RIGHT
    override val isUnary = false
  }

  data object Factorial : MathOperator {
    override val symbol = "!"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = true
  }

  data object Modulo : MathOperator {
    override val symbol = "#"
    override val precedence = 3
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Percent : MathOperator {
    // not operator in AST, used only in tokenizer and replaced after input fixups
    override val symbol = "%"
    override val precedence = -1
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = true
  }

  data object Sqrt : MathOperator {
    override val symbol = "√"
    override val precedence = 3
    override val associativity = Operator.Associativity.RIGHT
    override val isUnary = true
  }

  data object Sin : MathFunc {
    override val symbol = "sin"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Sin.symbol + LeftBracket.symbol
    }
  }

  data object Cos : MathFunc {
    override val symbol = "cos"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Cos.symbol + LeftBracket.symbol
    }
  }

  data object Tan : MathFunc {
    override val symbol = "tan"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Tan.symbol + LeftBracket.symbol
    }
  }

  data object ArSin : MathFunc {
    override val symbol = "sin⁻¹"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = ArSin.symbol + LeftBracket.symbol
    }
  }

  data object ArCos : MathFunc {
    override val symbol = "cos⁻¹"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = ArCos.symbol + LeftBracket.symbol
    }
  }

  data object ArTan : MathFunc {
    override val symbol = "tan⁻¹"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = ArTan.symbol + LeftBracket.symbol
    }
  }

  data object Ln : MathFunc {
    override val symbol = "ln"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Ln.symbol + LeftBracket.symbol
    }
  }

  data object Log : MathFunc {
    override val symbol = "log"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Log.symbol + LeftBracket.symbol
    }
  }

  data object Exp : MathFunc {
    override val symbol = "exp"

    data object WithBracket : Func.WithBracket, Math {
      override val symbol = Exp.symbol + LeftBracket.symbol
    }
  }

  data object Pi : Const, Math {
    override val symbol = "π"
  }

  data object E : Const, Math {
    override val symbol = "e"
  }

  // TODO not display only, used in tokenizer
  data object EngineeringE : DisplayOnly, Math {
    override val symbol = "E"
  }

  data object Fraction : DisplayOnly {
    override val symbol = "⁄"
  }

  // Programmer only
  data object Or : ProgrammerOperator {
    override val symbol = "or"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object And : ProgrammerOperator {
    override val symbol = "and"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Not : ProgrammerOperator {
    override val symbol = "not"
    override val precedence = Int.MAX_VALUE
    override val associativity = Operator.Associativity.RIGHT
    override val isUnary = true
  }

  data object Nand : ProgrammerOperator {
    override val symbol = "nand"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Nor : ProgrammerOperator {
    override val symbol = "nor"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Xor : ProgrammerOperator {
    override val symbol = "xor"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Lsh : ProgrammerOperator {
    override val symbol = "lsh"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Rsh : ProgrammerOperator {
    override val symbol = "rsh"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data object Mod : ProgrammerOperator {
    override val symbol = "mod"
    override val precedence = 2
    override val associativity = Operator.Associativity.LEFT
    override val isUnary = false
  }

  data class Number(override val symbol: String) : Token, Math, Programmer

  data object LeftBracket : Token, Math, Programmer {
    override val symbol = "("
  }

  data object RightBracket : Token, Math, Programmer {
    override val symbol = ")"
  }

  companion object {
    val parseableMathTokens: List<Math> by lazy {
      listOf(
          Plus,
          Minus,
          Multiply,
          Divide,
          Power,
          Factorial,
          Modulo,
          Percent,
          Sqrt,
          ArSin,
          ArCos,
          ArTan,
          Sin,
          Cos,
          Tan,
          Log,
          Exp,
          Ln,
          Pi,
          E,
          EngineeringE,
          LeftBracket,
          RightBracket,
        )
        .sortedByDescending { it.symbol.length }
    }
    val parseableProgrammerTokens: List<Programmer> by lazy {
      listOf(
          Plus,
          Minus,
          Multiply,
          Divide,
          Or,
          And,
          Not,
          Nand,
          Nor,
          Xor,
          Lsh,
          Rsh,
          Mod,
          LeftBracket,
          RightBracket,
        )
        .sortedByDescending { it.symbol.length }
    }
    val digitsWithDotSymbols by lazy { (Digit.all + Dot).map(Token::symbol) }

    val sexyToUgly by lazy {
      mapOf(
        Minus.symbol to listOf("-", "–", "—", "—"),
        Divide.symbol to listOf("/"),
        Multiply.symbol to listOf("*", "•"),
        ArSin.symbol to listOf("arsin"),
        ArCos.symbol to listOf("arcos"),
        ArTan.symbol to listOf("actan"),
      )
    }
  }
}

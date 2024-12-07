package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.combinations
import endredeak.aoc2024.lib.utils.longs

fun main() {
    solve("Bridge Repair") {
        val eqsWithSolutions = mutableSetOf<String>()
        fun calculate(vararg ops: String, skip: Set<String>? = null) =
            (skip?.let { lines.minus(it) } ?: lines)
                .sumOf { l ->
                    l.longs().let { numbers ->
                        ops.toSet()
                            .combinations(numbers.size - 2)
                            .firstOrNull { c ->
                                numbers.drop(2)
                                    .zip(c)
                                    .fold(numbers[1]) { acc, (n, op) ->
                                        when (op) {
                                            "+" -> acc + n
                                            "*" -> acc * n
                                            "|" -> "$acc$n".toLong()
                                            else -> error("invalid operand")
                                        }
                                    } == numbers[0]
                            }
                            ?.let { numbers[0] }
                            ?.also { eqsWithSolutions.add(l) }
                            ?: 0
                    }
                }


        part1(2664460013123) { calculate("+", "*") }
        part2(426214131924213) { calculate("+", "*", "|", skip = eqsWithSolutions) }
    }
}
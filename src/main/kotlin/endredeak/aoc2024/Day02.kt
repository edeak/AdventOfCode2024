package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.allSubListsWithAdjacentRemoved
import endredeak.aoc2024.lib.utils.ints
import kotlin.math.absoluteValue

fun main() {
    solve("Red-Nosed Reports") {
        val input = lines
            .map { it.ints() }

        fun List<Int>.isValid() =
            this.windowed(2, 1)
                .let {
                    (it.all { (a, b) -> (a < b) } ||
                            it.all { (a, b) -> (a > b) }) &&
                            it.all { (a, b) -> (a - b).absoluteValue in (1..3) }
                }

        part1(660) { input.count { it.isValid() } }

        part2(689) {
            input.count { report ->
                report.allSubListsWithAdjacentRemoved().any { it.isValid() }
            }
        }
    }
}
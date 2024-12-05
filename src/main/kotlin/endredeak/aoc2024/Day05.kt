package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.ints
import endredeak.aoc2024.lib.utils.middle

fun main() {
    solve("Print Queue") {
        val (rules, queue) = text
            .split("\n\n")
            .let { it[0].split("\n") to it[1].split("\n") }
            .let { (f, s) -> f.map { it.ints() }.groupBy({ it[0] }) { it[1] } to s.map { it.ints() } }

        val comparator = Comparator<Int> { l, r -> rules[l]?.let { if (r in it) -1 else 1 } ?: 0 }

        part1(6612) {
            queue
                .filter { it == it.sortedWith(comparator) }
                .sumOf { it.middle() }
        }

        part2(4944) {
            queue
                .filter { it != it.sortedWith(comparator) }
                .map { it.sortedWith(comparator) }
                .sumOf { it.middle() }
        }
    }
}
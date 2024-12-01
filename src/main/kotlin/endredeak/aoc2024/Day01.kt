package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.ints
import kotlin.math.abs

fun main() {
    solve("Historian Hysteria") {
        val input = lines
            .map { l -> l.ints().let { it[0] to it[1] } }
            .unzip()
            .let { (l, r) -> l.sorted() to r.sorted() }

        part1(1873376) {
            input.let { (l, r) -> l.zip(r, Int::minus).sumOf(::abs) }
        }

        part2(18997088) {
            input.let { (left, right) -> left.sumOf { l -> right.count { l == it } * l } }
        }
    }
}
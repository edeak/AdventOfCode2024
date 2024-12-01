package endredeak.aoc2024

import kotlin.math.abs

fun main() {
    solve("Historian Hysteria") {
        val input = lines
            .map { l -> l.split("   ").let { it[0].toInt() to it[1].toInt()} }
            .let {
                val (left, right) = mutableListOf<Int>() to mutableListOf<Int>()

                it.forEach { (l, r) ->
                    left.add(l)
                    right.add(r)
                }

                left to right
            }

        part1(1873376) {
            val (left, right) = input

            left.sort()
            right.sort()

            left.indices.sumOf { i -> abs(left[i] - right[i]) }
        }

        part2(18997088) {
            val (left, right) = input

            left.sumOf { l ->
                right.count { l == it } * l
            }
        }
    }
}
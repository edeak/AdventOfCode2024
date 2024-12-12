package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.longs

fun main() {
    solve("Plutonian Pebbles") {
        val input = text

        fun Long.blink() =
            when {
                this == 0L -> listOf(1L)
                "$this".length % 2 == 0 -> "$this".let { listOf(it.take(it.length / 2).toLong(), it.drop(it.length / 2).toLong()) }
                else -> listOf(this * 2024L)
            }

        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        fun calc(stone: Long, i: Int): Long =
            if (i == 0) 1 else cache.getOrPut(stone to i) { stone.blink().sumOf { calc(it, i - 1) } }

        part1(189167) { input.longs().sumOf { calc(it, 25) } }

        part2(225253278506288) { input.longs().sumOf { calc(it, 75) } }
    }
}
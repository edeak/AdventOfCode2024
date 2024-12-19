package endredeak.aoc2024

fun main() {
    solve("Linen Layout") {
        val (patterns, designs) = text.split("\n\n").let { it[0].split(", ") to it[1].lines() }

        val mem = mutableMapOf<String, Long>()
        fun List<String>.ways(d: String): Long = when {
            d.isNotEmpty() -> mem.getOrPut(d) { sumOf { if (d.startsWith(it)) ways(d.substring(it.length)) else 0L } }
            else -> 1L
        }

        part1(327) { designs.count { patterns.ways(it) > 0 } }

        part2(772696486795255) { designs.sumOf { patterns.ways(it) } }
    }
}
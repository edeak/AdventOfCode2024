package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord
import endredeak.aoc2024.lib.utils.combinations

fun main() {
    solve("Resonant Collinearity") {
        val input = lines
            .flatMapIndexed { y, l -> l.mapIndexed { x, c -> Coord(x, y) to "$c" } }
            .associate { it }

        fun calc(generateNodes: (Coord, List<Coord>) -> List<Coord>): Int =
            input.toMutableMap().apply {
                filterValues { it != "." }
                    .entries
                    .groupBy({ it.value }) { it.key } // "freq -> [coord_1, coord_2, ...]}
                    .values
                    .map { c -> // generate all the diffs: Coord_1 -> [C(1,1), C(2,2), ...]
                        c.combinations(2)
                            .filter { it[0] != it[1] }
                            .groupBy({ it[0] }) { it[0].diff(it[1]) }
                    }
                    .flatMap { it.flatMap { (p, d) -> generateNodes(p, d) } }
                    .forEach { n -> if (input.containsKey(n)) { this[n] = "#"} }
            }.count { it.value.contains("#") }

        part1(269) {
            calc { p, diffs -> diffs.map { p + it * 2 } }
        }

        part2(949) {
            calc { p, diffs ->
                diffs.flatMap {
                    mutableListOf(p).apply {
                        var curr = p
                        while (curr.x in (0..lines[0].lastIndex) && curr.y in (0..lines.lastIndex)) {
                            curr += it
                            add(curr)
                        }
                    }
                }
            }
        }
    }
}
package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord
import endredeak.aoc2024.lib.utils.toGrid

fun main() {
    solve("Ceres Search") {
        val input = lines.toGrid { "$it" }

        part1(2603) {
            input
                .filterValues { it == "X" }
                .keys
                .flatMap { x ->
                    x
                        .dirs(true)
                        .map { d ->
                            (1..3)
                                .runningFold(x) { acc, _ -> acc + d }
                                .mapNotNull { input[it] }
                                .joinToString("")
                        }
                }
                .count { it == "XMAS" }
        }

        part2(1965) {
            input
                .filterValues { it == "A" }
                .keys
                .count { a ->
                    listOf(
                        listOf(Coord(-1, -1), Coord(0, 0), Coord(1, 1)),
                        listOf(Coord(-1, 1), Coord(0, 0), Coord(1, -1))
                    )
                        .all { diagonal ->
                            diagonal
                                .map { d -> a + d }
                                .mapNotNull { input[it] }
                                .joinToString("")
                                .let { it == "MAS" || it == "SAM" }
                        }
                }
        }
    }
}
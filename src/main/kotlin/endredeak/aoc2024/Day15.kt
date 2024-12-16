package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.*

fun main() {
    solve("") {
        val input by lazy { text.split("\n\n") }

        fun Char.asMove() = when (this) {
            '>' -> Coord(1, 0)
            '<' -> Coord(-1, 0)
            '^' -> Coord(0, -1)
            'v' -> Coord(0, 1)
            else -> error("invalid character in moves")
        }

        fun List<String>.toMoves() = flatMap { it.map(Char::asMove) }

        //Move Logic
        fun MutableMap<Coord, Char>.move(c: Coord, m: Coord): Boolean {
            val char = getValue(c)
            put(c, '.')
            put(c + m, char)
            return true
        }

        fun Grid<Char>.nextSet(set: Set<Coord>, m: Coord): Set<Coord>? = set
            .flatMapTo(mutableSetOf()) { c ->
                val n = c + m
                when (this[n]!!) {
                    '#' -> return null
                    '.' -> emptySet()
                    '[' -> if (m in setOf(Coord(0, -1), Coord(0, 1))) setOf(
                        n,
                        n + Coord(1, 0)
                    ) else setOf(n)

                    ']' -> if (m in setOf(Coord(0, -1), Coord(0, 1))) setOf(
                        n,
                        n + Coord(-1, 0)
                    ) else setOf(n)

                    else -> setOf(n)
                }
            }

        fun Grid<Char>.tryMove(dir: Coord, coords: Set<Coord>): Boolean =
            when (val nextCoords = nextSet(coords, dir)) {
                null -> false
                emptySet<Coord>() -> coords.all { move(it, dir) }
                else -> tryMove(dir, nextCoords) && coords.all { move(it, dir) }
            }

        // Main algorithm
        fun Grid<Char>.solve(char: Char, moves: List<Coord>): Int {
            var curr = entries.first { it.value == '@' }.key
            moves.forEach { m ->
                if (tryMove(m, setOf(curr))) {
                    curr += m
                }
            }
            return filter { it.value == char }.map { it.key }.sumOf { it.y * 100 + it.x }
        }

        part1(1456590) {
            input
                .let { (grid, line) -> grid.split("\n").toGrid { it } to line.split("\n").toMoves() }
                .let { (g, o) -> g.solve('O', o) }
        }

        part2(1489116) {
            input
                .let { (grid, moves) ->
                    grid.replace("#", "##")
                        .replace(".", "..")
                        .replace("O", "[]")
                        .replace("@", "@.").split("\n").toGrid { it } to moves.split("\n").toMoves()
                }
                .let { (g, o) -> g.solve('[', o) }
        }
    }
}
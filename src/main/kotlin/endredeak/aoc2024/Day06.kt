package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord

fun main() {
    solve("Guard Gallivant") {
        data class Path(val points: Set<Coord>, val isLoop :Boolean)

        val map = lines
            .flatMapIndexed { y, l -> l.mapIndexed { x, c -> Coord(x,y) to c } }
            .associate { it }

        val start = map.filter { it.value == '^' }.entries.first().key

        fun Map<Coord, Char>.getPath(): Path {
            val points = mutableSetOf(start)
            var curr = start
            var dir = Coord(0 ,-1)
            val visited = mutableSetOf<Pair<Coord, Coord>>()

            while(curr + dir in this) {
                val state = curr to dir

                if (state in visited) {
                    return Path(points, true)
                }
                visited.add(state)

                if (this[curr + dir] == '#') {
                    dir = dir.rotateRight()
                    continue
                }
                curr += dir
                points.add(curr)
            }

            return Path(points, false)
        }

        part1(5305) {
            map.getPath().points.size
        }

        part2(2143) {
            map.getPath()
                .points
                .filter { it != start }
                .count { p ->
                    map.entries
                        .associate { it.key to it.value }
                        .toMutableMap()
                        .apply { this[p] = '#' }.getPath().isLoop
                }
        }
    }
}
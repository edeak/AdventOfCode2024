package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.*
import java.util.*

fun main() {
    solve("") {
        val input = lines

        fun toGrid(lines: List<String>, max: Int) =
            lines.map { it.ints().let { (x, y) -> x to y } }
                .let { bytes ->
                    (0..max)
                        .let { d ->
                            d.flatMap { y -> d.map { x -> Coord(x, y) to if ((x to y) in bytes) '#' else '.' } }
                        }
                }
                .associate { it } as Grid<Char>

        fun dijkstra(start: Coord, end: Coord, grid: Grid<Char>): Int {
            val q = PriorityQueue<Pair<Coord, Int>>(compareBy { it.second })
            val visited = mutableSetOf<Coord>()

            q.add(start to 0)

            while (q.isNotEmpty()) {
                q.poll().also { visited.add(it.first) }
                    .let { (curr, cost) ->
                        if (curr == end) return cost

                        q += curr.neighbours()
                            .map { it to (cost + 1) }
                            .filter { it.first !in visited && grid[it.first] != '#' && (it.first in grid.keys) && it !in q }
                    }
            }
            return -1
        }

        fun calc(max: Int, size: Int) = dijkstra(Coord(0, 0), Coord(max, max), toGrid(input.take(size), max))

        part1(370) { calc(70, 1024) }

        part2("65,6") {
            var i = 1024
            do { i++ } while(calc(70, i) != -1)
            input[i-1]
        }
    }
}
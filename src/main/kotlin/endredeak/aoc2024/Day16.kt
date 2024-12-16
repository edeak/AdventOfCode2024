package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord
import endredeak.aoc2024.lib.utils.Grid
import endredeak.aoc2024.lib.utils.single
import endredeak.aoc2024.lib.utils.toGrid
import java.util.*

typealias State = Pair<Coord, Coord>

fun main() {
    data class Path(val state: State, val cost: Int, val path: List<State>)

    fun dijkstra(start: State, end: Coord, grid: Grid<Char>): Pair<Set<Coord>, Map<Path, Int>> {
        val distances = mutableMapOf<Path, Int>().withDefault { Int.MAX_VALUE }
        val q = PriorityQueue<Path>(compareBy { it.cost })
        val visited = mutableSetOf<State>()

        val s = Path(start, 0, listOf(start))
        q.add(s)
        distances[s] = 0

        val bestPoints = mutableSetOf(end)
        var minCost = Int.MAX_VALUE

        while (q.isNotEmpty()) {
            val curr = q.poll()
            val (state, cost, path) = curr
            visited.add(state)
            distances[curr] = cost

            if (state.first == end) {
                if (cost <= minCost) {
                    minCost = cost
                    bestPoints += path.map { it.first }
                }
            }

            listOf(
                (state.first to state.second.rotateRight()) to (cost + 1000),
                (state.first to state.second.rotateLeft()) to (cost + 1000),
                ((state.first + state.second) to state.second) to (cost + 1)
            ).filter { it.first !in visited && grid[it.first.first] != '#' }
                .map { (s, c) -> Path(s, c, path + state) }
                .let { adj ->
                    q.addAll(adj)
                    adj.forEach { p ->
                        val totalDist = cost + p.cost
                        if (totalDist < distances.getValue(p)) {
                            distances[p] = totalDist
                        }
                    }
                }
        }
        return bestPoints to distances
    }

    solve("Reindeer Maze") {
        val grid = lines.toGrid { it }
        val start = grid.single { it == 'S' }
        val end = grid.single { it == 'E' }
        val (bestPoints, dists) = dijkstra(start to Coord(1, 0), end, grid)

        part1(79404) {
            dists.filter { d -> d.key.state.first == end }.minOf { it.value }
        }

        part2(451) {
            bestPoints.size
        }
    }
}
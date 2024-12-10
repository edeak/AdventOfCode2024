package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord

fun main() {
    solve("Hoof It") {
        val input = lines
            .flatMapIndexed { y, l -> l.mapIndexed { x, c -> Coord(x,y) to c} }
            .filter { it.second != '.' }
            .map { it.first to it.second.digitToInt() }
            .associate { it }

        fun Coord.neighbours() =
            input
                .filterKeys { k -> k in this.NEWS().map { n -> n + this} }
                .filterValues { it == input[this]!! + 1 }
                .keys

        fun bfs(start: Coord): Pair<Int, Int> {
            val discovered = mutableSetOf(start)
            val q = ArrayDeque<Pair<Coord, List<Coord>>>()
            q.add(start to listOf(start))
            var count = 0
            while (q.isNotEmpty()) {
                val (curr, path) = q.removeFirst()

                if (input[curr] == 9) { count++ }

                curr.neighbours()
                    .forEach {
                        if (path.none { d -> d == it }) { q.add(it to path.plus(listOf(it))) }
                        if (discovered.none { d -> d == it }) { discovered.add(it) }
                    }
            }

            return discovered.mapNotNull { input[it] }.count { it == 9 } to count
        }

        val bfsResult = input.filterValues { it == 0 }
            .keys
            .map { bfs(it) }
            .unzip()
            .let { it.first.sum() to it.second.sum() }

        part1(629) { bfsResult.first }
        part2(1242) { bfsResult.second }
    }
}
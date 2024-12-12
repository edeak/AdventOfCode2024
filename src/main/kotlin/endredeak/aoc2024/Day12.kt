package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord
import endredeak.aoc2024.lib.utils.toGrid

typealias Region = Set<Coord>
fun Region.price(method: Region.() -> Set<Pair<Coord, Coord>>) = this.size * method(this).size
fun Region.perimeter(): Set<Pair<Coord, Coord>> = flatMap { c -> c.dirs().filter { c + it !in this }.map { c to it } }.toSet()
fun Region.sides(): Set<Pair<Coord, Coord>> = with(perimeter()) { filterNot { (pos, dir) -> (pos + dir.rotateRight() to dir) in this } }.toSet()

fun main() {
    solve("Garden Groups") {
        val input = lines.toGrid { it }

        val regions = buildList<Pair<Char, Region>> {
            fun dfs(start: Coord): Set<Coord> {
                val discovered = mutableSetOf<Coord>()
                val s = ArrayDeque<Coord>()
                s.add(start)

                while (s.isNotEmpty()) {
                    val v = s.removeLast()
                    if (!discovered.contains(v)) {
                        discovered.add(v)
                        v.neighbours()
                            .filter { input[it] != null && input[it] == input[v] }
                            .forEach { s.addFirst(it) }
                    }
                }

                return discovered
            }

            input.keys.forEach { c ->
                val k = input[c]!!
                if (none { c in it.second }) {
                    val coords = dfs(c)
                    add(k to coords)
                }
            }
        }.unzip().second

        part1(1361494) { regions.sumOf { it.price(Region::perimeter) } }

        part2(830516) { regions.sumOf { it.price(Region::sides) } }
    }
}
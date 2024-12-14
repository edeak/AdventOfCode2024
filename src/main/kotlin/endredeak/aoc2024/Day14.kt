package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.Coord
import endredeak.aoc2024.lib.utils.ints
import endredeak.aoc2024.lib.utils.productOf

fun main() {
    solve("Restroom Redoubt") {
        val input = lines

        data class Robot(var pos: Coord, val velocity: Coord) {
            fun move(max: Coord, times: Int = 1) { pos = (pos + velocity*times).mod(max) }
        }

        val lim = Coord(101, 103)
        fun robots(): List<Robot> =
            input
                .map { it.ints().chunked(2) }
                .map { (p, v) -> Robot(Coord(p[0], p[1]), Coord(v[0], v[1])) }

        fun List<Robot>.printTiles() {
            (0..<lim.y).forEach { y ->
                (0..<lim.x).forEach { x ->
                    print(if(count { r -> r.pos == Coord(x, y) } == 0) "." else "#" )
                }
                println()
            }
        }

        part1(224357412) {
            with(robots()) {
                forEach { it.move(lim, 100) }

                lim.let { (w, h) ->
                    listOf(
                        filter { r -> r.pos.x < w / 2 && r.pos.y < h / 2 },
                        filter { r -> r.pos.x > w / 2 && r.pos.y < h / 2 },
                        filter { r -> r.pos.x < w / 2 && r.pos.y > h / 2 },
                        filter { r -> r.pos.x > w / 2 && r.pos.y > h / 2 }
                    )
                        .productOf { it.size.toLong() }
                }
            }
        }

        part2(7083) {
            with(robots()) {
                var i = 0
                while (size != distinctBy { it.pos }.size) {
                    forEach { it.move(lim) }
                    i++
                }
                i.also { printTiles() }
            }
        }
    }
}

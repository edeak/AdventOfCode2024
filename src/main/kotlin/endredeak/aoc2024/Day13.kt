package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.doubles
import endredeak.aoc2024.lib.utils.transpose
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    solve("Claw Contraption") {
        fun Double.isInt() = floor(this) == ceil(this)

        val input = lines
            .filter { it.isNotEmpty() }
            .chunked(3)
            .flatMap { eq -> eq.map { it.doubles() }.transpose() }

        // Cramer rule
        fun solveSystem(first: List<Double>, second: List<Double>) =
            first.let { (a1, b1, c1) ->
                second.let { (a2, b2, c2) ->
                    (a1 * b2 - b1 * a2)
                        .takeIf { it != 0.0 }
                        ?.let { det -> ((c1 * b2 - c2 * b1) / det) to ((a1 * c2 - a2 * c1) / det) }
                }
            }

        fun calc(max: Int? = null, mod: Double? = null) =
            run {
                mod?.let { m ->
                    input
                        .map { it.toMutableList().apply {
                            this[2] += m
                        }
                    }
                }
                    ?: input
            }
                .chunked(2)
                .mapNotNull { (f, s) -> solveSystem(f,s) }
                .filter { (a, b) -> a.isInt() && b.isInt() }
                .map { (a, b) -> a.toLong() to b.toLong() }
                .let { max?.let { m -> it.filter { (a, b) -> a + b <= m } } ?: it }
                .sumOf { (a, b) -> 3 * a + b }

        part1(35255) { calc(max = 200) }
        part2(87582154060429) { calc(mod = 10000000000000.0) }
    }
}
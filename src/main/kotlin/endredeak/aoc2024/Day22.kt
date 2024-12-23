package endredeak.aoc2024

fun main() {
    fun Long.n() =
        ((this shl 6 xor this) and 16777215).let { a ->
            ((a shr 5 xor a) and 16777215).let { b ->
                b shl 11 xor b and 16777215
            }
        }

    solve("Monkey Market") {
        val input = lines.map { it.toLong() }

        part1(19847565303) {
            input.sumOf { generateSequence(it, Long::n).elementAt(2000) }
        }

        part2(2250) {
            input
                .asSequence()
                .withIndex()
                .flatMap { (i, s) -> generateSequence(s, Long::n).take(2001).map { it % 10 }.windowed(5).map { i to it } }
                .groupingBy { it.second.zipWithNext(Long::minus) }
                .aggregate { _, acc: MutableMap<Int, Long>?, (i, l), _ -> (acc ?: mutableMapOf()).apply { getOrPut(i, l::last) } }
                .maxOf { it.value.values.sum() }
        }
    }
}
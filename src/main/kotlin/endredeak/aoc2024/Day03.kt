package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.longs

fun main() {
    solve("Mull It Over") {
        val input = text
        val regex = Regex("""(mul\(\d+,\d+\))|(do\(\))|(don't\(\))""")
        fun String.calc() = this.longs().zipWithNext().first().let { (a, b) -> a * b }

        part1(161289189) {
            regex.findAll(input)
                .mapNotNull { g -> g.value.takeIf { it.contains("mul") } }
                .sumOf(String::calc)
        }

        part2(83595109) {
            regex
                .findAll(input)
                .map { it.value }
                .fold(0L to true) { acc, next ->
                    when (next) {
                        "don't()" -> acc.first to false
                        "do()" -> acc.first to true
                        else ->
                            (if (acc.second) {
                                next.longs().zipWithNext().first().let { (a, b) -> a * b }
                            } else
                                acc.first) to acc.second
                    }
                }
                .first
        }
    }
}
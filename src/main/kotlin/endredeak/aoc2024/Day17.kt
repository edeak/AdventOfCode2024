package endredeak.aoc2024

import endredeak.aoc2024.lib.utils.longs

fun main() {
    solve("Chronospatial Computer") {
        data class Computer(var a: Long, var b: Long, var c: Long, var iptr: Int = 0) {
            private val output: MutableList<Long> = mutableListOf()
            private fun combo(code: Long) = when (code) {
                in (0..3L) -> code
                4L -> a
                5L -> b
                6L -> c
                else -> error("illegal operand $c")
            }

            fun run(program: List<Long>): List<Long> {
                while (true) {
                    if (iptr >= program.lastIndex) break
                    val op = program[iptr + 1]
                    when (program[iptr]) {
                        0L -> a = a shr combo(op).toInt()
                        1L -> b = b xor op
                        2L -> b = combo(op) % 8
                        3L -> if (a != 0L) iptr = op.toInt() - 2
                        4L -> b = b xor c
                        5L -> output += (combo(op) % 8L)
                        6L -> b = a shr combo(op).toInt()
                        7L -> c = a shr combo(op).toInt()
                    }

                    iptr += 2
                }

                return output
            }
        }

        val (computer, program) = text.split("\n\n")
            .let { (r, p) -> r.split("\n").flatMap { it.longs() } to p.longs() }
            .let { (r, p) ->
                r.let { (a, b, c) -> Computer(a, b, c) to p }
            }

        part1("6,0,6,3,0,2,3,1,6") {
            computer.run(program)
        }

        fun findA(program: List<Long>, target: List<Long>): Long {
            var aStart = if (target.size == 1) 0 else 8 * findA(program, target.subList(1, target.size))
            while (Computer(aStart, 0, 0).run(program) != target) aStart++
            return aStart
        }

        part2(236539226447469) {
            findA(program, program)
        }
    }
}
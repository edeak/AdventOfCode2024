package endredeak.aoc2024

fun main() {
    solve("Disk Fragmenter") {
        data class Block(val start: Int, val end: Int) {
            fun fits(other: Block) = (this.end - this.start) <= (other.end - other.start)
            fun size() = this.end - this.start
        }

        val input = {
            val disk = mutableListOf<Int>()
            val fileMap = mutableListOf<Block>()
            val freeMap = mutableListOf<Block>()

            var id = 0
            var p = 0
            text
                .map { it.digitToInt() }
                .forEachIndexed { i, size ->
                    (p to p + size)
                        .also { p += size }
                        .let { r -> (if (i % 2 == 0) fileMap else freeMap).add(Block(r.first, r.second)) }

                    val value = if (i % 2 == 0) id.also { id++ } else -1
                    repeat(size) { disk.add(value) }
                }

            Triple(disk, fileMap, freeMap)
        }

        fun List<Int>.checksum(): Long = indices.filter { this[it] >= 0 }.sumOf { this[it].toLong() * it }

        part1(6366665108136) {
            input().first.let { disk ->
                while (!disk.indices.none { it != disk.lastIndex && disk[it] == -1 && disk[it + 1] != -1 }) {
                    disk.indexOfLast { it != -1 }.let { curr ->
                        disk[disk.indexOfFirst { it == -1 }] = disk[curr]
                        disk[curr] = -1
                    }
                }
                disk.checksum()
            }
        }

        part2(6398065450842) {
            input().let { (disk, fileMap, freeMap) ->
                while (fileMap.isNotEmpty()) {
                    (fileMap.lastIndex to fileMap.removeLast()).let { (id, pos) ->
                        freeMap.indexOfFirst { pos.fits(it) && it.start < pos.start }
                            .takeIf { it >= 0 }
                            ?.let { it to freeMap[it] }
                            ?.let { (idx, t) ->
                                (t.start..<(t.start + pos.size())).forEach { disk[it] = id }
                                (pos.start..<pos.end).forEach { disk[it] = -1 }
                                freeMap[idx] = Block(t.start + pos.size(), t.end)
                            }
                    }
                }
                disk.checksum()
            }
        }
    }
}
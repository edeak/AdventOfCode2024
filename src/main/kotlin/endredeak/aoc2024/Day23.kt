package endredeak.aoc2024

fun main() {
    solve("LAN Party") {
        val input = lines.flatMap { it.split("-").let { (l, r) -> listOf(l to r, r to l) } }.let { pairs ->
            val nodes = mutableMapOf<String, MutableSet<String>>()

            pairs.forEach { (l, r) ->
                if (nodes.containsKey(l)) nodes[l]!!.add(r) else nodes[l] = mutableSetOf(r)
                if (nodes.containsKey(r)) nodes[r]!!.add(l) else nodes[r] = mutableSetOf(l)
            }

            nodes
        }

        part1(1314) {
            input
                .flatMap { (k, vset) ->
                    vset.toList().let { v ->
                        v.indices.flatMap { i ->
                            (i + 1..v.lastIndex).mapNotNull { j ->
                                if (v[i] in input.getValue(v[j])) setOf(k, v[i], v[j]) else null
                            }
                        }
                    }
                }
                .distinct()
                .count { r -> r.any { it.startsWith("t") } }
        }

        part2("bg,bu,ce,ga,hw,jw,nf,nt,ox,tj,uu,vk,wp") {
            input.map { (k, v) ->
                var intersect = v + k
                v.forEach { v1 ->
                    (intersect intersect (input.getValue(v1) + v1)).let { next ->
                        if (next.size > 2) intersect = next
                    }
                }
                intersect
            }
                .maxBy { it.size }
                .sorted()
                .joinToString(",")
        }
    }
}
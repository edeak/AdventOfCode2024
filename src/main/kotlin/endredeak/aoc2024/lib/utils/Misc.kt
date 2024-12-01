@file:Suppress("unused")
package endredeak.aoc2024.lib.utils

fun String.ints() = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()
fun String.longs() = Regex("""-?\d+""").findAll(this).map { it.value.toLong() }.toList()
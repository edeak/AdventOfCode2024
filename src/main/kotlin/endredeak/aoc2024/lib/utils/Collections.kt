@file:Suppress("unused")

package endredeak.aoc2024.lib.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun <T> Iterable<Iterable<T>>.print(separator: String = " ") =
    println(this.joinToString("\n") { it.joinToString(separator) })

fun <T> List<List<T>>.flipVertical() = this.reversed()
fun <T> List<List<T>>.rotateLeft(): List<List<T>> = this.transpose().reversed()
fun <T> List<List<T>>.rotateRight(): List<List<T>> = this.reversed().transpose()
fun <T> List<List<T>>.flipHorizontal(): List<List<T>> = this.map { it.reversed() }

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val ret = mutableListOf<MutableList<T>>()

    this.first().indices.forEach { col ->
        ret.add(this.map { row -> row[col] }.toMutableList())
    }

    return ret
}

fun <T, K> MutableMap<T, K>.insertOrUpdate(key: T, insertValue: K, updateFunction: (K) -> K) {
    this[key]
        ?.let { this[key] = updateFunction.invoke(it) }
        ?: run { this[key] = insertValue }
}

fun <K, V> Iterable<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap().toMutableMap()

fun <T> cartesianProduct(vararg lists: List<T>): List<List<T>> =
    lists
        .fold(listOf(listOf())) { acc, set ->
            acc.flatMap { list -> set.map { element -> list + element } }
        }

fun Collection<Number>.product(): Long = this.fold(1L) { acc, i -> acc * i.toLong() }
fun <T> Collection<T>.productOf(selector: (T) -> Long): Long = this.fold(1L) { acc, i -> acc * selector(i) }

fun <A, B> List<A>.pMap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async(Dispatchers.Default) { f(it) } }.awaitAll()
}

fun <T> List<T>.allSubListsWithAdjacentRemoved(size: Int = 1): List<List<T>> =
    this.indices
        .reversed()
        .windowed(size)
        .map { ix ->
            this.toMutableList()
                .apply {
                    ix.forEach { i ->
                        this@apply.removeAt(i)
                    }
                }
        }

fun List<String>.toGrid(): Map<Coord, String> =
    this.flatMapIndexed { y, line ->
        line.mapIndexed { x, c -> Coord(x, y) to "$c" }
    }.associate { it.first to it.second }
package endredeak.aoc2024.lib.utils

fun gcd(x: Long, y: Long): Long {
    var a = x
    var b = y
    while (b != 0L) a = b.also { b = a.mod(b) }
    return a
}

fun lcm(x: Long, y: Long): Long = x / gcd(x, y) * y
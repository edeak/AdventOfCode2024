package endredeak.aoc2024.lib.utils

data class Coord(val x: Int, val y: Int) : Comparable<Coord> {
    override fun compareTo(other: Coord): Int =
        this.x.compareTo(other.x) + this.y.compareTo(other.y)

    fun allNeighbours() =
        listOf(
            Coord(-1, -1),
            Coord(0, -1),
            Coord(1, -1),
            Coord(-1, 0),
            Coord(1, 0),
            Coord(-1, 1),
            Coord(0, 1),
            Coord(1, 1)
        )

    fun rotateRight() = Coord(-y, x)

    operator fun plus(other: Coord): Coord = Coord(this.x + other.x, this.y + other.y)
}
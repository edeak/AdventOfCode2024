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

    fun NEWS() = listOf(
        Coord(1, 0),
        Coord(0, 1),
        Coord(-1,0),
        Coord(0, -1))

    fun rotateRight() = Coord(-y, x)

    operator fun plus(other: Coord): Coord = Coord(this.x + other.x, this.y + other.y)
    fun diff(other: Coord) = Coord(other.x - x, other.y - y)
    operator fun minus(other: Coord): Coord = Coord(this.x - other.x, this.y - other.y)
    operator fun times(i: Int): Coord = Coord(x * i, y * i)
}
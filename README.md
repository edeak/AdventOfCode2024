[![License: WTFPL](https://img.shields.io/badge/License-WTFPL-brightgreen.svg)](http://www.wtfpl.net/about/)

My solutions for  challenges in Kotlin.

# AdventOfCode2024
Solutions for [AdventOfCode 2024](https://adventofcode.com/2024) in Kotlin

# Creating all the files:
Create input files:
```shell
for i in {1..24}; do touch "$(printf "Day%02d.txt" $i)"; done
```

Create solution files (assuming there is Day01.kt available):
```shell
for i in {2..25}; do cp Day01.kt "$(printf "Day%02d.kt" $i)"; done
```

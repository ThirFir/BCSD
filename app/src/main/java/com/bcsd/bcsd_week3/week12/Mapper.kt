package com.bcsd.bcsd_week3.week12

fun Word.toWordEntity(): WordEntity {
    return WordEntity(
        name = this.name,
        meaning = this.meaning,
        createdAt = System.currentTimeMillis()
    )
}

fun WordEntity.toWord(): Word {
    return Word(
        name = this.name,
        meaning = this.meaning
    )
}
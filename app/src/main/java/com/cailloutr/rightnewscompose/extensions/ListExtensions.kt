package com.cailloutr.rightnewscompose.extensions

import com.cailloutr.rightnewscompose.model.Section
import com.cailloutr.rightnewscompose.model.SectionsByIndex

fun List<Section>.toSectionsByIndex(): List<SectionsByIndex> {
    val initials = this.map {
        it.title[0]
    }

    return mutableListOf<SectionsByIndex>().also {
        initials.toSet().forEach { char ->
            it.add(
                SectionsByIndex(
                    index = char.toString(),
                    list = this.filter {
                        it.title.startsWith(char, true)
                    }
                )
            )
        }
    }
}

fun List<Any>.getNumberOfRows(): Int {
    return when (this.size) {
        in 1..4 -> {
            1
        }

        in 5..8 -> {
            2
        }

        else -> {
            3
        }
    }
}
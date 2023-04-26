package com.cailloutr.rightnewscompose.data.remote.responses.sections

import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.model.Section
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SectionsResult(
    val id: String,
    val webTitle: String,

    @SerialName("webUrl")
    val webURL: String,

    @SerialName("apiUrl")
    val apiURL: String,

    val editions: List<SectionsEdition>,
)

fun SectionsResult.toDefaultSection(): Section {
    val edition = editions.first {
        it.code == Code.Default.value
    }

    return Section(
        id = edition.id,
        title = edition.webTitle,
        webUrl = edition.webURL,
        apiUrl = edition.apiURL,
        code = edition.code
    )
}

fun SectionsResult.toRoomSections(): RoomSection {
    val edition = editions.first {
        it.code == Code.Default.value
    }

    return RoomSection(
        id = edition.id,
        title = edition.webTitle,
        webUrl = edition.webURL,
        apiUrl = edition.apiURL,
        code = edition.code
    )
}
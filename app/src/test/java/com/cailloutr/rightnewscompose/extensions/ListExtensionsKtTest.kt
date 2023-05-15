package com.cailloutr.rightnewscompose.extensions


import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.data.local.roommodel.toSection
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.model.Section
import com.cailloutr.rightnewscompose.model.SectionsByIndex
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ListExtensionsKtTest {

    @Test
    fun testToSectionsByIndex_returnsCorrectSectionsByIndex() {
        val sections = TestsConstants.fakeResponseSectionRoot.response.results.map {
            it.toRoomSections().toSection()
        }

        val expectedOutput = listOf<SectionsByIndex>(
            SectionsByIndex(
                index = "T",
                list = listOf<Section>(
                    TestsConstants.fakeResponseSectionRoot.response.results[0].toRoomSections()
                        .toSection()
                )
            ),
            SectionsByIndex(
                index = "G",
                list = listOf<Section>(
                    TestsConstants.fakeResponseSectionRoot.response.results[1].toRoomSections()
                        .toSection()
                )
            ),
            SectionsByIndex(
                index = "B",
                list = listOf<Section>(
                    TestsConstants.fakeResponseSectionRoot.response.results[2].toRoomSections()
                        .toSection()
                )
            ),
        )

        val output = sections.toSectionsByIndex()

        for (index in sections.indices) {
            assertThat(output[index]).isEqualTo(expectedOutput[index])
        }

    }

    @Test
    fun testGetNumberOfRows_listSize1_returns1() {
        // Arrange
        val inputList = listOf("item1")
        val expectedOutput = 1

        // Act
        val actualOutput = inputList.getNumberOfRows()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testGetNumberOfRows_listSize4_returns1() {
        // Arrange
        val inputList = listOf("item1", "item2", "item3", "item4")
        val expectedOutput = 1

        // Act
        val actualOutput = inputList.getNumberOfRows()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testGetNumberOfRows_listSize5_returns2() {
        // Arrange
        val inputList = listOf("item1", "item2", "item3", "item4", "item5")
        val expectedOutput = 2

        // Act
        val actualOutput = inputList.getNumberOfRows()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testGetNumberOfRows_listSize8_returns2() {
        // Arrange
        val inputList =
            listOf("item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8")
        val expectedOutput = 2

        // Act
        val actualOutput = inputList.getNumberOfRows()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testGetNumberOfRows_listSize9_returns3() {
        // Arrange
        val inputList =
            listOf("item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9")
        val expectedOutput = 3

        // Act
        val actualOutput = inputList.getNumberOfRows()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }
}
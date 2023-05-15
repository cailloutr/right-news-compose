package com.cailloutr.rightnewscompose.extensions


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionKtTest {


    @Test
    fun testToRouteId_noSlashes_returnsOriginalString() {
        // Arrange
        val inputString = "abcd1234"
        val expectedRouteId = inputString

        // Act
        val actualRouteId = inputString.toRouteId()

        // Assert
        assertThat(actualRouteId).isEqualTo(expectedRouteId)
    }

    @Test
    fun testToRouteId_withSlashes_replacesSlashesWithUnderscores() {
        // Arrange
        val inputString = "path/to/some/route"
        val expectedRouteId = "path_to_some_route"

        // Act
        val actualRouteId = inputString.toRouteId()

        // Assert
        assertThat(actualRouteId).isEqualTo(expectedRouteId)
    }

    @Test
    fun testToRouteId_emptyString_returnsEmptyString() {
        // Arrange
        val inputString = ""
        val expectedRouteId = ""

        // Act
        val actualRouteId = inputString.toRouteId()

        // Assert
        assertThat(actualRouteId).isEqualTo(expectedRouteId)
    }

    @Test
    fun testFromRouteId_validString_returnsModifiedString() {
        // Arrange
        val inputString = "path_to_some_route"
        val expectedOutput = "path/to/some/route"

        // Act
        val actualOutput = inputString.fromRouteId()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testFromRouteId_emptyString_returnsEmptyString() {
        // Arrange
        val inputString = ""
        val expectedOutput = ""

        // Act
        val actualOutput = inputString.fromRouteId()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }

    @Test
    fun testFromRouteId_stringWithoutUnderscores_returnsUnmodifiedString() {
        // Arrange
        val inputString = "no,underscores,here"
        val expectedOutput = inputString

        // Act
        val actualOutput = inputString.fromRouteId()

        // Assert
        assertThat(actualOutput).isEqualTo(expectedOutput)
    }
}
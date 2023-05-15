package com.cailloutr.rightnewscompose.util


import com.cailloutr.rightnewscompose.util.DateUtil.getFormattedDate
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DateUtilTest {

    @Before
    fun setUp() {
    }

    @Test
    fun testGetFormattedDate_validDate_returnsFormattedDate() {
        // Arrange
        val inputDate = "2023-05-15T12:34:56Z"
        val expectedFormattedDate = "seg., 15 mai., 2023 - 09:34 - BRT"

        // Act
        val actualFormattedDate = getFormattedDate(inputDate)

        // Assert
        assertThat(actualFormattedDate).isEqualTo(expectedFormattedDate)
    }

    @Test
    fun testGetFormattedDate_invalidDate_returnsOriginalDate() {
        // Arrange
        val inputDate = "2023-05-15T12:34:56" // Invalid date format
        val expectedOriginalDate = inputDate

        // Act
        val actualFormattedDate = getFormattedDate(inputDate)

        // Assert
        assertThat(actualFormattedDate).isEqualTo(expectedOriginalDate)
    }
}
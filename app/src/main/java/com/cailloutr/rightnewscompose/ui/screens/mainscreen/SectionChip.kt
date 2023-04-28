package com.cailloutr.rightnewscompose.ui.screens.mainscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.model.ChipItem
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun SectionChipGroup(
    list: List<ChipItem>,
    selectedSection: String,
    onItemSelectedListener: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()
    Row(
        modifier.horizontalScroll(horizontalScrollState)
    ) {
        if (list.isEmpty()) {
            repeat(5) {
                SectionChip(
                    id = "",
                    text = "",
                    onClickItem = {},
                    modifier = Modifier.widthIn(80.dp)
                )
            }
        } else {
            list.forEach {
                SectionChip(
                    id = it.id,
                    text = it.text,
                    onClickItem = { id ->
                        onItemSelectedListener(id)
                    },
                    isChecked = it.id == selectedSection,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionChip(
    id: String,
    text: String,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onClickItem: (String) -> Unit,
) {

    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val primary = MaterialTheme.colorScheme.primary
    val surface = MaterialTheme.colorScheme.onPrimary
    val onSurface = MaterialTheme.colorScheme.onSurface

    val selectedContainer by animateColorAsState(
        targetValue = if (isChecked) primary else surface,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    val selectedLabel by animateColorAsState(
        targetValue = if (isChecked) onPrimary else onSurface,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )


    FilterChip(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        },
        selected = isChecked,
        onClick = { onClickItem(id) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = selectedContainer,
            selectedLabelColor = selectedLabel
        ),
        modifier = modifier
            .padding(horizontal = 8.dp)
    )
}

@Preview
@Composable
fun SectionChipPreview() {
    RightNewsComposeTheme {
        Surface {
            SectionChip(
                id = "news",
                text = "News",
                isChecked = true,
                onClickItem = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkSectionChipPreview() {
    RightNewsComposeTheme {
        Surface {
            SectionChip(
                id = "news",
                text = "News",
                isChecked = false,
                onClickItem = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionChipGroupPreview() {
    RightNewsComposeTheme {
        SectionChipGroup(
            list = List(5) {
                ChipItem(it.toString(), it.toString())
            },
            selectedSection = "3",
            onItemSelectedListener = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkSectionChipGroupPreview() {
    RightNewsComposeTheme {
        SectionChipGroup(
            list = List(5) {
                ChipItem(it.toString(), it.toString())
            },
            selectedSection = "3",
            onItemSelectedListener = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EmptySectionChipGroupPreview() {
    RightNewsComposeTheme {
        SectionChipGroup(
            list = listOf(),
            selectedSection = "3",
            onItemSelectedListener = {})
    }
}
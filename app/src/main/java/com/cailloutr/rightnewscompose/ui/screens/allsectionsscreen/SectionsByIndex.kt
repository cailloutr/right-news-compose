package com.cailloutr.rightnewscompose.ui.screens.allsectionsscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.model.Section
import com.cailloutr.rightnewscompose.model.SectionsByIndex
import com.cailloutr.rightnewscompose.ui.components.SectionChip
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SectionsByIndex(
    rows: Int,
    lazyStaggeredGridState: LazyStaggeredGridState,
    contentPadding: Float,
    section: SectionsByIndex,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    // IndexHeight + rowCount * ChipHeight + (rows - 1) * contentPadding
    val gridHeight = 32 + rows * 40 + (rows - 1).coerceAtLeast(1) * contentPadding

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(gridHeight.dp)
    ) {
        Text(
            text = section.index,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .requiredHeight(32.dp)
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(rows),
            state = lazyStaggeredGridState,
            contentPadding = PaddingValues(contentPadding.dp),
            verticalArrangement = Arrangement.spacedBy(contentPadding.dp),
            horizontalItemSpacing = contentPadding.dp,
            modifier =  Modifier.fillMaxSize()

        ) {
            items(section.list) {section ->
                SectionChip(
                    id = section.id,
                    text = section.title,
                    onClickItem = {
                        onClick(it)
                    },
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .height(40.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun SectionsByIndexPreview() {
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val section = SectionsByIndex(
        index = "A",
        List(5) { index ->
            Section(
                id = index.toString(),
                title = index.toString(),
                webUrl = index.toString(),
                apiUrl = index.toString(),
                code = index.toString()
            )
        }
    )
    val gridRows = when (section.list.size) {
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
    RightNewsComposeTheme {
        SectionsByIndex(
            rows = gridRows,
            lazyStaggeredGridState = lazyStaggeredGridState,
            contentPadding = 8f,
            section = section
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkSectionsByIndexPreview() {
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    RightNewsComposeTheme {

        val section = SectionsByIndex(
            index = "A",
            List(20) { index ->
                Section(
                    id = index.toString(),
                    title = index.toString(),
                    webUrl = index.toString(),
                    apiUrl = index.toString(),
                    code = index.toString()
                )
            }
        )
        val gridRows = when (section.list.size) {
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
        RightNewsComposeTheme {
            SectionsByIndex(
                rows = gridRows,
                lazyStaggeredGridState = lazyStaggeredGridState,
                contentPadding = 8f,
                section = section
            )
        }
    }
}
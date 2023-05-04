package com.cailloutr.rightnewscompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.screens.detailscreen.DropDownItem
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun DropDownMenu(
    dropDownItems: List<DropDownItem>,
    onClick: (DropDownItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(),
                onClick = { isContextMenuVisible = true },
            ) {
                Image(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.options),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible = false },
            ) {
                dropDownItems.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onClick(it)
                            isContextMenuVisible = false
                        },
                        text = {
                            Text(text = stringResource(id = it.text))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DropDownMenuPreview() {
    RightNewsComposeTheme {
        Surface {
            DropDownMenu(
                dropDownItems = listOf(
                    DropDownItem(R.string.open_in_browser),
                    DropDownItem(R.string.share)
                ),
                onClick = {}
            )
        }
    }
}
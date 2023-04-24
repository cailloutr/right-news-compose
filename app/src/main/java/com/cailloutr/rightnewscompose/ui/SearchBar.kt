package com.cailloutr.rightnewscompose.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = { newValue -> onValueChange(newValue) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent

        ),
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(text = "Search")
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text)
            }
        ),
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(56.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    RightNewsComposeTheme {
        Surface {
            SearchBar(
                modifier = Modifier,
                text = "",
                onValueChange = {},
                onSearch = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkSearchBarPreview() {
    RightNewsComposeTheme {
        Surface {
            SearchBar(
                modifier = Modifier,
                text = "",
                onValueChange = {},
                onSearch = {}
            )
        }
    }
}
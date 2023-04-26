package com.cailloutr.rightnewscompose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.text.Html
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme


@Composable
fun HtmlText(text: String, textColor: Color, modifier: Modifier = Modifier) {
    val htmlDescription = remember(text) {
        Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                maxLines = 2
                ellipsize = TextUtils.TruncateAt.END
                movementMethod = LinkMovementMethod.getInstance()
                setTextAppearance(R.style.TextAppearance_RightNews_Subtitle1_Bold)
                setTextColor(textColor.toArgb())
            }
        },
        update = {
            it.text = htmlDescription
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HtmlTextPreview() {
    RightNewsComposeTheme {
        HtmlText(text = "Teste teste teste", MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkHtmlTextPreview() {
    RightNewsComposeTheme {
        HtmlText(text = "Teste teste teste", MaterialTheme.colorScheme.onSurface)
    }
}
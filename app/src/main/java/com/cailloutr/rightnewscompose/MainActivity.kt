package com.cailloutr.rightnewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cailloutr.rightnewscompose.ui.RightNewsApp
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RightNewsComposeTheme {
                RightNewsApp()
            }
        }
    }
}


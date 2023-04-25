package com.cailloutr.rightnewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cailloutr.rightnewscompose.navigation.RootNavGraph
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RightNewsComposeTheme {
                RootNavGraph()
            }
        }
    }
}


package com.cailloutr.rightnewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.model.Banner
import com.cailloutr.rightnewscompose.model.ChipItem
import com.cailloutr.rightnewscompose.ui.SectionChipGroup
import com.cailloutr.rightnewscompose.ui.screens.MainScreen
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isChecked by remember {
                mutableStateOf("")
            }
            RightNewsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Surface {
                        SectionChipGroup(
                            list = List(5) {
                                ChipItem(it.toString(), it.toString())
                            },
                            selectedSection = isChecked,
                            onItemSelectedListener = { id ->
                                isChecked = id
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val bannerList by remember {
        mutableStateOf(
            List(5) {
                Banner(it.toLong(), it.toString(), it.toString(), it.toString())
            }
        )
    }
    val sectionsList by remember {
        mutableStateOf(
            List(7) {
                ChipItem(
                    id = it.toString(),
                    text = it.toString(),
                )
            }
        )
    }
    val articles by remember {
        mutableStateOf(
            List(10) {
                Article(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    false,
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            }
        )
    }
    RightNewsComposeTheme {
        MainScreen(
            bannerState = bannerList,
            mainSectionsState = sectionsList,
            isRefreshingSectionsNewsState = false,
            sectionNewsState = articles
        )
    }
}
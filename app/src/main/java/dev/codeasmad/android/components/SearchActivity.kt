package dev.codeasmad.android.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import dev.codeasmad.android.components.ui.theme.CamComponentsTheme
import dev.codeasmad.android.components.vm.SearchVm

typealias TextListener = (String) -> Unit

class SearchActivity : ComponentActivity() {

    private val viewModel: SearchVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamComponentsTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val queryState by viewModel.query.observeAsState("")

                    SearchUi(queryState) {
                        viewModel.setQuery(it)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchUi(query: String, onQueryChange: TextListener) {
    TextField(value = query, onValueChange = onQueryChange)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CamComponentsTheme {
        SearchUi("") {

        }
    }
}
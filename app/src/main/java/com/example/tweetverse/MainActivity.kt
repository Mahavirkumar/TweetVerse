package com.example.tweetverse

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tweetverse.api.TweetVerseAPI
import com.example.tweetverse.screens.CategoryScreen
import com.example.tweetverse.screens.DetailScreen
import com.example.tweetverse.ui.theme.TweetVerseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tweetVerseAPI: TweetVerseAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val response = tweetVerseAPI.getCategories()
            Log.d("mk", response.body().toString())
        }

        setContent {
            TweetVerseTheme {
//              CategoryScreen()
//                DetailScreen()
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "category") {
        composable(route = "category") {
            CategoryScreen {
             navController.navigate("detail/${it}")
            }
        }
        composable(
            route = "detail/{category}", arguments = listOf(
                navArgument("category"){
                    type= NavType.StringType
                }
            )
        ) {
            DetailScreen()
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TweetVerseTheme {
//        Greeting("Android")
//    }
//}
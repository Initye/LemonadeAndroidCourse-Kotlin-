package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme() // Light Theme
            ) {
                LemonadeApp()
            }
        }
    }
}

@Preview
@Composable
fun LemonadeApp() {
    ImageAndText(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}


@Preview(showBackground = true)
@Composable
fun ImageAndText(modifier: Modifier = Modifier) {

    var currentClicks by remember { mutableIntStateOf(0) }
    var requiredClicks by remember { mutableIntStateOf((1..4).random())} //To generate first time number of clicks
    var result by remember { mutableIntStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val stringResource = when (result) {
        1 -> R.string.tree_tap
        2 -> R.string.tree_keep_tapping
        3 -> R.string.lemonade_drink
        else -> R.string.start_again
    }

    val contentDescription = when (result) {
        1 -> R.string.cd_lemon_tree
        2 -> R.string.cd_lemon
        3 -> R.string.cd_glass_lemonade
        else -> R.string.cd_glass_empty
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), //Light Theme in Column
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .background(color = Color (0xfff0f573))
                .fillMaxWidth()
        ) {
            Text(
                text = "Lemonade",
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 20.dp)
                    .align(Alignment.Center),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(imageResource),
                contentDescription = contentDescription.toString(),
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, //to avoid error on MutableInteractionSource
                        indication = null,
                        onClick = {
                            if (result == 2) {
                                currentClicks++

                                if (currentClicks >= requiredClicks) {
                                    result = (result % 4) + 1
                                    currentClicks = 0
                                    requiredClicks = (1..4).random() //to generate requiredClicks different from the first one
                                }
                            } else {
                                    result = (result % 4) + 1
                                }
                        }
                    )

                    .background(
                        color = Color(0xffa2c1f2),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(16.dp)
            )

            Text(
                modifier = Modifier
                    .padding(16.dp),
                    fontSize = 18.sp,
                text = stringResource(stringResource)
            )

            if (result == 2) {
                Text (
                    modifier = Modifier
                        .padding(18.dp),
                    text = "Required clicks: $currentClicks / $requiredClicks"
                )
            }
        }
}

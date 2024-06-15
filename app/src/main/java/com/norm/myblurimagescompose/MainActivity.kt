package com.norm.myblurimagescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.norm.myblurimagescompose.ui.theme.MyBlurImagesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBlurImagesComposeTheme {
                enableEdgeToEdge()

                val imageList = remember {
                    mutableListOf(
                        MyImage(
                            1,
                            R.drawable.image1
                        ),
                        MyImage(
                            2,
                            R.drawable.image2
                        ),
                        MyImage(
                            3,
                            R.drawable.image3
                        ),
                    )
                }

                val selectedMyImage = remember {
                    mutableStateListOf<Int>()
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(imageList) { image ->
                        val isBlurred by remember(selectedMyImage) {
                            derivedStateOf {
                                selectedMyImage.contains(image.id)
                            }
                        }

                        val bluerAnimation by animateDpAsState(
                            targetValue = if (isBlurred) {
                                18.dp
                            } else
                                0.dp,
                            label = "",
                        )

                        Image(
                            painter = painterResource(image.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius = bluerAnimation)
                                .size(256.dp)
                                .clickable {
                                    if (selectedMyImage.contains(image.id)) {
                                        selectedMyImage.remove(image.id)
                                    } else {
                                        selectedMyImage.add(image.id)
                                    }
                                }
                        )
                    }

                }

            }
        }
    }
}

data class MyImage(
    val id: Int,
    @DrawableRes val image: Int,
)
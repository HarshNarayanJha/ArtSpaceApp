package com.werewolfstudiios.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.werewolfstudiios.artspaceapp.data.ArtworkData
import com.werewolfstudiios.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtworkApp()
                }
            }
        }
    }
}

@Composable
fun ArtworkApp(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    println(ArtworkData.artworks.size)
    println(ArtworkData.artworks.lastIndex)
    println(currentArtwork)
    ArtworkPage(
        artwork = ArtworkData.artworks[currentArtwork],
        buttonPrevEnabled = currentArtwork > 0,
        buttonNextEnabled = currentArtwork < ArtworkData.artworks.lastIndex,
        onPrevPressed = {
            if (currentArtwork > 0) {
                currentArtwork--
            }
        },
        onNextPressed = {
            if (currentArtwork < ArtworkData.artworks.lastIndex) {
                currentArtwork++
            }
        },
        modifier = modifier.verticalScroll(scrollState)
    )
}

@Composable
fun ArtworkPage(
    artwork: Artwork,
    buttonPrevEnabled: Boolean,
    buttonNextEnabled: Boolean,
    onPrevPressed: () -> Unit,
    onNextPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier.padding(4.dp)
    ) {

        Card(
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(artwork.image),
                contentDescription = stringResource(artwork.imageDescription),
                modifier = Modifier
                    .padding(12.dp)
            )
        }

//        Spacer(Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.Bottom,
        ) {
            Card(
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(artwork.artTitle),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = stringResource(
                            R.string.artist_year_format,
                            stringResource(artwork.artist),
                            stringResource(artwork.year)
                        ),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(onClick = { onPrevPressed() }, enabled = buttonPrevEnabled) {
                    Text(stringResource(R.string.button_prev))
                }
                Button(onClick = { onNextPressed() }, enabled = buttonNextEnabled) {
                    Text(stringResource(R.string.button_next))
                }
            }
        }
    }
}

class Artwork(
    @DrawableRes val image: Int,
    @StringRes val artTitle: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int,
    @StringRes val imageDescription: Int,
) { }

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ArtworkPagePreview() {
    val artwork = Artwork(
        image = R.drawable.artwork1,
        artTitle = R.string.art1_title,
        artist = R.string.art1_artist,
        year = R.string.art1_year,
        imageDescription = R.string.art1_year
    )
    ArtworkPage(artwork = artwork, buttonPrevEnabled = true, buttonNextEnabled = true, onPrevPressed = {}, onNextPressed = {})
}
package com.cailloutr.rightnewscompose.ui.screens.profilescreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.components.SettingsCard
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    isLogged: Boolean = false,
    username: String = "",
    location: String = "",
    navigateToLogin: () -> Unit,
) {
    if (isLogged) {
        UserProfileScreen(username = username, location = location, modifier = modifier)
    } else {
        NotLoggedScreen(modifier = modifier, navigateToLogin = { navigateToLogin() })
    }
}

@Composable
fun UserProfileScreen(
    username: String,
    location: String,
    modifier: Modifier = Modifier,
    logout: () -> Unit = {},
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.size(50.dp))

        SettingsCard(
            text = "Edit Profile",
            icon = Icons.Default.Edit,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            onClick = {},
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingsCard(
            text = stringResource(R.string.settings),
            icon = Icons.Default.Settings,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            onClick = {},
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingsCard(
            text = stringResource(R.string.logout),
            icon = painterResource(id = R.drawable.ic_logout_24),
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.error,
            onClick = {
                logout()
            },
            modifier = Modifier
        )
    }
}

@Composable
fun NotLoggedScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(130.dp))
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .size(175.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.size(25.dp))
        Text(
            text = stringResource(R.string.not_logged_message_login_screen),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(55.dp))
        Button(onClick = { navigateToLogin() }) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun UserProfileScreenPrev() {
    RightNewsComposeTheme {
        Surface {
            UserProfileScreen(
                username = "Username",
                location = "Rio de Janeiro - Brazil"
            )
        }
    }
}

@Preview
@Composable
fun NotLoggedScreenPrev() {
    RightNewsComposeTheme {
        Surface() {
            NotLoggedScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RightNewsComposeTheme {
        ProfileScreen(navigateToLogin = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkProfileScreenPreview() {
    RightNewsComposeTheme {
        ProfileScreen(navigateToLogin = {})
    }
}
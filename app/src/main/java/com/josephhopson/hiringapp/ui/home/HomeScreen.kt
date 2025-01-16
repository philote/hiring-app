@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.josephhopson.hiringapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josephhopson.hiringapp.AppBar
import com.josephhopson.hiringapp.R
import com.josephhopson.hiringapp.data.HiringItem
import com.josephhopson.hiringapp.ui.AppViewModelProvider
import com.josephhopson.hiringapp.ui.theme.HiringAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    // viewModel UI state
    val homeUiState by viewModel.uiState.collectAsState()
    val currentState = homeUiState.currentState

    Scaffold(
        modifier = Modifier,
        topBar = {
            AppBar(
                title = stringResource(R.string.app_name),
                scrollBehavior = enterAlwaysScrollBehavior(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(currentState) {
                HomeUiStates.Landing -> Landing()
                HomeUiStates.Loading -> Loading()
                HomeUiStates.Error -> Error()
                is HomeUiStates.Success -> ItemList(
                    items = currentState.items
                )
            }
        }
    }
}

@Composable
fun ItemList(
    items: Map<Int, List<HiringItem>>
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items.forEach { (listId, items) ->
            stickyHeader {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Text(
                        text = stringResource(R.string.heading, listId),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                        style= MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
            items(items) { item ->
                ItemView(item = item)
            }
        }
    }
}

@Composable
fun ItemView(
    item: HiringItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = "Name: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = item.name ?: "Unknown")
            }
            Row {
                Text(
                    text = "ID: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = item.id.toString())
            }
            Row {
                Text(
                    text = "List ID: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = item.listId.toString())
            }
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Landing() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.search),
            contentDescription = stringResource(R.string.search)
        )
        Text(
            text = stringResource(R.string.search),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.error),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            style= MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    HiringAppTheme {
        Loading()
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPreview() {
    HiringAppTheme {
        Landing()
    }
}


@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    HiringAppTheme {
        Error()
    }
}

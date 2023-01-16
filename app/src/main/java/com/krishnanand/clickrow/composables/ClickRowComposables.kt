package com.krishnanand.clickrow.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.krishnanand.clickrow.R
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.viewmodels.ClickRowViewModel
import kotlinx.coroutines.launch


internal const val SNACKBAR_TEST_TAG = "snackbarTag"

@Composable
fun ClickRowComposables(viewModel: ClickRowViewModel, modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.click_rows),
                        modifier = Modifier.semantics { heading() },
                    )
                }
            )
        },
        snackbarHost = { snackbarHostState ->
            SnackbarHost(hostState = snackbarHostState) { data: SnackbarData ->
                Snackbar(
                    shape = RectangleShape,
                    backgroundColor = MaterialTheme.colors.primarySurface,
                    modifier = Modifier.testTag(SNACKBAR_TEST_TAG)
                ) {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primaryVariant
                    )
                }

            }
        }
    ) {

        val onRowClick:(String) -> Unit  = { snackbarString: String ->
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(snackbarString)
            }
        }
        ClickRowListUi(viewModel = viewModel, onRowClick, modifier = modifier.padding(it))
    }
}


@Composable
fun ClickRowListUi(viewModel: ClickRowViewModel, onClick: (String) -> Unit, modifier: Modifier) {
    val clickRows by viewModel.clickRowsFlow.collectAsState(mutableListOf())
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.CenterVertically)
        .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(clickRows) {
            ClickRowUi(it, onClick, modifier)
        }
    }
}


@Composable
fun ClickRowUi(clickRow: ClickRow, onClick: (String) -> Unit, modifier: Modifier) {
    val redeemCouponString = stringResource(id = R.string.redeem_coupon)
    Column(
        modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(role = Role.Button, onClickLabel = stringResource(id = R.string.redeem_coupon_accessibility)) {
                onClick(redeemCouponString)
            }
    ) {
        Text(
            text = clickRow.message,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
        Text(
            text = clickRow.expiresOn,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Divider(
            color = MaterialTheme.colors.onSurface
        )
    }
}
package com.krishnanand.clickrow.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.krishnanand.clickrow.composables.ClickRowComposables
import com.krishnanand.clickrow.ui.theme.ClickRowTheme
import com.krishnanand.clickrow.viewmodels.ClickRowViewModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClickRowActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val viewModel: ClickRowViewModel by viewModels {
        viewModelProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.fetchAndInitialiseClickRows()
        setContent {
            ClickRowTheme {
                ClickRowComposables(viewModel)
            }
        }
    }
}
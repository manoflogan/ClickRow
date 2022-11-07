package com.krishnanand.clickrow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.repository.ClickRowRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClickRowViewModel @Inject constructor(
    private val clickRowRepository: ClickRowRepository
): ViewModel() {

    private val _clickRowsFlow = MutableStateFlow<List<ClickRow>>(mutableListOf())
    val clickRowsFlow = _clickRowsFlow.asStateFlow()

    fun fetchAndInitialiseClickRows() {
        viewModelScope.launch {
            _clickRowsFlow.update {
                clickRowRepository.fetchClickRows()
            }
        }
    }
}
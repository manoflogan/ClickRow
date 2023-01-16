package com.krishnanand.clickrow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.repository.ClickRowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClickRowViewModel @Inject constructor(
    private val clickRowRepository: ClickRowRepository
): ViewModel() {

    private val _clickRowsFlow = MutableStateFlow<List<ClickRow>>(mutableListOf())
    val clickRowsFlow:Flow<List<ClickRow>> = _clickRowsFlow.filter {
        it.isNotEmpty()
    }

    fun fetchAndInitialiseClickRows() {
        viewModelScope.launch {
            _clickRowsFlow.value = clickRowRepository.fetchClickRows()
        }
    }
}
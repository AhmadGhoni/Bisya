package dev.ghoni.bisya.ui.screen.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ghoni.bisya.ui.component.DownloadState
import dev.ghoni.bisya.ui.component.ListItemData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExtViewModel : ViewModel() {
    private val _data = MutableStateFlow(
        listOf(
            ListItemData("SIBI Alfabet", "AhmadGhoni", "1.1", DownloadState.DOWNLOADED),
            ListItemData("Bahasa Isyarat SIBI", "AhmadGhoni", "1.0", DownloadState.NOT_DOWNLOADED),
            ListItemData("SIBI Translator", "AhmadGhoni", "1.0-Beta", DownloadState.NOT_DOWNLOADED)
        )
    )
    val data: StateFlow<List<ListItemData>> = _data

    private fun updateListItem(index: Int, updatedItem: ListItemData) {
        val currentList = _data.value.toMutableList()
        currentList[index] = updatedItem.copy() // Create a new instance
        _data.value = currentList
        viewModelScope.launch {
            addDummyItem()
            delay(1)
            removeDummyItem()
        }
    }

    fun startDownload(index: Int) {
        val newData = _data.value.toMutableList()
        newData[index].downloadState = DownloadState.DOWNLOADING
        updateListItem(index, newData[index])

        // Simulating download progress
        viewModelScope.launch {
            for (i in 0..100) {
                delay(50) // Simulating delay
            }
            newData[index].downloadState = DownloadState.DOWNLOADED
            updateListItem(index, newData[index])
        }
    }

    fun uninstall(index: Int) {
        val newData = _data.value.toMutableList()
        newData[index].downloadState = DownloadState.DOWNLOADING
        updateListItem(index, newData[index])

        viewModelScope.launch {
            delay(1000)
            newData[index].downloadState = DownloadState.NOT_DOWNLOADED
            updateListItem(index, newData[index])
        }
    }

    fun addDummyItem() {
        val newData = _data.value.toMutableList()
        val dummyItem = ListItemData("", "", "", DownloadState.DUMMY)
        newData.add(dummyItem)
        _data.value = newData
    }

    fun removeDummyItem() {
        val newData = _data.value.toMutableList()
        if (newData.isNotEmpty()) {
            newData.removeAt(newData.size - 1)
            _data.value = newData
        }
    }
}
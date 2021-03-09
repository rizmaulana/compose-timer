package com.example.androiddevchallenge.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.component.cell.LapCell
import com.example.androiddevchallenge.data.Lap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LapList(laps: List<Lap>) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState, reverseLayout = true) {
        itemsIndexed(laps) { index, item ->
            LapCell(index = index, lap = item)
            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
        }
        CoroutineScope(Dispatchers.Main).launch {
            if (laps.lastIndex >= 0){
                listState.scrollToItem(laps.lastIndex, 0)
            }
        }
    }
}
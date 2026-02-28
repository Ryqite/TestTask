package com.example.innowise.Presentation.UI_Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CollectionsRow(
    collections: List<String>,
    selected: String?,
    onSelect: (String) -> Unit
) {
    val displayedList = remember(collections, selected) {
        if (!selected.isNullOrEmpty() && collections.contains(selected)) {
            val list = collections.toMutableList()
            list.remove(selected)
            list.add(0, selected)
            list
        } else {
            collections
        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.Start
    ) {

        items(displayedList) { item ->
            val isSelected = item == selected
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color(0xFFBB1020) else Color(0xFFF3F5F9),
                onClick = { onSelect(item) }
            ) {
                Text(
                    text = item,
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

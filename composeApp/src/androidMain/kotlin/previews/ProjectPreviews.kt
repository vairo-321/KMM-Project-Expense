package previews

import App
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.ExpenseManager
import model.Expense
import model.ExpenseCategory
import presentation.ExpensesUiState
import ui.*


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}


@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    ExpensesScreen(uiState = ExpensesUiState.Success(
        expenses = ExpenseManager.fakeExpenseList,
        total = ExpenseManager.fakeExpenseList.sumOf { it.amount },
    ), onExpenseClick = { })
}

@Preview(showBackground = true)
@Composable
fun ExpenseDetailScreenPreview() {
    ExpensesDetailScreen (ExpenseManager.fakeExpenseList[0], addExpenseAndNavigateBack = { })
}

@Preview(showBackground = true)
@Composable
fun ExpenseTotalHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)){
        ExpenseTotalHeader(1200.00)
    }
}


@Preview(showBackground = true)
@Composable
fun AllExpensesHeaderPreview(){
    Box(modifier = Modifier.padding(16.dp)){
        AllExpensesHeader()
    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseItemPreview(){
    Box(modifier = Modifier.padding(8.dp)){
        ExpensesItem(ExpenseManager.fakeExpenseList[0], onExpenseClick = { })
    }
}
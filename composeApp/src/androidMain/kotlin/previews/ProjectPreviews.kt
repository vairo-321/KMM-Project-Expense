package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.Expense
import model.ExpenseCategory
import ui.*


@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    ExpensesScreen()
}


@Preview(showBackground = true)
@Composable
fun ExpenseTotalHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)){
        ExpenseTotalHeader(1000.00)
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
        ExpensesItem(expense = Expense(
            id = 1L,
            amount = 100.00,
            category = ExpenseCategory.PARTY,
            description = "Party",
        ), onExpenseClick = { })
    }
}


package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getColorsTheme
import model.Expense
import presentation.ExpensesUiState
import utils.EXPENSE_SCREEN_ERROR_TEST_TAG
import utils.EXPENSE_SCREEN_ERROR_TEXT_TEST_TAG
import utils.EXPENSE_SCREEN_LOADING_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_EMPTY_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_TOTAL_TEST_TAG
import utils.SwipeToDeleteContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpensesScreen(
    uiState: ExpensesUiState,
    onExpenseClick: (expense: Expense) -> Unit,
    onDeleteExpense: (expense: Expense) -> Unit
) {

    val colors = getColorsTheme()

    when (uiState) {
        is ExpensesUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag(EXPENSE_SCREEN_LOADING_TEST_TAG),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ExpensesUiState.Success -> {
            if (uiState.expenses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                        .testTag(EXPENSE_SCREEN_SUCCESS_EMPTY_TEST_TAG),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "No expenses found, please add your first expense with the + symbol down below",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).testTag(
                        EXPENSE_SCREEN_SUCCESS_TEST_TAG
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    stickyHeader {
                        Column(modifier = Modifier.background(colors.backgroundColor)) {
                            ExpenseTotalHeader(uiState.total)
                            AllExpensesHeader()
                        }
                    }
                    items(items = uiState.expenses, key = { it.id }) { expense ->
                        SwipeToDeleteContainer(
                            item = expense,
                            onDelete = onDeleteExpense
                        ) {
                            ExpensesItem(expense = expense, onExpenseClick = onExpenseClick)
                        }
                    }
                }
            }
        }

        is ExpensesUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize().testTag(EXPENSE_SCREEN_ERROR_TEST_TAG),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.testTag(EXPENSE_SCREEN_ERROR_TEXT_TEST_TAG),
                    text = "Error: ${uiState.message}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Composable
fun ExpenseTotalHeader(total: Double) {
    Card(
        shape = RoundedCornerShape(30),
        backgroundColor = Color.Black,
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(130.dp).padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.testTag(EXPENSE_SCREEN_SUCCESS_TOTAL_TEST_TAG),
                text = "$$total",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "USD",
                color = Color.Gray,
                fontSize = 30.sp
            )
        }
    }
}


@Composable
fun AllExpensesHeader() {

    val colors = getColorsTheme()

    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        Text(
            modifier = Modifier.weight(1f),
            text = "All Expenses",
            color = colors.textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Button(
            shape = RoundedCornerShape(50),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = false
        ) {
            Text(
                text = "View All",
            )
        }
    }
}


@Composable
fun ExpensesItem(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {

    val colors = getColorsTheme()

    //Card is container for each Row
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp).clickable {
            onExpenseClick(expense)
        }.testTag(EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG.plus("_${expense.id}")),
        shape = RoundedCornerShape(30),
        backgroundColor = colors.colorExpenseItem,
    ) {
        //Fila de cada detalle de expensa item, cada una de esta fila ira en la List<Row>
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Icon
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(35),
                color = colors.purple
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    imageVector = expense.icon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            //Column middle part, description and amount
            Column(
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text(
                    text = expense.category.name,
                    color = colors.textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = expense.description,
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            //Amount
            Text(
                text = "$${expense.amount}",
                color = colors.textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

















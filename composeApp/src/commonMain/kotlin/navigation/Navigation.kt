package navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import presentation.ExpensesViewModel
import ui.ExpensesDetailScreen
import ui.ExpensesScreen

@Composable
fun Navigation(navigator: Navigator) {

    val colors = getColorsTheme()

    //ViewModel With Koin
    val viewModel = koinViewModel(ExpensesViewModel::class)

    //ViewModel With PreCompose
    /*val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
        ExpensesViewModel(ExpenseRepositoryImpl(ExpenseManager))
    }
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
     */

    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene("/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(
                uiState = uiState,
                onExpenseClick = { expense ->
                    navigator.navigate("/addExpenses/${expense.id}")
                },
                onDeleteExpense = { expenseToDelete ->
                    viewModel.deleteExpense(expenseToDelete.id)
                })
        }

        scene(route = "/addExpenses/{id}?") { backStackEntry ->
            val id = backStackEntry.path<Long>("id")
            val expenseToEditOrAdd = id?.let { viewModel.getExpenseById(id) }

            ExpensesDetailScreen(
                expenseToEdit = expenseToEditOrAdd,
                categories = viewModel.getCategories()
            ) { expense ->

                if (expenseToEditOrAdd == null) {
                    viewModel.addExpense(expense)
                } else {
                    viewModel.editExpense(expense)
                }
                navigator.popBackStack()
            }

        }

    }

}
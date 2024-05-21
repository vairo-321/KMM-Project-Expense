import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.ExpenseManager
import data.ExpenseRepositoryImpl
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.viewmodel.viewModel
import presentation.ExpensesUiState
import presentation.ExpensesViewModel
import ui.ExpensesScreen

@Composable
fun App() {
    PreComposeApp {


        val viewModel = viewModel(modelClass = ExpensesViewModel::class){
            ExpensesViewModel(ExpenseRepositoryImpl(ExpenseManager))
        }

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AppTheme {
            ExpensesScreen(uiState = ExpensesUiState(
                expenses = uiState.expenses,
                total = uiState.total
            ))
        }
    }
}
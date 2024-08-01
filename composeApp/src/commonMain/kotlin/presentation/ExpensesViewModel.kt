package presentation

import domain.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Expense
import model.ExpenseCategory
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


sealed class ExpensesUiState {
    data class Success(val expenses: List<Expense>, val total: Double) : ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()
    object Loading : ExpensesUiState()
}

class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading) // <-- This line es de lectura y escritura, aqui se modifica los estados desde el ViewModel, aqui se modifica solo esto
    val uiState =
        _uiState.asStateFlow()    // <-- This line es de solo lectura, se usa para "leer" el estado del Viewmodel desde los composables (UI)
    private var allExpenses: MutableList<Expense> = mutableListOf()

    init {
        getExpenseList()
    }

    private fun getExpenseList() {
        viewModelScope.launch {
            try {
                val expenses = repo.getAllExpenses()
                _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
            } catch (error: Exception) {
                _uiState.value = ExpensesUiState.Error(error.message ?: "Error desconocido")
            }
        }
    }

    private suspend fun updateExpenseList() {
        try {
            val expenses = repo.getAllExpenses()
            _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
        } catch (error: Exception) {
            _uiState.value = ExpensesUiState.Error(error.message ?: "Error desconocido")
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.addExpense(expense)
                updateExpenseList()
            } catch (error: Exception) {
                _uiState.value = ExpensesUiState.Error(error.message ?: "Error desconocido")
            }
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.editExpense(expense)
                updateExpenseList()
            } catch (error: Exception) {
                _uiState.value = ExpensesUiState.Error(error.message ?: "Error desconocido")
            }
        }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }

    //sin uso
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.deleteExpense(expense)
                updateExpenseList()
            } catch (error: Exception) {
                _uiState.value = ExpensesUiState.Error(error.message ?: "Error desconocido")
            }
        }
    }

    fun getExpenseById(id: Long): Expense? {
        return (_uiState.value as? ExpensesUiState.Success)?.expenses?.firstOrNull { it.id == id }
    }
}
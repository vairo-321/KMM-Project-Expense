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


data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)

class ExpensesViewModel(private val repo: ExpenseRepository): ViewModel() {


    private val _uiState = MutableStateFlow(ExpensesUiState()) // <-- This line es de lectura y escritura, aqui se modifica los estados desde el ViewModel, aqui se modifica solo esto
    val uiState = _uiState.asStateFlow()    // <-- This line es de solo lectura, se usa para "leer" el estado del Viewmodel desde los composables (UI)
    private var allExpenses : MutableList<Expense> = mutableListOf()

    init {
        getAllExpenses()
    }

    private fun updateExpenseList(){
        viewModelScope.launch {
            allExpenses = repo.getAllExpenses().toMutableList()
            updateState()
        }
    }

    private fun getAllExpenses() {
        repo.getAllExpenses()
        updateExpenseList()
    }

    fun addExpense(expense: Expense) {
            repo.addExpense(expense)
            updateExpenseList()
    }

    fun editExpense(expense: Expense) {
            repo.editExpense(expense)
            updateExpenseList()
    }

    private fun updateState(){
        _uiState.update { state ->
            state.copy(
                expenses = allExpenses,
                total = allExpenses.sumOf { it.amount }
            )
        }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }

    //sin uso
    fun deleteExpense(expense: Expense) {
            repo.deleteExpense(expense)
            updateExpenseList()
    }

    fun getExpenseById(id: Long): Expense {
        return allExpenses.first { it.id == id }
    }

}
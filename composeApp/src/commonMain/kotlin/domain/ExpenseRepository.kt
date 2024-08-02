package domain

import model.Expense
import model.ExpenseCategory
import org.koin.core.scope.ScopeID

interface ExpenseRepository {

    suspend fun getAllExpenses(): List<Expense>
    suspend fun addExpense(expense: Expense)
    suspend fun editExpense(expense: Expense)
    fun getCategories(): List<ExpenseCategory>
    suspend fun deleteExpense(id: Long)

}
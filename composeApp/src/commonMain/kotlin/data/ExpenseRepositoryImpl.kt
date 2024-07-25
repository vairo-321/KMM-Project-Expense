package data

import com.expenseApp.db.AppDatabase
import domain.ExpenseRepository
import model.Expense
import model.ExpenseCategory

class ExpenseRepositoryImpl(
    private val expenseManager: ExpenseManager,
    private val appDatabase: AppDatabase
) : ExpenseRepository {

    private val queries = appDatabase.expensesDbQueries

    override fun getAllExpenses(): List<Expense> {
        return queries.selectAll().executeAsList().map {
            Expense(
                id = it.id,
                amount = it.amount,
                category = ExpenseCategory.valueOf(it.category),
                description = it.description
            )
        }
    }

    override fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                category = expense.category.name,
                description = expense.description
            )
        }
    }

    override fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                category = expense.category.name,
                description = expense.description
            )
        }
    }


    override fun getCategories(): List<ExpenseCategory> {
        return queries.selectCategoriName().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
    }

    override fun deleteExpense(expense: Expense) {
        queries.deleteExpense(id = expense.id)
    }

}
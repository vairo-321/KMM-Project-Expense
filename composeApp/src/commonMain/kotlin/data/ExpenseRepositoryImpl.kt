package data

import com.expenseApp.db.AppDatabase
import domain.ExpenseRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import model.Expense
import model.ExpenseCategory
import model.RemoteExpense

private const val BASE_URL = "http://192.168.100.157:8080"

class ExpenseRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val httpClient: HttpClient
) : ExpenseRepository {

    private val queries = appDatabase.expensesDbQueries


    override suspend fun getAllExpenses(): List<Expense> {
        return if (queries.selectAll().executeAsList().isEmpty()) {
            val networkResponse = httpClient.get("$BASE_URL/expenses").body<List<RemoteExpense>>()
            val expenses = networkResponse.map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description
                )
            }
            expenses.forEach {
                queries.insert(
                    amount = it.amount,
                    category = it.category.name,
                    description = it.description
                )
            }
            expenses
        } else {
            queries.selectAll().executeAsList().map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.category),
                    description = it.description
                )
            }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                category = expense.category.name,
                description = expense.description
            )
        }
        httpClient.post("$BASE_URL/expenses") {
            contentType(ContentType.Application.Json)
            setBody(
                RemoteExpense(
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description
                )
            )
        }
    }

    override suspend fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                category = expense.category.name,
                description = expense.description
            )
        }
        httpClient.put("$BASE_URL/expenses/${expense.id}") {
            contentType(ContentType.Application.Json)
            setBody(
                RemoteExpense(
                    id = expense.id,
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description
                )
            )
        }
    }


    override fun getCategories(): List<ExpenseCategory> {
        return queries.selectCategoriName().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        queries.deleteExpense(id = expense.id)
    }

}
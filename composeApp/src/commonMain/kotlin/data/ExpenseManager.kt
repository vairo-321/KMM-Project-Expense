package data

import model.Expense
import model.ExpenseCategory

object ExpenseManager {

    private var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 70.00,
            description = "Weekly buy",
            category = ExpenseCategory.GROCERIES
        ),
        Expense(
            id = currentId++,
            amount = 430.00,
            description = "Rent",
            category = ExpenseCategory.HOUSE
        ),
        Expense(
            id = currentId++,
            amount = 10.00,
            description = "Coffee",
            category = ExpenseCategory.COFFEE
        ),
        Expense(
            id = currentId++,
            amount = 21000.00,
            description = "Audi A1",
            category = ExpenseCategory.CAR
        ),
        Expense(
            id = currentId++,
            amount = 1400.00,
            description = "Lunch",
            category = ExpenseCategory.GROCERIES
        )
    )


    fun addExpense(expense: Expense) {
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun removeExpense(expense: Expense) {
        fakeExpenseList.remove(expense)
    }

    fun editExpense(expense: Expense) {
        val index = fakeExpenseList.indexOfFirst { it.id == expense.id }
        fakeExpenseList[index] = expense
    }

    fun getCategories(): List<ExpenseCategory> {
        return listOf(
            ExpenseCategory.GROCERIES,
            ExpenseCategory.HOUSE,
            ExpenseCategory.COFFEE,
            ExpenseCategory.CAR,
            ExpenseCategory.OTHER,
            ExpenseCategory.PARTY,
            ExpenseCategory.SNACK
        )
    }

    fun getExpense(id: Long): Expense? {
        return fakeExpenseList.find { it.id == id }
    }

    fun getAllExpenses(): List<Expense> {
        return fakeExpenseList
    }
}
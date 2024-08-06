import data.ExpenseManager
import data.ExpenseRepositoryImpl
import model.Expense
import model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExpensesRepoTest {
/*
    private val expenseManager: ExpenseManager = ExpenseManager
    val repository: ExpenseRepositoryImpl = ExpenseRepositoryImpl(expenseManager)
    val expenseListTest: List<Expense> = listOf(
        Expense(amount = 20.0, category = ExpenseCategory.PARTY, description = "Party"),
        Expense(amount = 40.0, category = ExpenseCategory.CAR, description = "Mechanic")
        )


    @Test
    fun testAddExpense() {
        repository.addExpense(expenseListTest[0])
        val id = repository.getAllExpenses().last().id
        assertContains(repository.getAllExpenses(), expenseListTest[0].copy(id = id))
    }

    @Test
    fun testListNotEmpty() {
        assertTrue(repository.getAllExpenses().isNotEmpty())
    }

    @Test
    fun testEditExpense() {
        repository.addExpense(expenseListTest[0])
        val id = repository.getAllExpenses().last().id
        val newExpense = expenseListTest[1].copy(id = id)
        repository.editExpense(newExpense)
        assertContains(repository.getAllExpenses(), newExpense)
    }

    @Test
    fun testGetAllCategoriesAndCheck() {
        val categoryListTest = repository.getCategories()
        assertTrue(categoryListTest.isNotEmpty())

        val categories = ExpenseCategory.entries.toTypedArray()

        categories.forEach {
            assertTrue(categoryListTest.contains(it))
        }
    }

    @Test
    fun testDeleteExpense() {
        repository.addExpense(expenseListTest[0])
        val id = repository.getAllExpenses().last().id
        repository.deleteExpense(expenseListTest[0].copy(id = id))
        assertFalse(repository.getAllExpenses().contains(expenseListTest[0].copy(id = id)))
    }
*/

}
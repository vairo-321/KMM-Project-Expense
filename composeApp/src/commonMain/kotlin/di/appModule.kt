package di

import com.expenseApp.db.AppDatabase
import data.ExpenseRepositoryImpl
import domain.ExpenseRepository
import org.koin.dsl.module
import presentation.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase)}
    factory { ExpensesViewModel(get()) }
}
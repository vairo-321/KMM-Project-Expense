package di

import com.expenseApp.db.AppDatabase
import data.ExpenseRepositoryImpl
import domain.ExpenseRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import presentation.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single<HttpClient> { HttpClient{ install(ContentNegotiation) { json() } } }
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase, get())}
    factory { ExpensesViewModel(get()) }
}
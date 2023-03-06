package com.example.mytodos.di

import com.example.mytodos.data.local.database.TodosDatabase
import com.example.mytodos.data.repo.MyTodosRepository
import com.example.mytodos.ui.viewModel.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myApplicationModule = module {

    single {
        TodosDatabase.getDatabaseInstance(androidContext()).todosDao()
    }

    factory { MyTodosRepository(get()) }

    viewModel { TodoViewModel(get()) }

}
package com.example.mytodos.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel() : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    fun add( disposable : ()-> Disposable){
        compositeDisposable.add(disposable())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
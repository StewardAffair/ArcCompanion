package com.lohika.arccompanion.domain

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class UseCase <Type, Params> where Type : Any {

    abstract fun run(params: Params) : Single<Type>

    operator fun invoke(params: Params): Single<Type> = run(params).subscribeOn(Schedulers.io())

    class None
}
package ru.sulgik.test.common.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class UseCase<Type, Params> {

    private var job : Job? = null

    private var command : ObservableUseCase<Type, Params>? = null

    protected abstract suspend fun run(params: Params) : Type

    internal open fun execute(command : ObservableUseCase<Type, Params>, params : Params){
        this.command = command

        executeCoroutine(command, params)
    }

    private fun executeCoroutine(command: ObservableUseCase<Type, Params>, params: Params){
        job = command.scope.launch(command.context) {
            val a = executeWithErrorHandling(params)
            if (a != null){
                emitValue(a)
            }
            clearCommand()
        }
    }

    private suspend fun executeWithErrorHandling(params: Params): Type? {
        try{
           return run(params)
        }catch (e : Exception){
            emitException(e)
        }
        return null
    }

    internal open fun cancel(){
        job?.cancel()
        clearCommand()
    }

    private fun emitValue(value : Type){
        command?.emitValue(value)
    }

    private fun emitException(e : Exception){
        command?.emitException(e)
    }

    private fun clearCommand(){
        command = null
    }

}
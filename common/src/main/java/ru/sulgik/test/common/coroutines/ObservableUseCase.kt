package ru.sulgik.test.common.coroutines

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sulgik.test.common.*
import ru.sulgik.test.common.events.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ObservableUseCase <Type, Params> (
    internal val scope : CoroutineScope,
    internal val context : CoroutineContext,
    private val case : UseCase<Type, Params>
) : ObservableCase<Type> {

    override var failureListener : OnFailureListener = {}
    override var successListener : OnSuccessListener<Type> = {}
    override var loadingListener : OnLoadingListener = {}
    override var cancelListener: OnCancelListener = {}

    private val _result : MutableLiveEvent<Type> = MutableLiveData()
    override val result : LiveEvent<Type> = _result

    private var isExecutable = true
        private set(value) {
            field = value
            _isExecutableLiveData.postValue(value)
        }
    private val _isExecutableLiveData = MutableLiveData(true)
    override val isExecutableLiveData : LiveData<Boolean> = _isExecutableLiveData

    private var isExecuted = false
        private set(value) {
            field = value
            _isExecutedLiveData.postValue(value)
        }
    private val _isExecutedLiveData = MutableLiveData(isExecuted)
    override val isExecutedLiveData : LiveData<Boolean> = _isExecutedLiveData

    fun execute(params : Params){
        if(!isExecutable || isExecuted)
            return

        run(params)
    }

    fun cancel(){
        emitCancel()
        case.cancel()
    }

    private fun run(params: Params){
        emitLoading()
        case.execute(this, params)
    }

    @MainThread
    private fun onSuccess(value : Type){
        successListener.invoke(value)
        _result.postResult(value)
        isExecuted = false
    }

    @MainThread
    private fun onFailure(e : Exception){
        failureListener.invoke(e)
        _result.postException(e)
        isExecuted = false
    }

    @MainThread
    private fun onLoading(){
        loadingListener.invoke()
        _result.postLoading()
        isExecuted = true
    }

    private fun onCancel(){
        cancelListener.invoke()
        _result.postCancel()
        isExecuted = false
    }

    internal fun emitValue(value : Type){
        scope.launch(Dispatchers.Main) {
            onSuccess(value)
        }
    }

    internal fun emitException(exception: Exception){
        scope.launch(Dispatchers.Main) {
            onFailure(exception)
        }
    }

    private fun emitLoading(){
        scope.launch(Dispatchers.Main) {
            onLoading()
        }
    }

    private fun emitCancel(){
        scope.launch(Dispatchers.Main) {
            onCancel()
        }
    }

}
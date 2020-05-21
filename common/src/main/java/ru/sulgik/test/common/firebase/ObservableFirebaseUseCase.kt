package ru.sulgik.test.common.firebase

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import ru.sulgik.test.common.*
import ru.sulgik.test.common.events.*
import java.lang.Exception


class ObservableFirebaseUseCase<Type>(task : Task<Type>) :
    ObservableCase<Type> {

    private val _result = MutableLiveEvent<Type>()
    override val result: LiveEvent<Type> = _result

    override var failureListener: OnFailureListener = {}
    override var successListener: OnSuccessListener<Type> = {}
    override var loadingListener: OnLoadingListener = {}
    override var cancelListener: OnCancelListener = {}

    private val isExecutable = false
    override val isExecutableLiveData: LiveData<Boolean> = MutableLiveData(isExecutable)

    private var isExecuted = true
        set(value){
            field = value
            _isExecutedLiveData.postValue(value)
        }
    private val _isExecutedLiveData = MutableLiveData(isExecuted)
    override val isExecutedLiveData: LiveData<Boolean> = _isExecutedLiveData

    init {
        onLoading()
        bind(task)
    }

    private fun bind(task: Task<Type>){
        task.addOnCompleteListener {
            isExecuted = false
        }
        task.addOnSuccessListener {
            onSuccess(it)
        }
        task.addOnFailureListener {
            onFailure(it)
        }
        task.addOnCanceledListener {
            onCancel()
        }
    }

    @MainThread
    private fun onSuccess(result : Type){
        _result.postResult(result)
        successListener.invoke(result)
    }

    @MainThread
    private fun onLoading(){
        _result.postLoading()
        loadingListener.invoke()
    }

    @MainThread
    private fun onCancel(){
        _result.postCancel()
        cancelListener.invoke()
    }

    @MainThread
    private fun onFailure(e : Exception){
        _result.postException(e)
        failureListener.invoke(e)
    }

}
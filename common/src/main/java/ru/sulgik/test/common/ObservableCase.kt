package ru.sulgik.test.common

import androidx.lifecycle.LiveData

interface ObservableCase<Type> {

    var failureListener : OnFailureListener
    var successListener : OnSuccessListener<Type>
    var loadingListener : OnLoadingListener
    var cancelListener: OnCancelListener

    val isExecutableLiveData : LiveData<Boolean>

    val isExecutedLiveData : LiveData<Boolean>

    val result : LiveEvent<Type>
}
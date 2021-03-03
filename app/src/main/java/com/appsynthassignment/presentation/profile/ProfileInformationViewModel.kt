package com.appsynthassignment.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsynthassignment.data.domain.GetProfileAndNotificationUseCase
import com.appsynthassignment.data.model.uimodel.ProfileInformationUiModel
import com.appsynthassignment.util.StatusResult
import kotlinx.coroutines.launch

class ProfileInformationViewModel(
    private val useCase: GetProfileAndNotificationUseCase
) : ViewModel() {

    private val _statusResult = MutableLiveData<StatusResult>()
    val statusResult: LiveData<StatusResult>
        get() = _statusResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _profileInformation = MutableLiveData<ProfileInformationUiModel>()
    val profileInformation: LiveData<ProfileInformationUiModel>
        get() = _profileInformation

    fun fetchData() {
        viewModelScope.launch {
            if (statusResult.value != StatusResult.SUCCESS) {
                _statusResult.value = StatusResult.LOADING
                useCase.getProfileInformation().let { result ->
                    if (result.isSuccess && result.data != null) {
                        // onSuccess
                        _profileInformation.value = ProfileInformationUiModel(
                            profile = result.data.getProfileUi(),
                            notification = result.data.getNotificationUiModel()
                        )
                        _statusResult.value = StatusResult.SUCCESS
                    } else {
                        // onError
                        _errorMessage.value = result.exception?.message
                        _statusResult.value = StatusResult.ERROR
                    }
                }
            }
        }
    }


}
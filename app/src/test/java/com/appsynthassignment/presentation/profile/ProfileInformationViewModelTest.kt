package com.appsynthassignment.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appsynthassignment.data.domain.GetProfileAndNotificationUseCase
import com.appsynthassignment.data.model.response.ProfileInformationResponseMapper
import com.appsynthassignment.data.model.uimodel.NotificationUiModel
import com.appsynthassignment.data.model.uimodel.ProfileUiModel
import com.appsynthassignment.util.ResultResponse
import com.appsynthassignment.util.StatusResult
import com.appsynthassignment.util.exception.NoInternetException
import com.appsynthassignment.util.exception.ResponseErrorException
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileInformationViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase: GetProfileAndNotificationUseCase

    private lateinit var viewModel: ProfileInformationViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())

        viewModel = ProfileInformationViewModel(useCase)
    }

    @Test
    fun `should show profile information data when getProfileInformation() success after call fetchData()`() =
        runBlockingTest {
            // given
            val response: ProfileInformationResponseMapper = mockk()
            val result = ResultResponse.success(response)
            val profileUiModel = ProfileUiModel()
            val notificationList = listOf<NotificationUiModel>()

            coEvery { useCase.getProfileInformation() } returns result
            every { response.getProfileUi() } returns profileUiModel
            every { response.getNotificationUiModel() } returns notificationList

            //when
            viewModel.profileInformation.observeForever {}
            viewModel.statusResult.observeForever {}
            viewModel.errorMessage.observeForever {}
            viewModel.fetchData()

            // then
            assertThat(viewModel.profileInformation.value?.profile).isEqualTo(profileUiModel)
            assertThat(viewModel.profileInformation.value?.notification).isEqualTo(notificationList)
            assertThat(viewModel.statusResult.value).isEqualTo(StatusResult.SUCCESS)
            assertThat(viewModel.errorMessage.value).isNull()
        }

    @Test
    fun `should show error when getProfileInformation() failed NoInternetException after call fetchData()`() =
        runBlockingTest {
            // given
            val exception: NoInternetException = mockk()
            val result = ResultResponse.error<ProfileInformationResponseMapper>(exception)
            val errorMessage = "No Internet"
            coEvery { useCase.getProfileInformation() } returns result
            every { exception.message } returns errorMessage

            //when
            viewModel.profileInformation.observeForever {}
            viewModel.statusResult.observeForever {}
            viewModel.errorMessage.observeForever {}
            viewModel.fetchData()

            // then
            assertThat(viewModel.profileInformation.value).isEqualTo(null)
            assertThat(viewModel.statusResult.value).isEqualTo(StatusResult.ERROR)
            assertThat(viewModel.errorMessage.value).isEqualTo(errorMessage)
        }

    @Test
    fun `should show error when getProfileInformation() failed ResponseErrorException after call fetchData()`() =
        runBlockingTest {
            // given
            val exception: ResponseErrorException = mockk()
            val result = ResultResponse.error<ProfileInformationResponseMapper>(exception)
            val errorMessage = "Bad Request"
            coEvery { useCase.getProfileInformation() } returns result
            every { exception.message } returns errorMessage

            //when
            viewModel.profileInformation.observeForever {}
            viewModel.statusResult.observeForever {}
            viewModel.errorMessage.observeForever {}
            viewModel.fetchData()

            // then
            assertThat(viewModel.profileInformation.value).isNull()
            assertThat(viewModel.statusResult.value).isEqualTo(StatusResult.ERROR)
            assertThat(viewModel.errorMessage.value).isEqualTo(errorMessage)
        }

}
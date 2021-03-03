package com.appsynthassignment.data.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.data.model.response.ProfileResponseModel
import com.appsynthassignment.data.repository.DataRepository
import com.appsynthassignment.util.ResultResponse
import com.appsynthassignment.util.exception.NoInternetException
import com.appsynthassignment.util.exception.ResponseErrorException
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetProfileAndNotificationUseCaseImpTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: DataRepository

    private lateinit var useCase: GetProfileAndNotificationUseCase

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        useCase = GetProfileAndNotificationUseCaseImp(repository)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        clearAllMocks()
    }

    @Test
    fun `getProfileInformation success`() = runBlocking {
        // given
        val userId = "userId"
        val profile: ProfileResponseModel = mockk()
        val notification: List<NotificationResponseModel> = mockk()
        val profileResult = ResultResponse.success(profile)
        val notificationResult = ResultResponse.success(notification)

        every { profile.userId } returns userId
        coEvery { repository.getProfile() } returns profileResult
        coEvery { repository.getNotification(eq(userId)) } returns notificationResult

        // when
        val result = useCase.getProfileInformation()

        // then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.exception).isEqualTo(null)
        assertThat(result.data?.profile).isEqualTo(profile)
        assertThat(result.data?.notification).isEqualTo(notification)
    }

    @Test
    fun `getProfileInformation failed on getProfile NoInternetException`() = runBlocking {
        // given
        val noInternetException: NoInternetException = mockk()
        val resultResponse = ResultResponse.error<ProfileResponseModel>(noInternetException)

        coEvery { repository.getProfile() } returns resultResponse

        // when
        val result = useCase.getProfileInformation()

        // then
        assertThat(result.isSuccess).isFalse()
        assertThat(result.data).isNull()
        assertThat(result.exception).isEqualTo(noInternetException)
    }

    @Test
    fun `getProfileInformation failed on getProfile ResponseErrorException`() = runBlocking {
        // given
        val responseException: ResponseErrorException = mockk()
        val resultResponse = ResultResponse.error<ProfileResponseModel>(responseException)

        coEvery { repository.getProfile() } returns resultResponse

        // when
        val result = useCase.getProfileInformation()

        // then
        assertThat(result.isSuccess).isFalse()
        assertThat(result.data).isNull()
        assertThat(result.exception).isEqualTo(responseException)
    }

    @Test
    fun `getProfileInformation failed on getNotification NoInternetException`() = runBlocking {
        // given
        val userId = "userId"
        val profile: ProfileResponseModel = mockk()
        val noInternetException: NoInternetException = mockk()
        val profileResult = ResultResponse.success(profile)
        val notificationResult =
            ResultResponse.error<List<NotificationResponseModel>>(noInternetException)

        every { profile.userId } returns userId
        coEvery { repository.getProfile() } returns profileResult
        coEvery { repository.getNotification(eq(userId)) } returns notificationResult

        // when
        val result = useCase.getProfileInformation()

        // then
        assertThat(result.isSuccess).isFalse()
        assertThat(result.data).isNull()
        assertThat(result.exception).isEqualTo(noInternetException)
    }

    @Test
    fun `getProfileInformation failed on getNotification ResponseErrorException`() = runBlocking {
        // given
        val userId = "userId"
        val profile: ProfileResponseModel = mockk()
        val responseErrorException: ResponseErrorException = mockk()
        val profileResult = ResultResponse.success(profile)
        val notificationResult =
            ResultResponse.error<List<NotificationResponseModel>>(responseErrorException)

        every { profile.userId } returns userId
        coEvery { repository.getProfile() } returns profileResult
        coEvery { repository.getNotification(eq(userId)) } returns notificationResult

        // when
        val result = useCase.getProfileInformation()

        // then
        assertThat(result.isSuccess).isFalse()
        assertThat(result.data).isNull()
        assertThat(result.exception).isEqualTo(responseErrorException)
    }

}
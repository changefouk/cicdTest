package com.appsynthassignment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.data.model.response.ProfileResponseModel
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
class DataRepositoryImpTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: DataRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        repository = DataRepositoryImp(localDataSource, remoteDataSource)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        clearAllMocks()
    }

    @Test
    fun `getProfile success`() = runBlocking {
        // given
        val result = ResultResponse.success<ProfileResponseModel>(mockk())
        coEvery { remoteDataSource.getProfile() } returns result


        //when
        val resultValue = repository.getProfile()

        coVerify { remoteDataSource.getProfile() }

        // then
        assertThat(resultValue).isEqualTo(result)
        assertThat(resultValue.isSuccess).isTrue()
    }

    @Test
    fun `getProfile failed`() = runBlocking {
        // given
        val exception = NoInternetException(mockk())
        val result = ResultResponse.error<ProfileResponseModel>(exception)
        coEvery { remoteDataSource.getProfile() } returns result

        //when
        val resultValue = repository.getProfile()

        coVerify { remoteDataSource.getProfile() }

        // then
        assertThat(resultValue).isEqualTo(result)
        assertThat(resultValue.exception).isEqualTo(exception)
        assertThat(resultValue.isSuccess).isFalse()
    }

    @Test
    fun `getProfile - localDataSource success`() = runBlocking {
        // given
        val id = "id"
        val notificationList = listOf<NotificationResponseModel>()
        val result = ResultResponse.success(notificationList)
        coEvery { localDataSource.getListNotification(id) } returns result

        //when
        val resultValue = repository.getNotification(id)

        // then
        coVerify { localDataSource.getListNotification(id) }
        assertThat(resultValue.data).isEqualTo(notificationList)
        assertThat(resultValue).isEqualTo(result)
        assertThat(resultValue.isSuccess).isTrue()
    }

    @Test
    fun `getProfile - localDataSource fail no data , remoteDataSource success`() = runBlocking {
        // given
        val id = "id"
        val exceptionLocal = NoInternetException(mockk())
        val resultLocal = ResultResponse.error<List<NotificationResponseModel>>(exceptionLocal)
        coEvery { localDataSource.getListNotification(id) } returns resultLocal

        val notificationListRemote = listOf<NotificationResponseModel>()
        val resultRemote = ResultResponse.success(notificationListRemote)
        coEvery { remoteDataSource.getNotification(id) } returns resultRemote
        coEvery { localDataSource.insertAll(any()) } returns Unit

        //when
        val resultValue = repository.getNotification(id)

        coVerify {
            localDataSource.getListNotification(id)
            remoteDataSource.getNotification(id)
            localDataSource.insertAll(any())
        }
        assertThat(resultValue).isEqualTo(resultRemote)
        assertThat(resultValue.isSuccess).isTrue()
        assertThat(resultValue.data).isEqualTo(notificationListRemote)
    }

    @Test
    fun `getProfile - localDataSource fail no data , remoteDataSource failed`() = runBlocking {
        // given
        val id = "id"
        val exceptionLocal = NoInternetException(mockk())
        val resultLocal = ResultResponse.error<List<NotificationResponseModel>>(exceptionLocal)
        coEvery { localDataSource.getListNotification(id) } returns resultLocal

        val exceptionRemote = ResponseErrorException("", 0)
        val resultRemote = ResultResponse.error<List<NotificationResponseModel>>(exceptionRemote)
        coEvery { remoteDataSource.getNotification(id) } returns resultRemote

        //when
        val resultValue = repository.getNotification(id)

        coVerify {
            localDataSource.getListNotification(id)
            remoteDataSource.getNotification(id)
        }
        assertThat(resultValue).isEqualTo(resultRemote)
        assertThat(resultValue.exception).isEqualTo(exceptionRemote)
        assertThat(resultValue.isSuccess).isFalse()
        assertThat(resultValue.data).isNull()
    }


}
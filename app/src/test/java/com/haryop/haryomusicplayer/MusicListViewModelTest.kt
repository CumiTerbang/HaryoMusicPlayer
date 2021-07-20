package com.haryop.haryomusicplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.data.repository.DataRepository
import com.haryop.haryomusicplayer.ui.main.MusicListViewModel
import com.haryop.haryomusicplayer.utils.Resource
import com.haryop.haryomusicplayer.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var contentEntityObserver: Observer<Resource<List<ContentEntity>>>

    @Mock
    private lateinit var returnvalue: LiveData<Resource<List<ContentEntity>>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun getSearch_Success() {
        val term = "bruno"

        testCoroutineRule.runBlockingTest {
            doReturn(returnvalue)
                .`when`(dataRepository)
                .getSearchContent(term)

            val viewModel = MusicListViewModel(dataRepository)
            viewModel.getSearchContent.observeForever(contentEntityObserver)
            verify(dataRepository).getSearchContent(term)
            verify(contentEntityObserver).onChanged(Resource.success(emptyList()))
            viewModel.getSearchContent.removeObserver(contentEntityObserver)

        }
    }

    @Test
    fun getSearch_Error() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            val term = "bruno"

            doThrow(RuntimeException(errorMessage))
                .`when`(dataRepository)
                .getSearchContent(term)
            val viewModel = MusicListViewModel(dataRepository)
            viewModel.getSearchContent.observeForever(contentEntityObserver)
            verify(dataRepository).getSearchContent(term)
            verify(contentEntityObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.getSearchContent.removeObserver(contentEntityObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}
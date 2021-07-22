package com.haryop.haryomusicplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.data.remote.ApiServices
import com.haryop.haryomusicplayer.data.remote.RemoteDataSource
import com.haryop.haryomusicplayer.data.repository.DataRepository
import com.haryop.haryomusicplayer.ui.main.MusicListViewModel
import com.haryop.haryomusicplayer.utils.Resource
import com.haryop.haryomusicplayer.utils.TestCoroutineRule
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var viewModel: MusicListViewModel

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var apiServices: ApiServices

    @Mock
    private lateinit var contentEntityObserver: Observer<Resource<List<ContentEntity>>>

    @Mock
    private lateinit var returnvalue: LiveData<Resource<List<ContentEntity>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(MusicListViewModel(DataRepository(remoteDataSource, apiServices)))
        viewModel.start("bruno")
        returnvalue = viewModel.getSearchContent
    }

    @Test
    fun getSearchContent() {
        assertNotNull(viewModel.getSearchContent)
//        viewModel.getSearchContent.observeForever(contentEntityObserver)

        viewModel.getSearchContent.observeForever({
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    assertNotNull(it.data)
                }
                Resource.Status.ERROR -> {
                    assertNotNull(it.message)
                }
                Resource.Status.LOADING -> {
                    assertNull(it.data)
                }
            }
        })

    }

    @After
    fun tearDown() {
        // do something if required
    }

}
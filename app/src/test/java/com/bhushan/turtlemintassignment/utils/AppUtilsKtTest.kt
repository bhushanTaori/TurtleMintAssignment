package com.bhushan.turtlemintassignment.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.utils.networkUtility.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AppUtilsKtTest {
    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    private lateinit var observer: Observer<Resource<List<IssueData>>>

    @Mock
    private lateinit var liveData: MediatorLiveData<Resource<List<IssueData>>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testKotlinUtil() {
        liveData.observeOnce(lifecycleOwner, observer)
        Assert.assertFalse(liveData.hasActiveObservers())
    }
}
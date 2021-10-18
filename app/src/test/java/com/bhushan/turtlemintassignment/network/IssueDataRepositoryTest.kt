package com.bhushan.turtlemintassignment.network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bhushan.turtlemintassignment.data.IssueCommentData
import com.bhushan.turtlemintassignment.data.IssueData
import com.bhushan.turtlemintassignment.utils.networkUtility.AppExecutors
import com.bhushan.turtlemintassignment.utils.networkUtility.Resource
import com.bhushan.turtlemintassignment.utils.networkUtility.Status
import com.bhushan.turtlemintassignment.viewModel.IssueDataViewModel
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class IssueDataRepositoryTest  {

    @Mock
    private lateinit var appExecutors: AppExecutors

    @Mock
    private lateinit var issueDataService: IssueDataService

    @Mock
    private lateinit var mockIssueDataViewModel: IssueDataViewModel

    @Mock
    private lateinit var liveData: MediatorLiveData<Resource<List<IssueData>>>

    @Mock
    private lateinit var issueCommentLiveData: MediatorLiveData<Resource<List<IssueCommentData>>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testIssueDataRepository() {
        val issueDataRepository = IssueDataRepository(appExecutors, issueDataService)
        Assert.assertFalse(issueDataRepository==null)

        val issueDataViewModel = IssueDataViewModel(issueDataRepository)
        Assert.assertFalse(issueDataViewModel==null)

        `when`(mockIssueDataViewModel.getIssueData()).thenReturn(liveData)
        `when`(mockIssueDataViewModel.getIssueCommentData(1)).thenReturn(issueCommentLiveData)
        val resultLiveData: LiveData<Resource<List<IssueData>>> = issueDataViewModel.getIssueData()
        val resultIssueCommentLiveData: LiveData<Resource<List<IssueCommentData>>> = issueDataViewModel.getIssueCommentData(1)
        Assert.assertThat(resultLiveData.value, `is`(liveData.value))
        Assert.assertThat(resultIssueCommentLiveData.value, `is`(issueCommentLiveData.value))

        val issueData1 = IssueData()
        val issueData2 = IssueData()
        val issueData3 = IssueData()
        val list:List<IssueData> = listOf(issueData1, issueData2, issueData3)
        liveData.value = Resource(Status.SUCCESS, list, "", 200)
        `when`(mockIssueDataViewModel.getIssueData()).thenReturn(liveData)
        Assert.assertThat(resultLiveData.value?.data, `is`(liveData.value?.data))

        val issueCommentData1 = IssueCommentData()
        val issueCommentData2 = IssueCommentData()
        val issueCommentData3 = IssueCommentData()
        val issueCommentDataList:List<IssueCommentData> = listOf(issueCommentData1, issueCommentData2, issueCommentData3)
        issueCommentLiveData.value = Resource(Status.SUCCESS, issueCommentDataList, "", 200)
        `when`(mockIssueDataViewModel.getIssueCommentData(1)).thenReturn(issueCommentLiveData)
        Assert.assertThat(resultIssueCommentLiveData.value?.data, `is`(resultIssueCommentLiveData.value?.data))
    }
}
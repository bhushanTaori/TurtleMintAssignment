package com.bhushan.turtlemintassignment.data

import com.bhushan.turtlemintassignment.utils.ISSUE_URL
import com.bhushan.turtlemintassignment.utils.getCommentIssueUrl
import com.bhushan.turtlemintassignment.utils.getDate
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class IssueDataUnitTest {
    private lateinit var issueData: IssueData

    @Before
    fun setUp() {
        issueData = IssueData(
            12345,
            "3.12.12 BOM references invalid okhttp-logging-interceptor coordinate",
            "When using the 3.12.12 BOM it's necessary to manually specify the version for logging-interceptor as the BOM published references the coordinate",
            updated_at = "2021-10-13T05:51:38Z"
        )
    }

    @Test
    fun test_default_values() {
        assertEquals(0, issueData.number)
        assertNull(issueData.user)
    }

    @Test
    fun test_updated_date() {
        assertEquals("10-13-2021", getDate(issueData.updated_at))
    }

    @Test
    fun test_number() {
        assertEquals("${ISSUE_URL}0", getCommentIssueUrl(issueData.number))
    }
}
package com.bhushan.turtlemintassignment.data

import com.bhushan.turtlemintassignment.utils.getDate
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class IssueCommentDataUnitTest {
    private lateinit var issueCommentData: IssueCommentData

    @Before
    fun setUp() {
        issueCommentData = IssueCommentData(
            12345,
            "3.12.12 BOM references invalid okhttp-logging-interceptor coordinate",
            updated_at = "2021-10-13T05:51:38Z"
        )
    }

    @Test
    fun test_default_values() {
        assertNull(issueCommentData.issue_url)
        assertNull(issueCommentData.user)
    }

    @Test
    fun test_updated_date() {
        assertEquals("10-13-2021", getDate(issueCommentData.updated_at))
    }
}
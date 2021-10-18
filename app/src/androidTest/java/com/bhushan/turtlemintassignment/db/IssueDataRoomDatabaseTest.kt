package com.bhushan.turtlemintassignment.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class IssueDataRoomDatabaseTest : TestCase() {

    private lateinit var issueDataDao: IssueDataDao
    private lateinit var db: IssueDataRoomDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, IssueDataRoomDatabase::class.java
        ).build()
        issueDataDao = db.issueDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadSpend() = runBlocking {
        //
    }
}
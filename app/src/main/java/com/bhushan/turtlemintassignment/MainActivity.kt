package com.bhushan.turtlemintassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bhushan.turtlemintassignment.utils.dbUtility.DatabaseHelper
import com.bhushan.turtlemintassignment.view.IssueDataFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseHelper.init(this)
        if (savedInstanceState == null) {
            val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.issueDataFragment)
        }
    }

    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
                supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment?
        val fragment: Fragment? = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (fragment is IssueDataFragment) {
            navHostFragment.navController.popBackStack()
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
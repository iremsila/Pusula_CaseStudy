package com.dijitalgaraj.study.ui

import androidx.navigation.fragment.NavHostFragment
import com.dijitalgaraj.study.R
import com.dijitalgaraj.study.base.BaseActionState
import com.dijitalgaraj.study.base.BaseActivity
import com.dijitalgaraj.study.databinding.ActivityLayoutBinding


class MainActivity :
    BaseActivity<MainViewModel, ActivityLayoutBinding>(MainViewModel::class.java) {
    override fun getViewBinding() = ActivityLayoutBinding.inflate(layoutInflater)

    override fun init() {
        setupNavigation()
    }

    override fun renderActionState(actionState: BaseActionState?) {

    }

    private fun setupNavigation() {
        val navHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_navigation)
        val navController = navHostFragment.navController
        val extras = this.intent.extras
        val startFragmentId = extras?.getInt("startFragmentId")
        val startFragmentArgs = extras?.getBundle("startFragmentArgs")


        navController.graph = graph
        startFragmentId?.takeIf { it != 0 }?.let {
            navController.navigate(it, startFragmentArgs)
        }
    }
}
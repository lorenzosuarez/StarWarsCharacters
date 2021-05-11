package com.example.starwarscharacters.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.starwarscharacters.R
import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.domain.RepositoryImpl
import com.example.starwarscharacters.ui.viewModel.MainViewModel
import com.example.starwarscharacters.ui.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            RepositoryImpl(
                DataSource(
                    AppDatabase.getDatabase(this.applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        /** Get local data **/
        viewModel.getLocalData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
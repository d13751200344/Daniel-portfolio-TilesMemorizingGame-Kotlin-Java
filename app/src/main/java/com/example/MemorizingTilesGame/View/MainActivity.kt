package com.example.MemorizingTilesGame.View

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.assignment3.R;
import com.example.assignment3.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

class MainActivity : AppCompatActivity() {
    /* lateinit is a keyword in Kotlin that is used to declare a variable and tell the
		compiler that the variable will be initialized at a later point in time, rather than
		having to assign a value at the time of declaration.*/
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        // activity_main.xml -> ActivityMainBinding
        lateinit var binding: ActivityMainBinding

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.main
        //val navView: NavigationView = binding.nav_view  //doesn't work
        val navView: NavigationView = findViewById(R.id.nav_view)

        /* parameters: 1. this activity, 2. The DrawerLayout object that contains the
       navigation drawer, 3. & 4. The string to be displayed when the drawer
       is opened and closed (burger or arrow) */
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        // Add a listener to the drawer layout so that the toggle button can be updated
        drawerLayout.addDrawerListener(toggle)

        // Add a listener to the drawer layout so that the toggle button can be updated
        drawerLayout.addDrawerListener(toggle)

        // to sync the state of the drawer toggle with the drawer layout
        toggle.syncState()

        // Enable the home button to open the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.welcomeIcon -> {
                    // Display a toast message when the home button is clicked
                    loadFragment(R.id.constraintLayoutInMain, WelcomeFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)  // Close the drawer
                }

                R.id.gameIcon -> {
                    // Load the fragment
                    loadFragment(R.id.constraintLayoutInMain, GameFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)  // Close the drawer
                }

                R.id.highScoreIcon -> {
                    loadFragment(R.id.constraintLayoutInMain, HighScoreFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)  // Close the drawer
                }

                else -> return@setNavigationItemSelectedListener false
            }
            true
        }

        // Load the WelcomeFragment when the app is opened
        loadFragment(R.id.constraintLayoutInMain, WelcomeFragment())
    }

    // This function is called when the toggle button is clicked so that the drawer can be opened
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Define a method to load the fragment
    // constraintLayoutId is the id of the constraintlayout component in the MainActivity
    fun loadFragment(constraintLayoutId: Int, fragment: Fragment?) {
        val fm: FragmentManager = supportFragmentManager // get the fragment manager
        val ft: FragmentTransaction = fm.beginTransaction() // get the fragment transaction

        // first parameter is the ConstraintLayout ID (because fragments are contained in the activity)
        if (fragment != null) {
            ft.replace(constraintLayoutId, fragment)
        } // add the fragment
        ft.commit() // commit the transaction
    }
}
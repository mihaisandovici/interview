package ibmro.ishqa

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ibmro.ishqa.admin.AdminFragment
import ibmro.ishqa.dashboard.DashboardFragment
import ibmro.ishqa.profile.ProfileActivity
import ibmro.ishqa.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity :
//        BaseActivity(),
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {


    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        super.setLanguage()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setDrawer()
        setStartFragment()
    }


    private fun setStartFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, DashboardFragment(), "todo")
                .commit()
    }

    fun setDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            this.doubleBackToExitPressedOnce = false
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true

            Toast.makeText(this, getString(R.string.please_click_again_exit), Toast.LENGTH_LONG).show()
            Handler().postDelayed({ !doubleBackToExitPressedOnce }, 1000)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                val dashboardFragmnet = DashboardFragment()
                startFragment(dashboardFragmnet)
            }
            R.id.nav_admin -> {
                val adminFragment = AdminFragment()
                startFragment(adminFragment)
            }
            R.id.nav_profile -> {
                val profileIntent=Intent(this,ProfileActivity::class.java)
                startActivity(profileIntent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit()
    }


}

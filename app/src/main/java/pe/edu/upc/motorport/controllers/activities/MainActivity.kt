package pe.edu.upc.motorport.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.controllers.fragments.MapFragment
import pe.edu.upc.motorport.controllers.fragments.AccountFragment
import pe.edu.upc.motorport.controllers.fragments.MechanicalWorkshopsFragment

class MainActivity : AppCompatActivity(){

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = bnvMain
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateTo(navView.menu.findItem(R.id.item_map))
    }

    private fun getFragmentFor(item: MenuItem): Fragment {
        return when(item.itemId) {
            R.id.item_map -> MapFragment()
            R.id.item_account -> AccountFragment()
            R.id.item_workshop -> MechanicalWorkshopsFragment()
            else -> Fragment()
        }
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.isChecked = true

        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, getFragmentFor(item))
            .commit() > 0
    }

}

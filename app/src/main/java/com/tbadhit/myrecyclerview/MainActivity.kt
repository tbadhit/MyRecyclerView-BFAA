package com.tbadhit.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbadhit.myrecyclerview.databinding.ActivityMainBinding

// LIST MODE :
// add package, kotlin parcelize, and binding at build.gradle
// add code in activity_main.xml
// create new resource file "item_row_hero.xml"
// add code in item_row_hero.xml
// Create new model class "Hero" and add code (1)
// Create new adapter class "ListHeroAdapter" and add code (1)
// add code in MainActivity (1) (2)
// add INTERNET PERMISSION in AndroidManifest
// Create "menu" resources in res (new/ Android Resource Directory) choose menu
// Create "menu_main.xml" in menu (new/ Menu Resource File)
// add code in "menu_main.xml"
// add code in MainActivity (3)


class MainActivity : AppCompatActivity() {

    // (1)
    private lateinit var binding: ActivityMainBinding

    // (2)
    private val list = ArrayList<Hero>()

    private var title = "Mode List"

    // Ketika orientasi hp di ubah dia tetap save statenya
    private var mode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionBarTitle(title)

        // (1)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-----

        // (1)
        binding.rvHeroes.setHasFixedSize(true)

        // (2)
        list.addAll(getListHeroes())
        showRecyclerList()
        //-----

        // Ketika orientasi hp di ubah dia tetap save statenya
        if (savedInstanceState == null) {
            setActionBarTitle(title)
            list.addAll(getListHeroes())
            showRecyclerList()
            mode = R.id.action_list
        } else {
            title = savedInstanceState.getString(STATE_TITLE).toString()
            val stateList = savedInstanceState.getParcelableArrayList<Hero>(STATE_LIST)
            val stateMode = savedInstanceState.getInt(STATE_MODE)

            setActionBarTitle(title)
            if (stateList != null) {
                list.addAll(stateList)
            }
            setMode(stateMode)
        }
        //-----
    }

    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "Kamu memilih ${hero.name}", Toast.LENGTH_SHORT).show()
    }

    // Ketika orientasi hp di ubah dia tetap save statenya
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_TITLE, title)
        outState.putParcelableArrayList(STATE_LIST, list)
        outState.putInt(STATE_MODE, mode)
    }
    //-----

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    // (2)
    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription= resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listHero = ArrayList<Hero>()
        for (position in dataName.indices) {
            val hero = Hero(
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listHero.add(hero)
        }
        return listHero
    }

    // (2)
    private fun showRecyclerList() {
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        binding.rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }

        })
    }

    // (4)
    private fun showRecyclerGrid() {
        binding.rvHeroes.layoutManager = GridLayoutManager(this, 2)
        val gridHeroAdapter = GridHeroAdapter(list)
        binding.rvHeroes.adapter = gridHeroAdapter

        gridHeroAdapter.setOnItemClickCallback(object : GridHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }

        })
    }

    // (5)
    private fun showRecyclerCardView() {
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = CardViewHeroAdapter(list)
        binding.rvHeroes.adapter = cardViewHeroAdapter
    }

    // (3)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // (3)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    // (3)
    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            // (3)
            R.id.action_list -> {
                title = "Mode List"
                showRecyclerList()
            }
            // (4)
            R.id.action_grid -> {
                title = "Mode Grid"
                showRecyclerGrid()
            }
            // (5)
            R.id.action_cardview -> {
                title = "Mode CardView"
                showRecyclerCardView()
            }
        }
        mode = selectedMode
        setActionBarTitle(title)
    }

    // Ketika orientasi hp di ubah dia tetap save statenya
    companion object {
        private const val STATE_TITLE = "state_string"
        private const val STATE_LIST = "state_list"
        private const val STATE_MODE = "state_mode"
    }
}
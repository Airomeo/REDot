package me.zzy.redot.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import me.zzy.redot.R
import me.zzy.redot.ui.design.TextSettingActivity

class MainActivity : AppCompatActivity() {
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private val myDataset = arrayOf("aaa", "bbb", "ccc", "ddd")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        list!!.setHasFixedSize(true)

        // use a linear layout manager
        layoutManager = LinearLayoutManager(this)
        list!!.layoutManager = layoutManager

        // specify an adapter (see also next example)
        mAdapter = MyAdapter(myDataset)
        list!!.adapter = mAdapter
        fab.setOnClickListener { view: View? ->
            startActivity(Intent(this, TextSettingActivity::class.java))

//            Snackbar.make(view!!, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
package com.share.greencloud.presentation.activity

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.share.greencloud.R
import com.share.greencloud.domain.interator.SwipeToDeleteCallback
import com.share.greencloud.domain.model.RentalOffice
import com.share.greencloud.presentation.adapter.UserFavoritePlaceAdapter
import com.share.greencloud.presentation.viewmodel.UserFavoritePlaceViewModel
import com.share.greencloud.utils.GreenCloudPreferences
import kotlinx.android.synthetic.main.activity_user_favorite.*
import java.util.*

class UserFavoriteActivity : AppCompatActivity(R.layout.activity_user_favorite) {

    private val viewmodel: UserFavoritePlaceViewModel by lazy { ViewModelProviders.of(this)[UserFavoritePlaceViewModel::class.java] }
    private lateinit var adapter: UserFavoritePlaceAdapter
    private lateinit var userDataFromRemote: List<RentalOffice>
    private var mutableUserData: MutableList<RentalOffice> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupToolbar()
        setupViewModel()
        registerDeleteDataItemAction()
    }

    private fun setupView() {
        user_favorite_recyclerView.setHasFixedSize(true)
        swipe_layout.setOnRefreshListener {
            Handler().postDelayed({
                loadData(mutableUserData)
                swipe_layout.isRefreshing = false
            }, 1000)
        }
        swipe_layout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light)

    }

    private fun setupViewModel() {
        viewmodel.getUserFavoriteData().observe(this, Observer { userData ->
            //            userDataFromRemote = userData
            mutableUserData = userData.toCollection(mutableListOf())
            loadData(mutableUserData)
            user_favorite_recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        })
    }

    private fun loadData(newData: MutableList<RentalOffice>) {
        if (newData.isNotEmpty()) {
            adapter = UserFavoritePlaceAdapter(newData)
            user_favorite_recyclerView.adapter = adapter
            runLayoutAnimation(user_favorite_recyclerView)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(baseContext, getString(R.string.no_data_msg_user_favorite_activity), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    private fun registerDeleteDataItemAction() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val header = HashMap<String, String>()
                header["token"] = GreenCloudPreferences.getToken(baseContext)
                viewmodel.deleteUserFavorite(header, adapter.removeAt(viewHolder.adapterPosition).toString());
                viewmodel.result.observe(this@UserFavoriteActivity, Observer {
                            if (it == 0) {
                                Toast.makeText(baseContext, getString(R.string.success_msg_delete_user_favorite_place), Toast.LENGTH_SHORT).show()
                            }
                        })
                viewmodel.clearResult()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(user_favorite_recyclerView)
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override
    fun onPause() {
        super.onPause()
        finish()
    }
}

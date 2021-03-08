package com.ninis.metaweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.ninis.metaweather.R
import com.ninis.metaweather.adapter.HeaderAdapter
import com.ninis.metaweather.adapter.WeatherAdapter
import com.ninis.metaweather.databinding.ActivityMainBinding
import com.ninis.metaweather.network.MainRepository
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    lateinit var retrofitClient: Retrofit

    lateinit var loadingProgress: KProgressHUD

    private val viewModel: MainViewModel by lazy {
        MainViewModel(MainRepository(retrofitClient))
    }

    private val weatherAdapter = WeatherAdapter()
    private val headerAdapter = HeaderAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvMainList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ConcatAdapter(headerAdapter, weatherAdapter)

            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(this@MainActivity, R.drawable.list_divider)?.let { setDrawable(it) }
            })
        }
        binding.srlMain.apply {
            setOnRefreshListener {
                viewModel.getSearch()
                isRefreshing = false
            }
        }
        binding.executePendingBindings()

        initProgress()

        viewModel.getSearch()

        viewModel.weathers.observe(this, Observer {
            if( it.isNotEmpty() )
                weatherAdapter.setItems(it)
        })

        viewModel.isLoading.observe(this, Observer {
            if( it ) {
                if (!loadingProgress.isShowing)
                    loadingProgress.show()
            } else {
                if( loadingProgress.isShowing )
                    loadingProgress.dismiss()
            }
        })

        viewModel.detailLoadingCount.observe(this, Observer {
            if( it > 0 ) {
                viewModel.checkDetailLoadingComplete()
            }
        })

        viewModel.loadComplete.observe(this, Observer {
            viewModel.setListData()
        })
    }

    private fun initProgress() {
        loadingProgress = KProgressHUD.create(this@MainActivity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.3f)
    }
}
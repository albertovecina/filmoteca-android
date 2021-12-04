package com.vsa.filmoteca.presentation.view.activity

import android.appwidget.AppWidgetManager
import android.content.*
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.Menu
import android.view.MenuItem
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vsa.filmoteca.R
import com.vsa.filmoteca.databinding.ActivityMainBinding
import com.vsa.filmoteca.presentation.presenter.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.utils.ChangeLog
import com.vsa.filmoteca.presentation.view.MoviesListView
import com.vsa.filmoteca.presentation.view.adapter.MoviesAdapter
import com.vsa.filmoteca.presentation.view.adapter.model.MovieViewModel
import com.vsa.filmoteca.presentation.view.dialog.DialogManager
import com.vsa.filmoteca.presentation.view.dialog.interfaces.OkCancelDialogListener
import com.vsa.filmoteca.presentation.view.notifications.NotificationService
import com.vsa.filmoteca.presentation.view.widget.EventsWidgetProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviesListActivity : BaseActivity(), MoviesListView, SwipeRefreshLayout.OnRefreshListener, MoviesAdapter.Callback {

    companion object {
        /**
         * Called when the activity is first created.
         */
        private const val EXTRA_URL = "extra_url"
        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_DATE = "extra_date"

        fun open(context: Context, flags: Int, url: String, title: String, date: String) {
            val intent = newIntent(context, flags, url, title, date)
            context.startActivity(intent)
        }

        fun newIntent(context: Context, flags: Int, url: String, title: String, date: String): Intent {
            val intent = Intent(context, MoviesListActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_DATE, date)
            intent.flags = flags
            return intent
        }

    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.onNewMoviesAdded()
        }
    }

    @Inject
    lateinit var presenter: MoviesListPresenter

    private lateinit var binding: ActivityMainBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter(NotificationService.ACTION_NEW_MOVIES))

        initViews()
        onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun initViews() {
        showTitle(0)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        val itemDecoration = androidx.recyclerview.widget.DividerItemDecoration(this,
                layoutManager.orientation)
        binding.recyclerViewMovies.layoutManager = layoutManager
        binding.recyclerViewMovies.addItemDecoration(itemDecoration)
        binding.recyclerViewMovies.adapter = MoviesAdapter(this, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.extras != null) {
            presenter.onCreate(intent.getStringExtra(EXTRA_URL),
                    intent.getStringExtra(EXTRA_TITLE),
                    intent.getStringExtra(EXTRA_DATE))
        } else {
            presenter.onCreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_refresh -> {
                presenter.onRefreshButtonClick()
                true
            }
            R.id.menu_item_about_us -> {
                presenter.onAboutUsButtonClick()
                true
            }
            else ->
                false
        }
    }

    override fun onRefresh() {
        presenter.onRefreshButtonClick()
    }

    override fun showTitle(moviesCount: Int) {
        val spannableString =
                if (moviesCount < 1)
                    SpannableString(getString(R.string.title_activity_main))
                else
                    SpannableString(getString(R.string.title_activity_main) + " (" + moviesCount + ")")
        spannableString.setSpan(TypefaceSpan("montserrat_regular.ttf"), 0, spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        supportActionBar?.title = spannableString
    }

    override fun showWifiRequestDialog(okCancelDialogListener: OkCancelDialogListener) {
        DialogManager.showOkCancelDialog(this, R.string.warning_no_internet_connection, okCancelDialogListener)
    }

    override fun showTimeOutDialog() {
        DialogManager.showSimpleDialog(this, R.string.timeout_dialog_message
        ) { finish() }
    }

    override fun showNoEventsDialog() {
        DialogManager.showSimpleDialog(this, R.string.warning_no_films_recived
        ) { finish() }
    }

    override fun stopRefreshing() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showChangeLog() {
        //La clase ChangeLog muestra los cambios en la ultima versión
        val changeLog = ChangeLog(this)
        if (changeLog.isUpdated())
            changeLog.logDialog.show()
    }

    override fun navigateToDetail(url: String, title: String, date: String) =
            DetailActivity.open(this, url, title, date)

    override fun showAboutUs() {
        val acercade = Intent(this, com.vsa.filmoteca.about.presentation.view.AboutActivity::class.java)
        startActivity(acercade)
    }

    override fun setMovies(movies: List<MovieViewModel>) {
        (binding.recyclerViewMovies.adapter as MoviesAdapter).update(movies)
    }

    override fun showWifiSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
    }


    override fun onMovieClick(position: Int) {
        presenter.onMovieRowClick(position)
    }

    override fun updateWidget() {
        val ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(ComponentName(this, EventsWidgetProvider::class.java))
        sendBroadcast(Intent(this, EventsWidgetProvider::class.java)
                .apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                })
    }

}

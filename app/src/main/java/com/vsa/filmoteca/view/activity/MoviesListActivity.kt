package com.vsa.filmoteca.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.vsa.filmoteca.R
import com.vsa.filmoteca.internal.di.component.ApplicationComponent
import com.vsa.filmoteca.internal.di.module.ActivityModule
import com.vsa.filmoteca.presentation.movieslist.MoviesListPresenter
import com.vsa.filmoteca.presentation.utils.ChangeLog
import com.vsa.filmoteca.view.MoviesListView
import com.vsa.filmoteca.view.adapter.EventDataProvider
import com.vsa.filmoteca.view.adapter.EventsAdapter
import com.vsa.filmoteca.view.dialog.DialogManager
import com.vsa.filmoteca.view.dialog.ProgressDialogManager
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MoviesListActivity : BaseActivity(), MoviesListView, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Inject
    lateinit var presenter: MoviesListPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initializePresenter()
        onNewIntent(intent)
    }

    override fun initializeInjector(applicationComponent: ApplicationComponent) {
        applicationComponent
                .plusActivityComponent(ActivityModule(this))
                .inject(this)
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
        when (item.itemId) {
            R.id.menu_item_refresh -> {
                presenter.onRefreshMenuButtonClick()
                return true
            }
            R.id.menu_item_about_us -> {
                presenter.onAboutUsButtonClick()
                return true
            }
        }
        return false
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }

    override fun showTitle(moviesCount: Int) {
        if (moviesCount < 1)
            supportActionBar!!.setTitle(R.string.title_activity_main)
        else
            supportActionBar!!.title = getString(R.string.title_activity_main) + " (" + moviesCount + ")"
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

    override fun showProgressDialog() {
        ProgressDialogManager.showProgressDialog(this, R.string.loading)
    }

    override fun hideProgressDialog() {
        ProgressDialogManager.hideProgressDialog()
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showChangeLog() {
        //La clase ChangeLog muestra los cambios en la ultima versión
        val cl = ChangeLog(this)
        if (cl.firstRun())
            cl.logDialog.show()
    }

    override fun navigateToDetail(url: String, title: String, date: String) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra(DetailActivity.EXTRA_URL, url)
        i.putExtra(DetailActivity.EXTRA_TITLE, title)
        i.putExtra(DetailActivity.EXTRA_DATE, date)
        startActivity(i)
    }


    override fun showAboutUs() {
        val acercade = Intent(this, AboutActivity::class.java)
        startActivity(acercade)
    }

    override fun showWifiSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
    }

    override fun setMovies(dataProvider: EventDataProvider) {
        val eventsAdapter = EventsAdapter(this, dataProvider)
        listViewMovies.adapter = eventsAdapter
    }

    override fun isListLoaded(): Boolean {
        return listViewMovies.adapter != null
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        presenter.onMovieRowClick(position)
    }

    private fun initViews() {
        showTitle(0)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary)
        listViewMovies.onItemClickListener = this
    }

    private fun initializePresenter() {
        presenter.setView(this)
    }

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

}

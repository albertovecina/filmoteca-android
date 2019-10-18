package com.vsa.filmoteca.view.activity

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.vsa.filmoteca.R
import com.vsa.filmoteca.internal.di.component.ApplicationComponent
import com.vsa.filmoteca.internal.di.module.ActivityModule
import com.vsa.filmoteca.presentation.detail.DetailPresenter
import com.vsa.filmoteca.view.DetailView
import com.vsa.filmoteca.view.dialog.DialogManager
import com.vsa.filmoteca.view.dialog.ProgressDialogManager
import com.vsa.filmoteca.view.widget.EventsWidget
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailView, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_URL = "extra_url"

        fun open(context: Context, url: String, title: String, date: String) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_DATE, date)
            })
        }
    }

    @Inject
    lateinit var presenter: DetailPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initializePresenter()
        presenter.onCreate(intent.getStringExtra(EXTRA_URL),
                intent.getStringExtra(EXTRA_TITLE))

    }

    override fun initializeInjector(applicationComponent: ApplicationComponent) {
        applicationComponent
                .plusActivityComponent(ActivityModule(this))
                .inject(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        presenter.onCreate(intent.getStringExtra(EXTRA_URL),
                intent.getStringExtra(EXTRA_TITLE))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_share -> {
                presenter.onShareButtonClick()
                return true
            }
            R.id.menu_item_browser -> {
                presenter.onShowInBrowserButtonClick()
                return true
            }
            R.id.menu_item_filmaffinity -> {
                presenter.onFilmAffinitySearchButtonClick()
                return true
            }
            R.id.menu_item_refresh -> {
                presenter.onRefresh()
                return true
            }
            R.id.menu_item_about_us -> {
                presenter.onAboutUsButtonClick()
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return false
        }
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }

    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(R.color.color_primary_dark,
                R.color.color_accent,
                R.color.color_primary)
        fabComments.setOnClickListener { presenter.onFabClick() }
        webviewMoviePage.onScrollChangedCallback = { _, t, _, oldt ->
            swipeRefreshLayout.isEnabled = t == 0
            if (t < oldt && fabComments.visibility != View.VISIBLE)
                fabComments.show()
        }
        webviewMoviePage.onOverScrollListener = { fabComments.hide() }
        showMovieTitle(intent.getStringExtra(EXTRA_TITLE))

    }

    override fun showContent() {
        wrapperContent.visibility = View.VISIBLE
    }

    override fun hideContent() {
        wrapperContent.visibility = View.GONE
    }

    override fun showProgressDialog() {
        ProgressDialogManager.showProgressDialog(this, R.string.loading)
    }

    override fun hideProgressDialog() {
        ProgressDialogManager.hideProgressDialog()
    }

    override fun showMovieTitle(title: String) {
        textViewTitle.text = title
    }

    override fun updateWidget() {
        val intent = Intent(this, EventsWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        val ids = intArrayOf(R.xml.appwidget_info)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun launchBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun showErrorNoInternet() {
        DialogManager.showSimpleDialog(this, R.string.error_no_internet) { dialog -> dialog.dismiss() }
    }

    override fun showShareDialog() {
        val titleShareButton = intent.getStringExtra(EXTRA_TITLE)
        val dateShareButton = getString(R.string.share_date) + ": " + intent.getStringExtra(EXTRA_DATE).substring(1)
        val infoShareButton = getString(R.string.share_message) + " " + titleShareButton + "\n" + dateShareButton
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        intent.putExtra(Intent.EXTRA_TEXT, infoShareButton)

        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    override fun showTimeOutDialog() {
        DialogManager.showSimpleDialog(this, R.string.timeout_dialog_message) { finish() }
    }

    override fun setWebViewContent(html: String, baseUrl: String) {
        webviewMoviePage.loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8",
                "about:blank")
    }

    override fun navigateToComments(title: String) {
        //TODO
    }

    override fun showAboutUs() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun initializePresenter() {
        presenter.view = this
    }

}

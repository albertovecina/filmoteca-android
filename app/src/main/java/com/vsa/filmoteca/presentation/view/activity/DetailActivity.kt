package com.vsa.filmoteca.presentation.view.activity

import android.appwidget.AppWidgetManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vsa.filmoteca.R
import com.vsa.filmoteca.about.presentation.view.AboutDialog
import com.vsa.filmoteca.databinding.ActivityDetailBinding
import com.vsa.filmoteca.presentation.presenter.detail.DetailPresenter
import com.vsa.filmoteca.presentation.view.DetailView
import com.vsa.filmoteca.presentation.view.dialog.DialogManager
import com.vsa.filmoteca.presentation.view.widget.EventsWidgetProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
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

    private lateinit var binding: ActivityDetailBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        presenter.onCreate(
            intent.getStringExtra(EXTRA_URL) ?: "",
            intent.getStringExtra(EXTRA_TITLE) ?: ""
        )

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        presenter.onCreate(
            intent.getStringExtra(EXTRA_URL) ?: "",
            intent.getStringExtra(EXTRA_TITLE) ?: ""
        )
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
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.color_primary_dark,
            R.color.color_accent,
            R.color.color_primary
        )
        binding.webviewMoviePage.onScrollChangedCallback = { _, top, _, _ ->
            binding.swipeRefreshLayout.isEnabled = top == 0
        }
    }

    override fun showContent() {
        binding.wrapperContent.visibility = View.VISIBLE
    }

    override fun hideContent() {
        binding.wrapperContent.visibility = View.GONE
    }

    override fun showMovieTitle(title: String) {
        binding.textViewTitle.text = title
    }

    override fun updateWidget() {
        val intent = Intent(this, EventsWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        val ids = intArrayOf(R.xml.appwidget_info)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }

    override fun stopRefreshing() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun launchBrowser(url: String) {
        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, R.string.no_browser_found, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showErrorNoInternet() {
        DialogManager.showSimpleDialog(
            this,
            R.string.error_no_internet
        ) { dialog -> dialog.dismiss() }
    }

    override fun showShareDialog() {
        val titleShareButton = intent.getStringExtra(EXTRA_TITLE)
        val dateShareButton =
            getString(R.string.share_date) + ": " + intent.getStringExtra(EXTRA_DATE)?.substring(1)
        val infoShareButton =
            getString(R.string.share_message) + " " + titleShareButton + "\n" + dateShareButton
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
        binding.webviewMoviePage.loadDataWithBaseURL(
            baseUrl, html, "text/html", "utf-8",
            "about:blank"
        )
    }

    override fun showAboutUs() {
        AboutDialog.show(supportFragmentManager)
    }

}

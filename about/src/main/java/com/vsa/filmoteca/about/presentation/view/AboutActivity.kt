package com.vsa.filmoteca.about.presentation.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.PackageInfoCompat
import com.vsa.filmoteca.about.R
import com.vsa.filmoteca.about.databinding.ActivityAboutUsBinding
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutActivity : AppCompatActivity(), AboutView, View.OnClickListener {

    private lateinit var binding: ActivityAboutUsBinding

    @Inject
    lateinit var presenter: AboutPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.wrapperContent.setOnClickListener(this)
        try {
            val packageManager = this.packageManager
            val packageInfo =
                packageManager.getPackageInfo(this.packageName, PackageManager.GET_META_DATA)
            binding.textViewAboutUsBuild.text = String.format(
                getString(R.string.version_code),
                PackageInfoCompat.getLongVersionCode(packageInfo)
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        presenter.create()
    }

    override fun onClick(v: View) {
        if (v !== binding.wrapperAboutUsDialog)
            finish()
    }

}

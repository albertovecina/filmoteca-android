package com.vsa.filmoteca.presentation.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.PackageInfoCompat
import com.vsa.filmoteca.R
import com.vsa.filmoteca.databinding.ActivityAboutUsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAboutUsBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.wrapperContent.setOnClickListener(this)
        try {
            val packageManager = this.packageManager
            val packageInfo = packageManager.getPackageInfo(this.packageName, PackageManager.GET_META_DATA)
            binding.textViewAboutUsBuild.text = String.format(getString(R.string.version_code), PackageInfoCompat.getLongVersionCode(packageInfo))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun onClick(v: View) {
        if (v !== binding.wrapperAboutUsDialog)
            finish()
    }

}

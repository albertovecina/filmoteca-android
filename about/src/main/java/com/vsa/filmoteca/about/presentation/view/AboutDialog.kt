package com.vsa.filmoteca.about.presentation.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.pm.PackageInfoCompat
import androidx.fragment.app.FragmentManager
import com.vsa.filmoteca.about.R
import com.vsa.filmoteca.about.databinding.FragmentAboutBinding
import com.vsa.filmoteca.about.presentation.presenter.AboutPresenter
import com.vsa.filmoteca.core.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutDialog : BaseDialogFragment<FragmentAboutBinding>(), AboutView {

    companion object {

        private const val TAG = "AboutDialog"

        fun show(fragmentManager: FragmentManager) {
            AboutDialog().show(fragmentManager, TAG)
        }

    }


    @Inject
    lateinit var presenter: AboutPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.create()
        showVersionCode()
    }

    private fun initViews() {
        binding.wrapperContent.setOnClickListener { dismiss() }
    }

    private fun showVersionCode() {
        try {
            val packageManager = requireActivity().packageManager
            val packageInfo =
                packageManager.getPackageInfo(
                    requireActivity().packageName,
                    PackageManager.GET_META_DATA
                )
            binding.textViewAboutUsBuild.text =
                getString(R.string.version_code, PackageInfoCompat.getLongVersionCode(packageInfo))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

}

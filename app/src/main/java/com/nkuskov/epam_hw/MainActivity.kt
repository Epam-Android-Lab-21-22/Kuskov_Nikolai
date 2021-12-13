package com.nkuskov.epam_hw

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.nkuskov.epam_hw.Extensions.Companion.setClickableAndEnabled
import com.nkuskov.epam_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.goButton.setClickableAndEnabled(
            InternetConnectionLiveData.getInternetStatus(
                getSystemService(
                    Context.CONNECTIVITY_SERVICE
                ) as ConnectivityManager
            )
        )

        InternetConnectionLiveData(this).observe(this, {
            onInternetConnectionChanged(it, binding)
        })

        binding.goButton.setOnClickListener {
            startActivity(Intent(this, FutureActivity::class.java))
        }
    }

    private fun onInternetConnectionChanged(
        internetConnectionStatus: Boolean,
        binding: ActivityMainBinding
    ) {
        binding.goButton.setClickableAndEnabled(internetConnectionStatus)
        if (!internetConnectionStatus) Snackbar.make(
            binding.root,
            getString(R.string.no_internet_text),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(
            BUTTON_ENABLED,
            binding.goButton.isEnabled && binding.goButton.isClickable
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.goButton.setClickableAndEnabled(
            savedInstanceState.getBoolean(
                BUTTON_ENABLED,
                false
            )
        )
    }

    companion object {
        const val BUTTON_ENABLED = "BUTTON_ENABLED"
    }
}
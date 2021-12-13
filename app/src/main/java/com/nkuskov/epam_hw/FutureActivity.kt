package com.nkuskov.epam_hw

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.nkuskov.epam_hw.databinding.ActivityFutureBinding
import com.nkuskov.epam_hw.Enums.Companion.CheckPermissionResult
import com.nkuskov.epam_hw.Extensions.Companion.setClickableAndEnabled


class FutureActivity : AppCompatActivity() {
    private val binding: ActivityFutureBinding by lazy {
        ActivityFutureBinding.inflate(
            layoutInflater
        )
    }

    private val requestPermissionDialog =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                binding.futureButton.setClickableAndEnabled(true)
                launchCamera()
            } else {
                failedGracefully()
                binding.futureButton.setClickableAndEnabled(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.futureButton.setOnClickListener {
            handleCheckResult(checkPermission())
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.futureButton.setClickableAndEnabled(
            when (checkPermission()) {
                CheckPermissionResult.GRANTED -> true
                else -> false
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                when {
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        binding.futureButton.setClickableAndEnabled(true)
                        launchCamera()
                    }
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
                        ) {
                            showGoToSettingsDialog()
                        } else failedGracefully()
                        binding.futureButton.setClickableAndEnabled(false)
                    }
                }
            }
        }
    }

    private fun checkPermission(): CheckPermissionResult {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> CheckPermissionResult.GRANTED

            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> CheckPermissionResult.GRANTED

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                android.Manifest.permission.CAMERA
            ) -> CheckPermissionResult.NEED_TO_EXPLAIN

            else -> CheckPermissionResult.NEED_TO_REQUEST
        }
    }

    private fun handleCheckResult(result: CheckPermissionResult) {
        when (result) {
            CheckPermissionResult.GRANTED -> launchCamera()
            CheckPermissionResult.DENIED -> failedGracefully()
            CheckPermissionResult.NEED_TO_REQUEST -> askForCameraPermission()
            CheckPermissionResult.NEED_TO_EXPLAIN -> showPermissionExplanation()
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_title))
            .setMessage(getString(R.string.camera_permission_explanation_text))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> askForCameraPermission() }
            .show()
    }

    private fun askForCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        else
            requestPermissionDialog.launch(android.Manifest.permission.CAMERA)
    }

    private fun failedGracefully() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_title))
            .setMessage(getString(R.string.rejected_camera_permission_text))
            .setPositiveButton(getString(R.string.on_change_mind_text)) { _, _ ->
                askForCameraPermission()
            }
            .setNegativeButton(getString(R.string.ok), null)
            .show()
    }

    private fun showGoToSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.rejected_camera_permission_title))
            .setMessage(getString(R.string.rejected_camera_permission_with_not_ask_again))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    this.data = Uri.fromParts(PACKAGE_URI_SCHEME, packageName, null)
                })
            }
            .setNegativeButton(getString(R.string.ok), null)
            .show()
    }

    private fun launchCamera() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
            startActivity(Intent(CAMERA_INTENT))
        else
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.camera_not_allowed_title))
                .setMessage(getString(R.string.camera_not_allowed_text))
                .setPositiveButton(getString(R.string.ok), null)
                .show()
    }

    companion object {
        private const val CAMERA_INTENT = "android.media.action.IMAGE_CAPTURE"
        private const val CAMERA_PERMISSION_CODE = 777
        private const val PACKAGE_URI_SCHEME = "package"
    }
}
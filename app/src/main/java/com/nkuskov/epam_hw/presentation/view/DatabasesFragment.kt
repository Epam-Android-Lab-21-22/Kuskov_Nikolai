package com.nkuskov.epam_hw.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.data.database.shared_pref.SharedPref
import com.nkuskov.epam_hw.presentation.view_model.shared_pref.SharedPrefViewModel
import com.nkuskov.epam_hw.presentation.view_model.shared_pref.SharedPrefViewModelFactory
import com.nkuskov.epam_hw.databinding.FragmentDatabasesBinding
import com.nkuskov.epam_hw.presentation.view_model.database.DatabaseViewModel
import com.nkuskov.epam_hw.presentation.view_model.database.DatabaseViewModelFactory
import com.nkuskov.epam_hw.presentation.view_model.external_storage.ExternalStorageViewModel
import com.nkuskov.epam_hw.presentation.view_model.external_storage.ExternalStorageViewModelFactory
import com.nkuskov.epam_hw.presentation.view_model.internal_storage.InternalStorageViewModel
import com.nkuskov.epam_hw.presentation.view_model.internal_storage.InternalStorageViewModelFactory
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.nkuskov.epam_hw.presentation.common.Enums.Companion.CheckPermissionResult
import java.lang.Exception

class DatabasesFragment : Fragment(R.layout.fragment_databases) {
    private val sharedPrefViewModel: SharedPrefViewModel by activityViewModels {
        SharedPrefViewModelFactory(
            requireActivity().getSharedPreferences(
                SharedPref.SHARED_PREF_NAME,
                AppCompatActivity.MODE_PRIVATE
            )!!
        )
    }
    private val databaseViewModel: DatabaseViewModel by activityViewModels {
        DatabaseViewModelFactory(
            requireActivity()
        )
    }
    private val internalStorageViewModel: InternalStorageViewModel by activityViewModels {
        InternalStorageViewModelFactory(
            requireActivity()
        )
    }
    private val externalStorageViewModel: ExternalStorageViewModel by activityViewModels {
        ExternalStorageViewModelFactory(
            requireActivity()
        )
    }

    private var _binding: FragmentDatabasesBinding? = null

    private val binding get() = _binding!!

    private val textInputNotEmpty
        get() = binding.textInputLayout.editText?.text.toString().isNotEmpty()
    private val textInputValue get() = binding.textInputLayout.editText?.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatabasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSharedPrefCallbacks()
        initDatabaseCallbacks()
        initInternalStorageCallbacks()
        initExternalStorageCallbacks()
    }

    private fun initSharedPrefCallbacks() {
        sharedPrefViewModel.textData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.sharedPrefWriteData.setOnClickListener {
            if (textInputNotEmpty)
                sharedPrefViewModel.writeData(textInputValue)
        }

        binding.sharedPrefReadData.setOnClickListener {
            sharedPrefViewModel.readData()
        }
    }

    private fun initDatabaseCallbacks() {
        databaseViewModel.textData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                Toast.makeText(activity, it, LENGTH_LONG).show()
            }
        }

        binding.databaseWriteData.setOnClickListener {
            if (textInputNotEmpty)
                databaseViewModel.writeData(textInputValue)
        }

        binding.databaseReadData.setOnClickListener {
            databaseViewModel.readData()
        }
    }

    private fun initInternalStorageCallbacks() {
        internalStorageViewModel.textData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.internalStorageWriteData.setOnClickListener {
            if (textInputNotEmpty)
                internalStorageViewModel.writeData(textInputValue)
        }

        binding.internalStorageReadData.setOnClickListener {
            try {
                internalStorageViewModel.readData()
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun initExternalStorageCallbacks() {
        externalStorageViewModel.textData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.externalStorageWriteData.setOnClickListener {
            if (textInputNotEmpty)
                handleCheckResult(checkPermission()) {
                    writeDataToExternalStorage()
                }

        }

        binding.externalStorageReadData.setOnClickListener {
            handleCheckResult(checkPermission()) {
                externalStorageViewModel.readData()
            }
        }
    }

    private fun writeDataToExternalStorage(){
        externalStorageViewModel.writeData(
            textInputValue
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            EXTERNAL_STORAGE_PERMISSION_CODE -> {
                when {
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        writeDataToExternalStorage()
                    }
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            !shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ) {
                            showGoToSettingsDialog()
                        } else failedGracefully()
                    }
                }
            }
        }
    }

    private val requestPermissionDialog =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                writeDataToExternalStorage()
            } else {
                failedGracefully()
            }
        }

    private fun checkPermission(): CheckPermissionResult {
        return when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> CheckPermissionResult.GRANTED

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> CheckPermissionResult.NEED_TO_EXPLAIN

            else -> CheckPermissionResult.NEED_TO_REQUEST
        }
    }

    private fun handleCheckResult(result: CheckPermissionResult, onSuccessAction: () -> Unit) {
        when (result) {
            CheckPermissionResult.GRANTED -> onSuccessAction()
            CheckPermissionResult.DENIED -> failedGracefully()
            CheckPermissionResult.NEED_TO_REQUEST -> askForExternalStoragePermission()
            CheckPermissionResult.NEED_TO_EXPLAIN -> showPermissionExplanation()
        }
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.permission_title))
            .setMessage(getString(R.string.external_storage_permission_explanation_text))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> askForExternalStoragePermission() }
            .show()
    }

    private fun askForExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                EXTERNAL_STORAGE_PERMISSION_CODE
            )
        else
            requestPermissionDialog.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun failedGracefully() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.permission_title))
            .setMessage(getString(R.string.rejected_external_storage_permission_text))
            .setPositiveButton(getString(R.string.on_change_mind_text)) { _, _ ->
                askForExternalStoragePermission()
            }
            .setNegativeButton(getString(R.string.ok), null)
            .show()
    }

    private fun showGoToSettingsDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.rejected_external_storage_permission_title))
            .setMessage(getString(R.string.rejected_external_storage_permission_with_not_ask_again))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    this.data =
                        Uri.fromParts(PACKAGE_URI_SCHEME, requireActivity().packageName, null)
                })
            }
            .setNegativeButton(getString(R.string.ok), null)
            .show()
    }

    companion object {
        private const val EXTERNAL_STORAGE_PERMISSION_CODE = 777
        private const val PACKAGE_URI_SCHEME = "package"
    }
}
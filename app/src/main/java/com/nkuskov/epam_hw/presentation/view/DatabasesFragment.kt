package com.nkuskov.epam_hw.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.activityViewModels
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.databinding.FragmentDatabasesBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.nkuskov.epam_hw.presentation.common.Enums.Companion.CheckPermissionResult
import com.nkuskov.epam_hw.presentation.view_model.DatabaseFragmentViewModel
import com.nkuskov.epam_hw.presentation.view_model.DatabaseFragmentViewModelFactory
import java.lang.Exception

class DatabasesFragment : Fragment(R.layout.fragment_databases) {
    private val databaseFragmentViewModel: DatabaseFragmentViewModel by activityViewModels {
        DatabaseFragmentViewModelFactory(
            requireActivity().applicationContext
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
        initSQLDatabaseCallbacks()
        initInternalStorageCallbacks()
        initExternalStorageCallbacks()
    }

    private fun initSharedPrefCallbacks() {
        databaseFragmentViewModel.sharedPrefTextData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.sharedPrefWriteData.setOnClickListener {
            if (textInputNotEmpty)
                databaseFragmentViewModel.writeDataToSharedPref(textInputValue)
        }

        binding.sharedPrefReadData.setOnClickListener {
            databaseFragmentViewModel.readDataFromSharedPref()
        }
    }

    private fun initSQLDatabaseCallbacks() {
        databaseFragmentViewModel.sqlDatabaseTextData.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                Toast.makeText(activity, it, LENGTH_LONG).show()
            }
        }

        binding.databaseWriteData.setOnClickListener {
            if (textInputNotEmpty)
                try {
                    databaseFragmentViewModel.writeDataToSQLDatabase(textInputValue)
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, LENGTH_LONG).show()
                }
        }

        binding.databaseReadData.setOnClickListener {
            try {
                databaseFragmentViewModel.readDataFromSQLDatabase()
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, LENGTH_LONG).show()
            }
        }
    }

    private fun initInternalStorageCallbacks() {
        databaseFragmentViewModel.internalStorageTextData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.internalStorageWriteData.setOnClickListener {
            if (textInputNotEmpty)
                try {
                    databaseFragmentViewModel.writeDataToInternalStorage(textInputValue)
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, LENGTH_LONG).show()
                }
        }

        binding.internalStorageReadData.setOnClickListener {
            try {
                databaseFragmentViewModel.readDataFromInternalStorage()
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, LENGTH_LONG).show()
            }
        }
    }

    private fun initExternalStorageCallbacks() {
        databaseFragmentViewModel.externalStorageTextData.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, LENGTH_LONG).show()
        }

        binding.externalStorageWriteData.setOnClickListener {
            if (textInputNotEmpty)
                handleCheckResult(checkPermission()) {
                    try {
                        writeDataToExternalStorage()
                    } catch (e: Exception) {
                        Toast.makeText(activity, e.message, LENGTH_LONG).show()
                    }
                }

        }

        binding.externalStorageReadData.setOnClickListener {
            handleCheckResult(checkPermission()) {
                try {
                    databaseFragmentViewModel.readDataFromExternalStorage()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, LENGTH_LONG).show()
                }
            }
        }
    }

    private fun writeDataToExternalStorage(){
        try {
            databaseFragmentViewModel.writeDataToExternalStorage(
                textInputValue
            )
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, LENGTH_LONG).show()
        }
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
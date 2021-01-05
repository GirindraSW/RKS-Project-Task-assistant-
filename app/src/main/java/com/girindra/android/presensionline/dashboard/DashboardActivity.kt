package com.girindra.android.presensionline.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.girindra.android.presensionline.R
import com.girindra.android.presensionline.databinding.ActivityDashboardBinding
import com.girindra.android.presensionline.databinding.PresenceLayoutBinding
import com.girindra.android.presensionline.databinding.SuccessDialogBinding
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    private var binding: ActivityDashboardBinding? = null
    private var dialogBinding: PresenceLayoutBinding? = null
    private var bindingSuccess: SuccessDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val nameExtras = intent.getStringExtra("name")
        binding?.name?.text = nameExtras

        binding?.logout?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnPresence?.setOnClickListener {
            dialogBinding = PresenceLayoutBinding.inflate(layoutInflater)
            bindingSuccess = SuccessDialogBinding.inflate(layoutInflater)
            val alertDialogView = LayoutInflater.from(this).inflate(R.layout.presence_layout, null)
            val builder = AlertDialog.Builder(this)
                .setView(alertDialogView)
                .setTitle("Your Presence")
            builder.setView(dialogBinding?.root)
            val alertDialog = builder.show()
            val arrayMaterial = resources.getStringArray(R.array.spinner_subject)
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayMaterial)
            dialogBinding?.spinner?.adapter = adapter

            dialogBinding?.btnDate?.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, "DATE")
            }
            dialogBinding?.btnTime?.setOnClickListener {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, "TIME")
            }
            dialogBinding?.btnAdd?.setOnClickListener {
                alertDialog.dismiss()
                val alertDialogViewSuccess = LayoutInflater.from(this).inflate(R.layout.success_dialog, null)
                val successBuilder = AlertDialog.Builder(this)
                    .setView(alertDialogViewSuccess)
                    .setTitle("Success!")
                successBuilder.setView(bindingSuccess?.root)
                val successDialog = successBuilder.show()
                bindingSuccess?.btnOk?.setOnClickListener {
                    successDialog.dismiss()
                    bindingSuccess = null
                }
                dialogBinding = null
            }
            dialogBinding?.btnCancel?.setOnClickListener {
                alertDialog.dismiss()
                bindingSuccess = null
                dialogBinding = null
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dialogBinding?.tvDate?.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        dialogBinding?.tvTime?.text = dateFormat.format(calendar.time)
    }
    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Do you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            this.finishAffinity()
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
        }
        alertDialogBuilder.show()
    }
}
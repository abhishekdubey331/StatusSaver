package com.technogeeks.statussaver.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.technogeeks.statussaver.app.databinding.ActivityBaseBinding
import com.technogeeks.statussaver.library.FactorialCalculator
import com.technogeeks.statussaver.library.android.NotificationUtil

class BaseActivity : AppCompatActivity() {

    private val notificationUtil: NotificationUtil by lazy { NotificationUtil(this) }
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCompute.setOnClickListener {
            val input = binding.editTextFactorial.text.toString().toInt()
            val result = FactorialCalculator.computeFactorial(input).toString()

            binding.textResult.text = result
            binding.textResult.visibility = View.VISIBLE

            notificationUtil.showNotification(
                context = this,
                title = getString(R.string.notification_title),
                message = result
            )
        }
    }
}

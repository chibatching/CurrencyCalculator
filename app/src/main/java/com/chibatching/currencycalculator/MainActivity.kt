package com.chibatching.currencycalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.chibatching.currencycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.currencyCalcResults.observe(this) { results ->
            if (results?.isEmpty() == true) {
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
                return@observe
            }
            binding.recyclerView.withModels {
                results?.forEach { currencyCalcResult ->
                    listItemCurrencyResult {
                        id(currencyCalcResult.currencyCode)
                        currencyResult(currencyCalcResult)
                        onBind { _, view, _ ->
                            view.dataBinding.lifecycleOwner = this@MainActivity
                        }
                        onUnbind { _, view ->
                            view.dataBinding.lifecycleOwner = null
                        }
                    }
                }
            }
        }
    }
}

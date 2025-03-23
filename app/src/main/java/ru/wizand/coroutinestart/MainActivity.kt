package ru.wizand.coroutinestart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.wizand.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity {
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }
//        loadCity { city -> // Изменил название параметра it на city для наглядности
//            runOnUiThread { // Переключаемся на главный поток для обновления UI
//                binding.tvLocation.text = city
//                loadTemperature(city) { temperature -> // Изменил название параметра it на temperature для наглядности
//                    runOnUiThread { // Переключаемся на главный поток для обновления UI
//                        binding.tvTemperature.text = temperature.toString()
//                        binding.progress.isVisible = false
//                        binding.buttonLoad.isEnabled = true
//                    }
//                }
//            }
//        }
//    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
//            Handler(Looper.getMainLooper()).post { // ТОЖЕ САМОЕ ЧТО И НИЖЕ СТРОЧКА
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
//        thread {
//            Thread.sleep(5000)
//            runOnUiThread { // Переключаемся на главный поток перед вызовом callback
//                callback.invoke("Moscow")
//            }
//        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke(17)
            }
        }
    }
//        thread {
//            runOnUiThread { // Переключаемся на главный поток для Toast
//                Toast.makeText(
//                    this@MainActivity, // Используйте this@MainActivity для контекста в лямбде
//                    getString(R.string.loading_temperature_toast, city),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            Thread.sleep(5000)
////            runOnUiThread { // Переключаемся на главный поток перед вызовом callback
//                callback.invoke(17)
//            }
//        }
//    }
}
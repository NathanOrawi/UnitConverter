package au.edu.jcu.my.unitconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.edu.jcu.my.unitconverter.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.calculateButton.setOnClickListener{ calculateConversion() }
        }
    private fun calculateConversion() {
        val stringInTextField = binding.unitValueToConvert.text.toString()
        val unitValue = stringInTextField.toDoubleOrNull()
        if (unitValue == null) {
            binding.conversionAmount.text = ""
            return
        }

        // Changes the text of the radio buttons based on the round up switch
        if (binding.roundUpSwitch.isChecked) {
            binding.optionCelsiusFahrenheit.text = getString(R.string.fahrenheit_to_celsius)
            binding.optionDegreeRadians.text = getString(R.string.degrees_to_radians)
            binding.optionKilometerMiles.text = getString(R.string.miles_to_kilometers)
        } else {
            binding.optionCelsiusFahrenheit.text = getString(R.string.celsius_to_fahrenheit)
            binding.optionDegreeRadians.text = getString(R.string.radians_to_degrees)
            binding.optionKilometerMiles.text = getString(R.string.kilometers_to_miles)
        }

        // Converts units based on the selected radio button, and the manner of conversion based on the round up switch
        val conversionPercentage: Double = when (binding.conversionOptions.checkedRadioButtonId) {
            // celsius to fahrenheit conversion
            R.id.option_celsius_fahrenheit -> if (binding.roundUpSwitch.isChecked) {
                unitValue * 9 / 5 + 32
            } else {
                (unitValue - 32) * 5 / 9
            }
            // radians to degrees conversion
            R.id.option_degree_radians -> if (binding.roundUpSwitch.isChecked) {
                unitValue * (180 / Math.PI)
            } else {
                unitValue * (Math.PI / 180)
            }
            // kilometers to miles conversion
            else -> if (binding.roundUpSwitch.isChecked) {
                unitValue / 1.60934
            } else {
                unitValue * 1.60934
            }
        }

        val formattedConversion = NumberFormat.getInstance().format(conversionPercentage)
        binding.conversionAmount.text = getString(R.string.conversion_amount, formattedConversion)

    }
}
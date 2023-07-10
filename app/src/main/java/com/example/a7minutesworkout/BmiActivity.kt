package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    private var binding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity )
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener{
            if(validateMetricUnits()){
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue*heightValue)

                displayBmiResult(bmi)

            }else{
                Toast.makeText(this@BmiActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_LONG).show()
            }


        }

    }

    private fun displayBmiResult(bmi : Float){

        val bmiLabel : String
        val bmiDescription : String

        if(bmi <= 15f){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! Your really need to take care of yourself! eat more!"
        } else if(bmi <= 16f){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! Your really need to take care of yourself! eat more!"
        } else if(bmi <= 18.5f){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! Your really need to take care of yourself! eat more!"
        }else if(bmi <= 25f){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in good shape"
        }else if(bmi <= 30f){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! Your really need to take care of yourself! Workout more!"
        }else if(bmi <= 35f){
            bmiLabel = "Obese Class || (Moderately Obese)"
            bmiDescription = "Oops! Your really need to take care of yourself! Workout more!"
        } else if(bmi <= 40f){
            bmiLabel = "Obese Class || (Severely Obese)"
            bmiDescription = "Oops! You are in a very serious condition! Act now!"
        }else{
            bmiLabel = "Obese Class || (Very Severely Obese)"
            bmiDescription = "Oops! You are in a very serious condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBmiResult?.visibility = View.VISIBLE
        binding?.tvBmiValue?.text = bmiValue
        binding?.tvBmiType?.text = bmiLabel
        binding?.tvBmiDescription?.text = bmiDescription



    }
    private fun validateMetricUnits() : Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid

    }
}
package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView : String = METRIC_UNITS_VIEW

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

        makeVisibleMetricUnitsView() //setting the metric view as the default
        binding?.rgUnits?.setOnCheckedChangeListener{_, checkedId : Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener{
            if(currentVisibleView == METRIC_UNITS_VIEW && validateMetricUnits()){
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue*heightValue)

                displayBmiResult(bmi)

            }else if(currentVisibleView == US_UNITS_VIEW && validateUsUnits()){
                val weightValue : Float = binding?.etUsUnitWeight?.text.toString().toFloat()
                val heightValueFeet : Float = binding?.etUsUnitFeet?.text.toString().toFloat() * 12 //converting to inces
                val heightValueInches : Float = binding?.etUsUnitInches?.text.toString().toFloat()
                val heightValue = heightValueFeet + heightValueInches

                val bmi = weightValue / (heightValue*heightValue) * 703

                displayBmiResult(bmi)

            }else{
                Toast.makeText(this@BmiActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_LONG).show()
            }


        }

    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.llMetricUnits?.visibility = View.VISIBLE
        binding?.llUsUnits?.visibility = View.INVISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.llMetricUnits?.visibility = View.INVISIBLE
        binding?.llUsUnits?.visibility = View.VISIBLE

        binding?.etUsUnitFeet?.text!!.clear()
        binding?.etUsUnitInches?.text!!.clear()
        binding?.etUsUnitWeight?.text!!.clear()

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
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
    private fun validateUsUnits() : Boolean{
        var isValid = true

        if(binding?.etUsUnitFeet?.text.toString().isEmpty()) {
            isValid = false
        }else if(binding?.etUsUnitInches?.text.toString().isEmpty()){
                isValid = false
        }else if(binding?.etUsUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
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
}
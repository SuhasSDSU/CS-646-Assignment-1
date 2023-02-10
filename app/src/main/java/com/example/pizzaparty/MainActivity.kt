package com.example.pizzaparty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungrySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"On create Invoked")
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)
        howHungrySpinner = findViewById(R.id.hungry_spinner)
    }

    fun calculateClick(view: View) {
        // Get the text that was typed into the EditText
        val numAttendStr = numAttendEditText.text.toString()

        // Convert the text into an integer
        val numAttend = numAttendStr.toIntOrNull() ?: 0

        val spinner = findViewById<Spinner>(R.id.hungry_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.hungry_array, android.R.layout.simple_spinner_dropdown_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter;

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                Toast.makeText(this@MainActivity, item, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Get hunger level selection
         val hungerLevel = when (howHungrySpinner.getSelectedItemPosition()) {
            R.id.hungry_spinner -> PizzaCalculator.HungerLevel.LIGHT
            R.id.hungry_spinner -> PizzaCalculator.HungerLevel.MEDIUM
            else -> PizzaCalculator.HungerLevel.RAVENOUS
         };

        // Get the number of pizzas needed
         val calc = PizzaCalculator(numAttend, hungerLevel)
         val totalPizzas = calc.totalPizzas

//         Place totalPizzas into the string resource and display
         val totalText = getString(R.string.total_pizzas, totalPizzas)

         numPizzasTextView.setText(totalText)
    }
}
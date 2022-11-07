package com.miu.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_quiz.*
import java.text.SimpleDateFormat
import java.util.*

class QuizActivity : AppCompatActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        btnReset.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == btnReset) {
            resetClickAction()
        } else if (view == btnSubmit) {
            submitClickAction()
        }
    }

    private fun resetClickAction() {
        radioGroup.clearCheck()
        checkboxes.children.forEach { it as CheckBox
            it.isChecked = false
        }
    }

    private fun submitClickAction() {
        val selectedRadio = radioGroup.checkedRadioButtonId
        val selectedCheckBoxes = checkboxes.children.filterIsInstance<CheckBox>().filter { it.isChecked }

        if (selectedRadio != -1 && selectedCheckBoxes.count() > 0) {
            var score = 0
            if (selectedRadio == R.id.secondRadio)
                score += 50

            if (selectedCheckBoxes.count() == 1 && selectedCheckBoxes.first() == firstCheckBox)
                score += 50


            showResult(score)

        } else {
            Toast.makeText(this, "Please answer all the questions.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showResult(score: Int) {
        val currentDateTime = Date()
        val datetimeFormatter = SimpleDateFormat("dd/M/yyyy hh:mm")
        val dateTime = datetimeFormatter.format(currentDateTime)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Your Result")
        builder.setMessage("Congratulations! You submitted on $dateTime. You achieved $score%.")
        builder.setPositiveButton("Got it") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("Reset") { dialogInterface, _ ->
            resetClickAction()
            dialogInterface.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
package com.example.mediconnectapk

import com.example.mediconnectapk.Constants.OPEN_GOOGLE
import com.example.mediconnectapk.Constants.OPEN_SEARCH
import java.lang.Exception
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "How may I assist you?"
                    2 -> "Hi!"
                    else -> "error" }
            }

            message.contains("how to manage anxiety") -> {
                when (random) {
                    0 -> "Be active."
                    1 -> "Take control..."
                    2 -> "Connect with people."
                    3-> "Have some \"me time\""
                    else -> "error"
                }
            }

            message.contains("flu symptoms") -> {
                when (random) {
                    0 -> "fever* or feeling feverish/chills."
                    1 -> "cough."
                    2 -> "runny or stuffy nose."
                    else -> "error"
                }
            }
            message.contains("how to deal with stomachache") -> {
                when (random) {
                    0 -> "Water"
                    1 -> "Reduce alcohol consumption"
                    2 -> "Eat rice, bananas or toast"
                    else -> "error"
                }
            }

            message.contains("when should I see the doctor") -> {
                when (random) {
                    0 -> "You Have a Persistent, High Fever"
                    1 -> "Your Cold Becomes Unusually Bad"
                    2 -> "You're Short of Breath."
                    else -> "error"
                }
            }
            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Please ask google for further answers"
                    else -> "error"
                }
            }
        }
    }
}
package id.rizmaulana.composetimer.util

object Formatter {

    fun getTimerFormat(timeInMilies: Long): String {
        val timeInSec = timeInMilies.div(1000)
        val minutes = (timeInSec % 3600) / 60;
        val second = timeInSec % 60;
        val milSec = timeInMilies % 60;
        return String.format("%02d:%02d:%02d", minutes, second, milSec)

    }
}
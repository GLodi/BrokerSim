package giuliolodi.brokersim.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import giuliolodi.brokersim.R
import java.math.BigInteger
import java.security.SecureRandom
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object {

        fun showLoadingDialog(context: Context): ProgressDialog {
            val progressDialog = ProgressDialog(context)
            progressDialog.show()
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.setIndeterminate(true)
            progressDialog.setCancelable(true)
            progressDialog.setCanceledOnTouchOutside(false)
            return progressDialog
        }

        fun getDate(): String {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
            val date: Date = Date()
            return dateFormat.format(date)
        }

        fun getRandomString(): String {
            val random: SecureRandom = SecureRandom()
            return BigInteger(130, random).toString(32)
        }

    }

}
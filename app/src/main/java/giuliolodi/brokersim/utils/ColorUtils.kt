package giuliolodi.brokersim.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import giuliolodi.brokersim.R

class ColorUtils(context: Context) {

    var t: Int = 0
    
    var mContext = context
    
    fun getRandomColor(): Int {
        val colorList: IntArray = intArrayOf(
                ContextCompat.getColor(mContext, R.color.brown),
                ContextCompat.getColor(mContext, R.color.deepOrange),
                ContextCompat.getColor(mContext, R.color.orange),
                ContextCompat.getColor(mContext, R.color.amber),
                ContextCompat.getColor(mContext, R.color.lime),
                ContextCompat.getColor(mContext, R.color.lightGreen),
                ContextCompat.getColor(mContext, R.color.green),
                ContextCompat.getColor(mContext, R.color.teal),
                ContextCompat.getColor(mContext, R.color.cyan),
                ContextCompat.getColor(mContext, R.color.lightBlue),
                ContextCompat.getColor(mContext, R.color.blue),
                ContextCompat.getColor(mContext, R.color.indigo),
                ContextCompat.getColor(mContext, R.color.deepPurple),
                ContextCompat.getColor(mContext, R.color.purple),
                ContextCompat.getColor(mContext, R.color.pink),
                ContextCompat.getColor(mContext, R.color.red))
        t = colorList[(Math.random() * colorList.size).toInt()]
        return t
    }

    fun getRandomDarkColor(): Int {
        when(t) {
            ContextCompat.getColor(mContext, R.color.brown) -> return ContextCompat.getColor(mContext, R.color.brownDark)
            ContextCompat.getColor(mContext, R.color.deepOrange) -> return ContextCompat.getColor(mContext, R.color.deepOrangeDark)
            ContextCompat.getColor(mContext, R.color.orange) -> return ContextCompat.getColor(mContext, R.color.orangeDark)
            ContextCompat.getColor(mContext, R.color.amber) -> return ContextCompat.getColor(mContext, R.color.amberDark)
            ContextCompat.getColor(mContext, R.color.lime) -> return ContextCompat.getColor(mContext, R.color.limeDark)
            ContextCompat.getColor(mContext, R.color.lightGreen) -> return ContextCompat.getColor(mContext, R.color.lightGreenDark)
            ContextCompat.getColor(mContext, R.color.green) -> return ContextCompat.getColor(mContext, R.color.greenDark)
            ContextCompat.getColor(mContext, R.color.teal) -> return ContextCompat.getColor(mContext, R.color.tealDark)
            ContextCompat.getColor(mContext, R.color.cyan) -> return ContextCompat.getColor(mContext, R.color.cyanDark)
            ContextCompat.getColor(mContext, R.color.lightBlue) -> return ContextCompat.getColor(mContext, R.color.lightBlueDark)
            ContextCompat.getColor(mContext, R.color.blue) -> return ContextCompat.getColor(mContext, R.color.blueDark)
            ContextCompat.getColor(mContext, R.color.indigo) -> return ContextCompat.getColor(mContext, R.color.indigoDark)
            ContextCompat.getColor(mContext, R.color.deepPurple) -> return ContextCompat.getColor(mContext, R.color.deepPurpleDark)
            ContextCompat.getColor(mContext, R.color.purple) -> return ContextCompat.getColor(mContext, R.color.purpleDark)
            ContextCompat.getColor(mContext, R.color.pink) -> return ContextCompat.getColor(mContext, R.color.pinkDark)
            ContextCompat.getColor(mContext, R.color.red) -> return ContextCompat.getColor(mContext, R.color.redDark)
        }
        return 0
    }

}
package giuliolodi.financegame.utils

import android.content.Context
import giuliolodi.financegame.R

class ColorUtils(context: Context) {

    var t: Int = 0
    
    var mContext = context
    
    fun getRandomColor(): Int {
        val colorList: IntArray = intArrayOf(
                mContext.resources.getColor(R.color.brown),
                mContext.resources.getColor(R.color.deepOrange),
                mContext.resources.getColor(R.color.orange),
                mContext.resources.getColor(R.color.amber),
                mContext.resources.getColor(R.color.lime),
                mContext.resources.getColor(R.color.lightGreen),
                mContext.resources.getColor(R.color.green),
                mContext.resources.getColor(R.color.teal),
                mContext.resources.getColor(R.color.cyan),
                mContext.resources.getColor(R.color.lightBlue),
                mContext.resources.getColor(R.color.blue),
                mContext.resources.getColor(R.color.indigo),
                mContext.resources.getColor(R.color.deepPurple),
                mContext.resources.getColor(R.color.purple),
                mContext.resources.getColor(R.color.pink),
                mContext.resources.getColor(R.color.red))
        t = colorList[(Math.random() * colorList.size).toInt()]
        return t
    }

    fun getRandomDarkColor(): Int {
        when(t) {
            mContext.resources.getColor(R.color.brown) -> return mContext.resources.getColor(R.color.brownDark)
            mContext.resources.getColor(R.color.deepOrange) -> return mContext.resources.getColor(R.color.deepOrangeDark)
            mContext.resources.getColor(R.color.orange) -> return mContext.resources.getColor(R.color.orangeDark)
            mContext.resources.getColor(R.color.amber) -> return mContext.resources.getColor(R.color.amberDark)
            mContext.resources.getColor(R.color.lime) -> return mContext.resources.getColor(R.color.limeDark)
            mContext.resources.getColor(R.color.lightGreen) -> return mContext.resources.getColor(R.color.lightGreenDark)
            mContext.resources.getColor(R.color.green) -> return mContext.resources.getColor(R.color.greenDark)
            mContext.resources.getColor(R.color.teal) -> return mContext.resources.getColor(R.color.tealDark)
            mContext.resources.getColor(R.color.cyan) -> return mContext.resources.getColor(R.color.cyanDark)
            mContext.resources.getColor(R.color.lightBlue) -> return mContext.resources.getColor(R.color.lightBlueDark)
            mContext.resources.getColor(R.color.blue) -> return mContext.resources.getColor(R.color.blueDark)
            mContext.resources.getColor(R.color.indigo) -> return mContext.resources.getColor(R.color.indigoDark)
            mContext.resources.getColor(R.color.deepPurple) -> return mContext.resources.getColor(R.color.deepPurpleDark)
            mContext.resources.getColor(R.color.purple) -> return mContext.resources.getColor(R.color.purpleDark)
            mContext.resources.getColor(R.color.pink) -> return mContext.resources.getColor(R.color.pinkDark)
            mContext.resources.getColor(R.color.red) -> return mContext.resources.getColor(R.color.redDark)
        }
        return 0
    }

}
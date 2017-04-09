package giuliolodi.financegame.ui.assets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstechlab.easyfonts.EasyFonts
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_stock.view.*

class AssetsAdapter : RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    private var stockDbs: List<StockDb> = ArrayList()

    private val onClickSubject: PublishSubject<Int> = PublishSubject.create()

    // Expose PublishSubject for as onClickListener
    fun getPositionClicks(): Observable<Int> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stockDb: StockDb) = with(itemView) {
            item_stock_symbol.typeface = EasyFonts.robotoRegular(context)
            item_stock_name.typeface = EasyFonts.robotoRegular(context)
            item_stock_price.typeface = EasyFonts.robotoRegular(context)

            item_stock_symbol.text = stockDb.symbol
            item_stock_icon.letter = stockDb.symbol
            item_stock_name.text = stockDb.name
            item_stock_icon.shapeColor = stockDb.iconColor

            item_stock_price.text = "$" + String.format("%.2f", stockDb.price)
            val diff = stockDb.previousClose!!.minus(stockDb.price!!)
            item_stock_increase.text = String.format("%.2f", diff)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockDbs[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(position) }
    }

    override fun getItemCount(): Int {
        return stockDbs.size
    }

    fun addStocks(stocks: List<StockDb>) {
        stockDbs = stocks
        notifyDataSetChanged()
    }

}
package giuliolodi.financegame.ui.market

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstechlab.easyfonts.EasyFonts
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_market_stock.view.*

class MarketAdapter : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    private var stockList: MutableList<StockDb> = ArrayList()

    private val onClickSubject: PublishSubject<String> = PublishSubject.create()

    // Expose PublishSubject for as onClickListener
    fun getPositionClicks(): Observable<String> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stock: StockDb) = with(itemView) {
            item_market_stock_symbol.typeface = EasyFonts.robotoRegular(context)
            item_market_stock_name.typeface = EasyFonts.robotoRegular(context)
            item_market_stock_price.typeface = EasyFonts.robotoRegular(context)

            item_market_stock_symbol.text = stock.symbol
            item_market_stock_name.text = stock.name

            item_market_stock_price.text = "$" + String.format("%.2f", stock.price)
            val diff = stock.price!!.minus(stock.previousClose!!)
            item_market_stock_increase.text = String.format("%.2f", diff)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_market_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(stockList[position].symbol) }
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    fun addStocks(stocks: List<StockDb>) {
        stockList = stocks.toMutableList()
        notifyDataSetChanged()
    }

    fun addStockDb(stockDb: StockDb) {
        stockList.add(stockDb)
        notifyDataSetChanged()
    }

}
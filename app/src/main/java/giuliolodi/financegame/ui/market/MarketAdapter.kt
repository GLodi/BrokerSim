package giuliolodi.financegame.ui.market

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstechlab.easyfonts.EasyFonts
import giuliolodi.financegame.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_market_stock.view.*
import yahoofinance.Stock

class MarketAdapter : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    private var stockList: MutableList<Stock> = ArrayList()

    private val onClickSubject: PublishSubject<Int> = PublishSubject.create()

    // Expose PublishSubject for as onClickListener
    fun getPositionClicks(): Observable<Int> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stock: Stock) = with(itemView) {
            item_market_stock_symbol.typeface = EasyFonts.robotoRegular(context)
            item_market_stock_name.typeface = EasyFonts.robotoRegular(context)
            item_market_stock_price.typeface = EasyFonts.robotoRegular(context)

            item_market_stock_symbol.text = stock.symbol
            item_market_stock_name.text = stock.name

            item_market_stock_price.text = "$" + String.format("%.2f", stock.quote.price)
            val diff = stock.quote.previousClose!!.minus(stock.quote.price!!)
            item_market_stock_increase.text = String.format("%.2f", diff)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_market_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(position) }
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    fun addStocks(stocks: List<Stock>) {
        stockList = stocks.toMutableList()
        notifyDataSetChanged()
    }

    fun addStock(stock: Stock) {
        stockList.add(stock)
        notifyDataSetChanged()
    }

}
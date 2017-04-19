package giuliolodi.financegame.ui.market

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_market_stock.view.*
import yahoofinance.Stock

class MarketAdapter : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    private var mStockList: MutableList<Stock> = ArrayList()
    private val onClickSubject: PublishSubject<String> = PublishSubject.create()

    fun getPositionClicks(): Observable<String> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stock: Stock) = with(itemView) {
            item_market_stock_symbol.text = stock.symbol
            item_market_stock_name.text = stock.name
            item_market_stock_price.text = "$" + String.format("%.2f", stock.quote.price)
            item_market_stock_increase.text = String.format("%.2f", stock.quote.price!!.minus(stock.quote.previousClose!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_market_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mStockList[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(mStockList[position].symbol) }
    }

    override fun getItemCount(): Int {
        return mStockList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addStocks(stocks: List<Stock>) {
        mStockList = stocks.toMutableList()
        notifyDataSetChanged()
    }

}
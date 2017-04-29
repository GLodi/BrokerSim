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

class MarketAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mStockList: MutableList<Stock?> = ArrayList()
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

    class LoadingHolder(root: View) : RecyclerView.ViewHolder(root)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val root: RecyclerView.ViewHolder
        if (viewType == 1) {
            val view = (LayoutInflater.from(parent?.context).inflate(R.layout.item_market_stock, parent, false))
            root = ViewHolder(view)
        } else  {
            val view = (LayoutInflater.from(parent?.context).inflate(R.layout.item_loading, parent, false))
            root = LoadingHolder(view)
        }
        return root
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val stock = mStockList[position]!!
            holder.bind(stock)
            holder.itemView.setOnClickListener { onClickSubject.onNext(stock.symbol) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mStockList.get(position) != null) 1 else 0
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

    fun addLoading() {
        mStockList.add(null)
        notifyItemInserted(mStockList.size - 1)
    }

    fun addMoreStocks(stocks: List<Stock>) {
        val lastNull = mStockList.lastIndexOf(null)
        mStockList.removeAt(mStockList.lastIndexOf(null))
        mStockList.addAll(stocks)
        notifyDataSetChanged()
    }

}
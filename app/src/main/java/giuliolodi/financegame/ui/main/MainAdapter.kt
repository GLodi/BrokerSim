package giuliolodi.financegame.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import kotlinx.android.synthetic.main.item_stock.view.*
import yahoofinance.Stock

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var stocks: List<Stock> = ArrayList()

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stock: Stock) = with(itemView) {
            item_stock_name.text = stock.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun addStocks(stocks: List<Stock>) {
        this.stocks = stocks
        notifyDataSetChanged()
    }

}
package giuliolodi.financegame.ui.stock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDbBought
import kotlinx.android.synthetic.main.item_stock_activity.view.*
import yahoofinance.Stock

class StockAdapter : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    private var mStockDbList: MutableList<StockDbBought> = ArrayList()
    private var mCurrentStock: Stock? = null

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stockDbBought: StockDbBought, currentStock: Stock) = with(itemView) {
            item_stock_activity_price.text = "$" + String.format("%.2f", stockDbBought.priceWhenBought)
            item_stock_activity_amount.text = stockDbBought.amount.toString()
            item_stock_activity_profit.text = "$" + String.format("%.2f", currentStock.quote.price.toDouble().minus(stockDbBought.priceWhenBought!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock_activity, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mStockDbList[position], mCurrentStock!!)
    }

    override fun getItemCount(): Int {
        return mStockDbList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addStockDbBoughtList(stocks: List<StockDbBought>, currentStock: Stock) {
        mStockDbList = stocks.toMutableList()
        mCurrentStock = currentStock
        notifyDataSetChanged()
    }

}
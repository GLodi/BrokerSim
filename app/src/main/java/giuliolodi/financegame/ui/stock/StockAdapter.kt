package giuliolodi.financegame.ui.stock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import giuliolodi.financegame.R
import giuliolodi.financegame.models.SellRequest
import giuliolodi.financegame.models.StockDbBought
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_stock_activity.view.*
import yahoofinance.Stock

class StockAdapter : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    private var mStockDbBoughtList: MutableList<StockDbBought> = arrayListOf()
    private var mCurrentStock: Stock? = null
    private val onClickSubject: PublishSubject<SellRequest> = PublishSubject.create()

    fun getPositionClicks(): Observable<SellRequest> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stockDbBought: StockDbBought, currentStock: Stock) = with(itemView) {
            item_stock_activity_price.text = "Bought for: $${String.format("%.2f", stockDbBought.priceWhenBought)}"
            item_stock_activity_amount.text = stockDbBought.amount.toString()
            item_stock_activity_profit.text = "Profit: $${String.format("%.2f", currentStock.quote.price.toDouble().minus(stockDbBought.priceWhenBought!!))}"
            item_stock_activity_seekbar.progress = 0
            item_stock_activity_seekbar.max = stockDbBought.amount!!
            item_stock_activity_date.text = stockDbBought.dateBought
            item_stock_activity_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    item_stock_activity_selectedamount.text = p1.toString()
                    item_stock_activity_profit.text = "Profit: $${String.format("%.2f", currentStock.quote.price.toDouble().minus(stockDbBought.priceWhenBought!!) * p1)}"
                    if (p1 == 0) item_stock_activity_profit.text = "Profit: $${String.format("%.2f", currentStock.quote.price.toDouble().minus(stockDbBought.priceWhenBought!!))}"
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock_activity, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mStockDbBoughtList[position], mCurrentStock!!)
        holder.itemView.item_stock_activity_sellbutton.setOnClickListener {
            if (holder.itemView.item_stock_activity_seekbar.progress > 0) {
                if (mStockDbBoughtList.size == 1  && holder.itemView.item_stock_activity_seekbar.progress == mStockDbBoughtList[position].amount)
                    onClickSubject.onNext(SellRequest(mStockDbBoughtList[position], holder.itemView.item_stock_activity_seekbar.progress, mCurrentStock!!, true))
                else
                    onClickSubject.onNext(SellRequest(mStockDbBoughtList[position], holder.itemView.item_stock_activity_seekbar.progress, mCurrentStock!!))
            }
        }
    }

    fun addStockDbBoughtList(stocks: List<StockDbBought>, currentStock: Stock) {
        mStockDbBoughtList = stocks.toMutableList()
        mCurrentStock = currentStock
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int { return mStockDbBoughtList.size }

    override fun getItemId(position: Int): Long { return position.toLong() }

}
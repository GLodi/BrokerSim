package giuliolodi.financegame.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstechlab.easyfonts.EasyFonts
import giuliolodi.financegame.R
import giuliolodi.financegame.model.StockDb
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_stock.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var stocks: List<StockDb> = ArrayList()

    private val onClickSubject: PublishSubject<StockDb> = PublishSubject.create()

    fun getPositionClicks(): Observable<StockDb> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stockDb: StockDb) = with(itemView) {
            item_stock_symbol.typeface = EasyFonts.robotoRegular(context)
            item_stock_name.typeface = EasyFonts.robotoRegular(context)
            item_stock_currency.typeface = EasyFonts.robotoRegular(context)
            item_stock_price.typeface = EasyFonts.robotoRegular(context)

            item_stock_symbol.text = stockDb.symbol
            item_stock_icon.letter = stockDb.symbol
            item_stock_name.text = stockDb.name
            item_stock_currency.text = stockDb.currency
            item_stock_price.text = "Price: $" + stockDb.price.toString()
            if (stockDb.lastPrice != null) item_stock_lastprice.text = "Last Price: $" + stockDb.lastPrice.toString()
            item_stock_icon.shapeColor = stockDb.iconColor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stocks[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(stocks[position]) }
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun addStocks(stocks: List<StockDb>) {
        this.stocks = stocks
        notifyDataSetChanged()
    }

}
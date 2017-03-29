package giuliolodi.financegame.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstechlab.easyfonts.EasyFonts
import giuliolodi.financegame.R
import giuliolodi.financegame.model.StockDb
import kotlinx.android.synthetic.main.item_stock.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var stocks: List<StockDb> = ArrayList()

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stock: StockDb) = with(itemView) {
            item_stock_symbol.typeface = EasyFonts.robotoRegular(context)
            item_stock_name.typeface = EasyFonts.robotoRegular(context)
            item_stock_currency.typeface = EasyFonts.robotoRegular(context)

            item_stock_symbol.text = stock.symbol
            item_stock_icon.letter = stock.symbol
            item_stock_name.text = stock.name
            item_stock_currency.text = stock.currency
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

    fun addStocks(stocks: List<StockDb>) {
        this.stocks = stocks
        notifyDataSetChanged()
    }

}
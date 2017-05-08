package giuliolodi.brokersim.data.api

import android.content.Context
import giuliolodi.brokersim.di.scope.AppContext
import io.reactivex.Observable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import yahoofinance.Stock
import yahoofinance.YahooFinance
import javax.inject.Inject

class ApiHelperImpl : ApiHelper {

    private val mContext: Context

    @Inject
    constructor(@AppContext context: Context) {
        mContext = context
    }

    override fun downloadStock(stockSymbol: String): Observable<Stock> {
        return Observable.defer { Observable.just(YahooFinance.get(stockSymbol)) }
    }

    override fun downloadStockList(stockList: Array<String>): Observable<Map<String, Stock>> {
        return Observable.defer { Observable.just(YahooFinance.get(stockList)) }
    }

    override fun downloadActiveStockSymbols(): Observable<List<String>> {
        return Observable.defer {
            val symbols: MutableList<String> = arrayListOf()
            val elStrings: MutableList<String> = arrayListOf()
            val doc: Document = Jsoup.connect("http://online.wsj.com/mdc/public/page/2_3021-activnyse-actives.html").get()
            val tags: Elements? = doc.getElementsByTag("tr")
            val elements: MutableList<Elements> = arrayListOf()
            if (tags != null) {
                for (tag in tags) {
                    if (!tag.getElementsByTag("a").isEmpty()) {
                        elements.add(tag.getElementsByTag("a"))
                        elStrings.add(elements.get(elements.lastIndex).toString())
                        symbols.add(elStrings.get(elStrings.lastIndex).substring(elStrings.get(elStrings.lastIndex).lastIndexOf("(") + 1, elStrings.get(elStrings.lastIndex).lastIndexOf(")")))
                    }
                }
            }
            Observable.just(symbols.toList())
        }
    }

}
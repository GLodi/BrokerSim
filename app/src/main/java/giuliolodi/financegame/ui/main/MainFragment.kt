package giuliolodi.financegame.ui.main

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.ui.base.BaseFragment

class MainFragment : BaseFragment(), MainContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.main_fragment, container, false)



        return view
    }

    override fun showContent() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}
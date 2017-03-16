package giuliolodi.financegame.ui.base

interface BaseContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun isNetworkAvailable(): Boolean

    }

    interface Presenter<V: View> {

        fun onAttach(view: V)

        fun onDetach()

    }

}
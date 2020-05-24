package ibmro.ishqa.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>>
    : Fragment(), BaseView {

    protected abstract var presenter: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        presenter.attachView(this as V)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}
package ibmro.ishqa.dashboard

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseFragment
import ibmro.ishqa.dashboard.sectionVM.SectionVM
import ibmro.ishqa.dashboard.sections.SectionAdapter
import kotlinx.android.synthetic.main.content_dashboard.*

class DashboardFragment : BaseFragment<DashboardContract.View,
        DashboardContract.Presenter>(),
        DashboardContract.View {
    lateinit override var presenter: DashboardContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DashboardPresenter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.content_dashboard, container, false)
        presenter.loadSectionRepo()
        return rootView
    }

    override fun replaceAdapterContent(sections: List<SectionVM>) {
        setupRecycler(sections)
    }

    private fun setupRecycler(listOfSection: List<SectionVM>) {
        dashboard_recycler.layoutManager = LinearLayoutManager(context)
        dashboard_recycler.adapter = SectionAdapter(listOfSection)
        dashboard_recycler.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}





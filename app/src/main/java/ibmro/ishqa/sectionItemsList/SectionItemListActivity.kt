package ibmro.ishqa.sectionItemsList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ibmro.common.androidCommon.KEY_TITLE_SECTION
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM
import kotlinx.android.synthetic.main.activity_item_list.*

class SectionItemListActivity : BaseActivity<SectionItemListContract.View,
        SectionItemListContract.Presenter>(),
        SectionItemListContract.View {


    lateinit override var presenter: SectionItemListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = SectionItemListPresenter(this, intent.getStringExtra(KEY_TITLE_SECTION))
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_item_list)
    }

    override fun replaceAdapterContent(secionItems: List<SectionItemVM>) {
        view_more_no_items.visibility = View.GONE
        view_more_list.layoutManager = LinearLayoutManager(this)
        view_more_list.adapter = SectionItemListAdapter(secionItems)
        view_more_list.setHasFixedSize(true)
    }

    override fun showNoItems() {
        view_more_no_items.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}


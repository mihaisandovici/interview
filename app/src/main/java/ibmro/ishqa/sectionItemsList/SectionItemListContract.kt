package ibmro.ishqa.sectionItemsList

import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView
import ibmro.ishqa.dashboard.sectionVM.SectionItemVM

object SectionItemListContract {

    interface View : BaseView {
        fun replaceAdapterContent(secionItems: List<SectionItemVM>)

        fun showNoItems()

    }

    interface Presenter : BasePresenter<View> {
        fun loadSectionItemsList(tagName: String)
    }
}
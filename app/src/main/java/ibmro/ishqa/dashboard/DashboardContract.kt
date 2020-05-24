package ibmro.ishqa.dashboard

import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView
import ibmro.ishqa.dashboard.sectionVM.SectionVM

//interface SectionsView : BaseView{
//
//    fun replaceAdapterContents(sections: List<SectionVM>)
//
////    fun viewMoreClick()
////
////    fun itemClick()
//
//}

object DashboardContract {

    interface View : BaseView {
        fun replaceAdapterContent(sections: List<SectionVM>)

//        fun showMoreItems()

//        fun showItem()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSectionRepo()
    }

}
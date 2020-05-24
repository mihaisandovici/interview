package ibmro.ishqa.sectionItemsList

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.sections.GetSectionByTagInteractor
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.dashboard.sectionVM.SectionListMapper
import io.reactivex.disposables.CompositeDisposable

class SectionItemListPresenter(
        private var context: Context,
        private var tagName: String) : BasePresenterImpl<SectionItemListContract.View>(),
        SectionItemListContract.Presenter {

    private val disposable: CompositeDisposable

    init {
        disposable = CompositeDisposable()
    }

    override fun loadSectionItemsList(tagName: String) {
        disposable.add(GetSectionByTagInteractor()
                .invoke(tagName)
                .applySchedulers()
                .subscribe { section ->
                    val listSectionItem = SectionListMapper(context).map(listOf(section)).first().listSectionItem
                    if (listSectionItem.isEmpty()) {
                        view?.showNoItems()
                    } else
                        view?.replaceAdapterContent(listSectionItem)

                })
    }

    override fun attachView(view: SectionItemListContract.View) {
        super.attachView(view)
        loadSectionItemsList(tagName)
    }

}
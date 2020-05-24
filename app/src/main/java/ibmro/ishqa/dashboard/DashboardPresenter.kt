package ibmro.ishqa.dashboard

import android.content.Context
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.sections.GetAllSectionInteractor
import ibmro.ishqa.base.BasePresenterImpl
import ibmro.ishqa.dashboard.sectionVM.SectionListMapper
import io.reactivex.disposables.CompositeDisposable


class DashboardPresenter(private var context: Context) : BasePresenterImpl<DashboardContract.View>(),
        DashboardContract.Presenter {

    private val disposables: CompositeDisposable

    init {
        disposables = CompositeDisposable()
    }

    override fun loadSectionRepo() {
        disposables.add(GetAllSectionInteractor().invoke()
                .applySchedulers()
                .subscribe { list ->
                    view?.replaceAdapterContent(
                            SectionListMapper(context)
                                    .map(list))
                }
        )
    }
}
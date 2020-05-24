package ibmro.datanetworksource.interactors.sections

import ibmro.datanetworksource.repositories.SectionRepo
import ibmro.datanetworksource.repositories.entities.sections.Section
import io.reactivex.Single

class GetAllSectionInteractor()
    : () -> Single<List<Section>> {
    private val sectionRepo: SectionRepo = SectionRepo
    override fun invoke(): Single<List<Section>> =
            Single.just(
                    sectionRepo.getAllSection()
            )
}
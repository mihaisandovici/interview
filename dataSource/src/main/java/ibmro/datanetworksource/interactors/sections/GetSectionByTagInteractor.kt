package ibmro.datanetworksource.interactors.sections

import ibmro.datanetworksource.repositories.SectionRepo
import ibmro.datanetworksource.repositories.entities.sections.Section
import io.reactivex.Single

class GetSectionByTagInteractor() : (String) -> Single<Section> {
    private val sectionRepo = SectionRepo
    override fun invoke(tagName: String): Single<Section> =
            Single.just(sectionRepo.getMainSectionByTag(tagName))
}
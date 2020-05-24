package ibmro.ishqa.dashboard.sectionVM

import ibmro.datanetworksource.repositories.entities.Tag

data class SectionVM(
        var mainTag: Tag,
        var titleSection: String,
        var descriptionSection: String,
        var listSectionItem: List<SectionItemVM>
) {
//    fun from(section: Section): SectionVM =
//            SectionVM(section.mainTag,
//                    section.mainTag. )

}

data class SectionItemVM(
        var itemMainTag: Tag,
        var itemTag: Tag,
        var titleItem: String,
        var imageResource: Int
)
package ibmro.datanetworksource.repositories

import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.datanetworksource.repositories.entities.sections.MainSections
import ibmro.datanetworksource.repositories.entities.sections.Section
import ibmro.datanetworksource.repositories.entities.sections.SectionItem

object SectionRepo {

    fun getAllSection(): List<Section> =
            MainSections.values().map { mainSections ->
                Section(mainSections.getTag(),
                        mainSections.getSectionItems().map {
                            SectionItem(it)
                        })
            }

    fun getMainSectionByTag(mainTag: String): Section? {
        val tag = MainSections.getItemByString(mainTag)
        return Section(tag!!.getTag(), tag.getSectionItems().map { SectionItem(it) })
    }

    fun getSectionItem(itemTagName: String): SectionItem =
            SectionItem(Tag.getItemByString(itemTagName)!!)




//    fun getSectionItem(tagName: String, itemName: String): SectionItem? {
//        val tag = Tag.valueOf(tagName)
//        tag.getSectionItems().map {
//            if (it.toString() == itemName)
//                return SectionItem(tag, it)
//            else return null
//        }
//        return null
//    }

    //creez obiectele statice
// apelez call ur de rest API de la server
// logica de unde vin datele

}
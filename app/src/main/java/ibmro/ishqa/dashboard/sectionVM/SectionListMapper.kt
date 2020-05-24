package ibmro.ishqa.dashboard.sectionVM

import android.content.Context
import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.sections.Section

class SectionListMapper(private var context: Context)
    : ModelMapper<List<Section>, List<SectionVM>> {
    override fun map(input: List<Section>): List<SectionVM> =
            input.map { section ->
                SectionVM(
                        mainTag = section.mainTag,
                        titleSection = section.mainTag.getTitleString(context),
                        descriptionSection = section.mainTag.getDescribeResource(context),
                        listSectionItem = section.listItem.map { sectionItem ->
                            SectionItemVM(section.mainTag,
                                    sectionItem.tag,
                                    sectionItem.tag.getTitleString(context),
                                    sectionItem.tag.getImage())
                        }

                )
            }

}

class SectionMapper(private var context: Context)
    : ModelMapper<Section, SectionVM> {
    override fun map(input: Section): SectionVM =
            SectionVM(
                    mainTag = input.mainTag,
                    titleSection = input.mainTag.getTitleString(context),
                    descriptionSection = input.mainTag.getDescribeResource(context),
                    listSectionItem = input.listItem.map {
                        SectionItemVM(
                                input.mainTag,
                                it.tag,
                                it.tag.getTitleString(context),
                                it.tag.getImage()
                        )
                    }

            )
}
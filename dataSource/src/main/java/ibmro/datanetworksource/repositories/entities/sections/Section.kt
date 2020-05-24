package ibmro.datanetworksource.repositories.entities.sections

import ibmro.datanetworksource.repositories.entities.Tag

data class Section(
        // tag corect
        val mainTag: Tag,
        val listItem: List<SectionItem>
) {

}
package ibmro.ishqa.interview.interviewsList.tagsRecycler

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.Tag

class TagListMapper() :
        ModelMapper<List<String>, List<Tag?>> {

    override fun map(input: List<String>): List<Tag?> {
        val outputList = arrayListOf<Tag?>()

        input.map { tagString ->
            if (Tag.getItemByString(tagString) != null) {
                outputList.add(Tag.getItemByString(tagString))
            }
        }

        return outputList.toList()

    }
}
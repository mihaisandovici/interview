package ibmro.datanetworksource.repositories.entities

import java.io.Serializable

data class TagItem(val tag: Tag,
                   var checked: Boolean) : Serializable
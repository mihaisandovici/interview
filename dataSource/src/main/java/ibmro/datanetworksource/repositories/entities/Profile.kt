package ibmro.datanetworksource.repositories.entities

data class Profile(
        var profilePicturePath: String?,
        var name: String?,
        var tags: ArrayList<String?>
)
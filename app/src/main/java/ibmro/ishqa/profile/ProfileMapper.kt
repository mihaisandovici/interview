package ibmro.ishqa.profile

import ibmro.common.ModelMapper
import ibmro.datanetworksource.repositories.entities.Profile

class ProfileMapper :
        ModelMapper<ProfilePersistent, Profile> {
    override fun map(input: ProfilePersistent): Profile {

        return Profile(
                profilePicturePath = input.profilePicturePath,
                name = input.name,
                tags = input.tags
        )
    }
}

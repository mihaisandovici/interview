package ibmro.datanetworksource.interactors.profile

import ibmro.common.androidCommon.SharedPref
import ibmro.datanetworksource.repositories.ProfileRepository
import ibmro.datanetworksource.repositories.entities.Profile
import io.reactivex.Single

class ProfileInteractor
(
        private val profileRepository: ProfileRepository,
        val pref: SharedPref
        //val context : Context
)  {
     fun getProfile(): Single<Profile> =
            Single.just(
                    profileRepository.getProfile(pref)
            )

     fun saveProfile(profile:Profile): Single<Profile> =
            Single.just(profileRepository.saveProfile(pref,profile))
}
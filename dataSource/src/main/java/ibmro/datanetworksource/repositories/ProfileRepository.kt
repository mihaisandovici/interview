package ibmro.datanetworksource.repositories

import ibmro.datanetworksource.repositories.entities.Profile
import com.google.gson.Gson
import ibmro.common.androidCommon.SharedPref


object ProfileRepository {

    fun saveProfile(sharedPref: SharedPref, profile: Profile) : Profile {  //context : Context
        val gson = Gson()
        val jsonProfile = gson.toJson(profile)
        sharedPref.profile = jsonProfile
        return profile
    }


    fun getProfile(sharedPref: SharedPref) : Profile{
        var profile = Profile(null,"", arrayListOf())

        if(!sharedPref.profile.isEmpty()){
            val jsonProfile = sharedPref.profile
            val gson = Gson()
            profile = gson.fromJson (jsonProfile,Profile::class.java)
        }

        return profile
    }

}

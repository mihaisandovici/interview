package ibmro.ishqa.profile

import android.content.Context
import android.graphics.Bitmap
import ibmro.ishqa.base.BasePresenter
import ibmro.ishqa.base.BaseView

class ProfileContract {

    interface View : BaseView {


        fun displayProfileValues(profilePicPath: String?, name: String?, tags : ArrayList<String?>)

        fun setDefaultProfilePicture()

        fun setName(profileName: String)

        fun setImage(bitmap : Bitmap)

        fun addAdapter(tags: ArrayList<String?>)
    }

    interface Presenter : BasePresenter<View> {

        fun getTags() : ArrayList<String?>

        fun updateProfileName(name : String)

        fun displayProfile()

        fun saveProfilePicture(bitmap: Bitmap, applicationContext: Context)

        fun deleteProfilePicturePath()

        fun setTags(tags: ArrayList<String?>)

        fun saveProfile()

        fun getProfile()

        fun getImagePath() : String?

    }
}
package ibmro.ishqa.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ibmro.common.extension.applySchedulers
import ibmro.datanetworksource.interactors.profile.ProfileInteractor
import ibmro.datanetworksource.repositories.ProfileRepository
import ibmro.ishqa.App
import ibmro.ishqa.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.io.FileOutputStream

class ProfilePresenter : BasePresenterImpl<ProfileContract.View>(),
        ProfileContract.Presenter {


    private val disposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(view: ProfileContract.View) {
        super.attachView(view)
        getProfile()
        displayProfile()
    }

    override fun displayProfile() {
        if(ProfilePersistent.name!!.isEmpty()) {
            ProfilePersistent.name = ANONYMOUS
            view!!.setName(ANONYMOUS)
        }
        else
            view!!.setName(ProfilePersistent.name!!)
        
        if(ProfilePersistent.profilePicturePath!=null) {
            val bitmapProfilePicture = BitmapFactory.decodeFile(ProfilePersistent.profilePicturePath)
            if(bitmapProfilePicture!=null)
            view!!.setImage(bitmapProfilePicture)
        }
        view!!.addAdapter(ProfilePersistent.tags)

    }

    override fun getImagePath() : String?{
        return ProfilePersistent.profilePicturePath
    }

    override fun setTags(tags: ArrayList<String?>) {
        ProfilePersistent.tags=tags
        view?.addAdapter(tags)
    }

    override fun getTags(): ArrayList<String?> {
        return ProfilePersistent.tags
    }

    override fun deleteProfilePicturePath() {
        ProfilePersistent.profilePicturePath=null
        view!!.setDefaultProfilePicture()
    }

    override fun updateProfileName(name: String) {
        ProfilePersistent.name=name
        view!!.setName(name)
    }

    override fun saveProfilePicture(bitmap: Bitmap, applicationContext: Context) {
        try {
            ProfilePersistent.profilePicturePath=getAttachmentsDirectoryPath(applicationContext)
            val fos = FileOutputStream(getAttachmentsDirectoryPath(applicationContext))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            fos.close()

        } catch (e: Exception) {
            Log.e("SAVE_IMAGE", e.message, e)
        }
        view!!.setImage(bitmap)
    }

    private fun getAttachmentsDirectoryPath(applicationContext: Context): String {
        return applicationContext.filesDir.absolutePath + File.separator + LOCAL_DIRECTORY
    }


    override fun saveProfile() {
        disposable.add(ProfileInteractor(ProfileRepository, App.prefs)
            .saveProfile(ProfileMapper().map(ProfilePersistent))
            .applySchedulers()
            .subscribe())
    }

    override fun getProfile() {
        disposable.add(ProfileInteractor(ProfileRepository, App.prefs)
                .getProfile()
                .applySchedulers()
                .subscribe { profile ->
                    view?.displayProfileValues(profile.profilePicturePath,profile.name,profile.tags)
                }
        )
    }


}
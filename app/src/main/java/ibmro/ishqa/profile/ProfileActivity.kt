package ibmro.ishqa.profile


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import ibmro.common.extension.clickOn
import ibmro.ishqa.App
import ibmro.ishqa.MainActivity
import ibmro.ishqa.R
import ibmro.ishqa.base.BaseActivity
import ibmro.ishqa.selectTags.TagActivity
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.IOException


class ProfileActivity : BaseActivity<ProfileContract.View,
        ProfileContract.Presenter>(),
        ProfileContract.View {


    override lateinit var presenter: ProfileContract.Presenter
    private var setLayoutOnce: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = ProfilePresenter()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = PROFILE
        checkForCompletedProfileStep()
        setButtons()

    }

    override fun onStart() {
        super.onStart()
        setLayoutOnce = true

    }

    private fun setButtons() {
        addTagButton.clickOn {
            startTagActivity()
        }

        addPicture.clickOn {
            showAddDeleteDialog()
        }
        doneButton.clickOn {
            startMainActivity()
        }
        editButton.clickOn {
            editNameDialog()
        }
    }

    private fun startMainActivity() {
        App.prefs.completedProfileStep = true
        presenter.saveProfile()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun startTagActivity() {
        val i = Intent(this, TagActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList(PROFILE_TAGS, presenter.getTags())
        i.putExtras(bundle)
        startActivityForResult(i, RESULT_LOAD_TAGS)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        presenter.saveProfile()
    }


    private fun checkForCompletedProfileStep() {
        val sharedPref = App.prefs
        val completedProfileStep = sharedPref.completedProfileStep
        if (completedProfileStep) {
            doneButton.visibility = View.GONE
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        } else {
            doneButton.visibility = View.VISIBLE
        }
    }

    override fun displayProfileValues(profilePicPath: String?, name: String?, tags: ArrayList<String?>) {
        if (!setLayoutOnce) {
            ProfilePersistent.profilePicturePath = profilePicPath
            ProfilePersistent.name = name
            ProfilePersistent.tags = tags
            presenter.displayProfile()
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        presenter.saveProfile()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (App.prefs.completedOnboarding) {
            presenter.saveProfile()
            super.onBackPressed()
        }
    }

    override fun addAdapter(tags: ArrayList<String?>) {


            val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }

            profile_tag_recycler_view.apply {
                layoutManager = flexboxLayoutManager
                adapter = TagAdapterFlexbox(tags, true)
            }

    }


    private fun showAddDeleteDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Add new Profile Picture")
                { _, _ ->
                    choosePhotoFromGallery()
                }

                .setNegativeButton("Remove Profile Picture") { _, _ ->
                    presenter.deleteProfilePicturePath()

                }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMG) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmapProfilePicture = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    Toast.makeText(this@ProfileActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
                    presenter.saveProfilePicture(bitmapProfilePicture as Bitmap, applicationContext)
                    roundImage(bitmapProfilePicture)


                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@ProfileActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == RESULT_LOAD_TAGS) {
            if (resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val tags = bundle!!.getStringArrayList(PROFILE_TAG_RESULT) as ArrayList<String?>
                presenter.setTags(tags)

            }

        }
    }

    private fun roundImage(bitmapProfilePicture: Bitmap) {
        val dr = RoundedBitmapDrawableFactory.create(resources, bitmapProfilePicture)
        dr.cornerRadius = Math.max(bitmapProfilePicture.width, bitmapProfilePicture.height) / 2.0f
        profilePicture.setImageDrawable(dr)
    }


    private fun editNameDialog() {

        val li = LayoutInflater.from(this)
        val editNameView = li.inflate(R.layout.edit_name_dialog, null) as View

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(editNameView)
        val userInput = editNameView.findViewById(R.id.editTextDialogUserInput) as EditText
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK")
                { _, _ ->
                    presenter.updateProfileName(userInput.text.toString())
                }

                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    override fun setDefaultProfilePicture() {
        profilePicture.setImageResource(R.drawable.account)
    }

    override fun setName(profileName: String) {
        name.text = profileName
    }

    override fun setImage(bitmap: Bitmap) {
        profilePicture.setImageBitmap(bitmap)
        roundImage(bitmap)
    }
}

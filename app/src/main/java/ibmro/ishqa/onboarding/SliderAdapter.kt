package ibmro.ishqa.onboarding

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ibmro.ishqa.R

class SliderAdapter(private val mContext: Context, private val onboardingItems: Array<OnboardingItem>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = LayoutInflater.from(mContext)
        val layout = layoutInflater.inflate(R.layout.slide_layout, container, false) as ViewGroup

        val slideImageView = layout.findViewById(R.id.slide_image) as ImageView
        val slideHeading = layout.findViewById(R.id.slide_heading) as TextView
        val slideDescription = layout.findViewById(R.id.slide_desc) as TextView

        val onboardingItem = onboardingItems[position]
        slideImageView.setImageResource(onboardingItem.iconResId)
        slideHeading.text =mContext.resources.getString(onboardingItem.heading)
        slideDescription.text = mContext.resources.getString(onboardingItem.description)

        container.addView(layout)

        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return OnboardingItem.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}
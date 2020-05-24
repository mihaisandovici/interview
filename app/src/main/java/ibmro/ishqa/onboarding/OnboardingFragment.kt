package ibmro.ishqa.onboarding

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ibmro.ishqa.MainActivity
import ibmro.ishqa.R
import ibmro.ishqa.profile.ProfileActivity
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnboardingFragment : Fragment(), View.OnClickListener, ViewPager.OnPageChangeListener{

    private val indicatorList = mutableListOf<ImageView>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addIndicator()
        addAdapter()
        setListeners()
    }


     private fun addIndicator(){

         for (i in 0 until NUM_PAGES) {
                 val indicator = LayoutInflater.from(this.context).inflate(R.layout.page_indicator, indicator_container,false)
                 indicatorList.add(i,indicator as ImageView)
                 indicator_container.addView(indicator)
             }

        indicatorList[0].setImageResource(R.drawable.indicator_selected)
     }


    private fun addAdapter() {
        val sliderAdapter=SliderAdapter(this.context!!, OnboardingItem.values())
        slideViewPager.adapter=sliderAdapter
    }

    private fun setListeners(){
        start_now_btn.setOnClickListener(this)
        next_btn.setOnClickListener (this)
        finish_btn.setOnClickListener(this)
        slideViewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }
    override fun onPageSelected(position: Int) {

        if(position+1 == NUM_PAGES) {
            start_now_btn.visibility = View.GONE
            next_btn.visibility = View.GONE
            finish_btn.visibility = View.VISIBLE
        }
        else{
            start_now_btn.visibility = View.VISIBLE
            next_btn.visibility = View.VISIBLE
            finish_btn.visibility = View.GONE
        }

        if(indicatorList.size > 0) {
            indicatorList.forEach { it.setImageResource(R.drawable.indicator_unselected) }
            indicatorList[position].setImageResource(R.drawable.indicator_selected)
        }

    }

    override fun onClick(view: View?) {
        if(view!!.id==R.id.finish_btn || view.id==R.id.start_now_btn) {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        else if(view.id==R.id.next_btn){
            slideViewPager.currentItem = slideViewPager.currentItem + 1
            }
    }



}
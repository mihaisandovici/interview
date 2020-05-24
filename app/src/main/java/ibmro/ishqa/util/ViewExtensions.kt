package ibmro.ishqa.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.interview.interviewsList.tagsRecycler.TagAdapterFlexBox

fun RecyclerView.flexBox(context: Context, tagList: List<Tag?>, reverse: Boolean) {
    val flexboyLayoutManager = FlexboxLayoutManager(context).apply {
        flexWrap = FlexWrap.WRAP
        if (reverse) {
            flexDirection = FlexDirection.ROW_REVERSE
        } else
            flexDirection = FlexDirection.ROW
        alignItems = AlignItems.STRETCH
    }

    this.apply {
        layoutManager = flexboyLayoutManager
        adapter = TagAdapterFlexBox(tagList)
    }
}
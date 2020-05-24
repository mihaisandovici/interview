package ibmro.ishqa.dashboard.sectionVM

import android.content.Context
import ibmro.datanetworksource.repositories.entities.Tag
import ibmro.ishqa.R

fun Tag.getImage(): Int {
    when (this) {
        Tag.ManageInterviews -> return R.drawable.ic_interviews
        Tag.ISH -> return R.drawable.ic_ish
        Tag.Topics -> return R.drawable.ic_topic
        Tag.Learn -> return R.drawable.ic_learn
        Tag.Java -> return R.drawable.ic_java
        Tag.Kotlin -> return R.drawable.ic_kotlin
        Tag.Android -> return R.drawable.ic_android
        Tag.IOS -> return R.drawable.ic_ios
        Tag.Swift -> return R.drawable.ic_swift
        Tag.JavaScript -> return R.drawable.ic_javascript
        Tag.SQL -> return R.drawable.ic_database
        Tag.DataStructure -> return R.drawable.ic_datastructure
        Tag.DesignPatterns -> return R.drawable.ic_design_patterns
        Tag.Algorithms -> return R.drawable.ic_algorithms
        Tag.GIT -> return R.drawable.ic_git
        Tag.Mobile -> return R.drawable.ic_mobile
        Tag.Backend -> return R.drawable.ic_backend
        Tag.Modules -> return R.drawable.ic_modules
        Tag.FrontEnd -> return R.drawable.ic_frontend
        Tag.Support -> return R.drawable.ic_support
        Tag.Interviewers -> return R.drawable.ic_administrator
        Tag.Candidate -> return R.drawable.ic_candidate
        Tag.Interviews -> return R.drawable.ic_interviews
        else ->
            return 0
    }
}

fun Tag.getTitleString(context: Context): String {
    when (this) {
        Tag.Topics -> return context.getString(R.string.topics_items)
        Tag.ManageInterviews -> return context.getString(R.string.manage_interviews_items)
        Tag.Learn -> return context.getString(R.string.learn_items)
        Tag.ISH -> return context.getString(R.string.ish_items)
        Tag.Java -> return context.getString(R.string.java)
        Tag.Kotlin -> return context.getString(R.string.kotlin)
        Tag.Android -> return context.getString(R.string.Android)
        Tag.IOS -> return context.getString(R.string.ios)
        Tag.Swift -> return context.getString(R.string.swift)
        Tag.JavaScript -> return context.getString(R.string.java_script)
        Tag.SQL -> return context.getString(R.string.sql)
        Tag.DataStructure -> return context.getString(R.string.data_structure)
        Tag.DesignPatterns -> return context.getString(R.string.design_patterns)
        Tag.Algorithms -> return context.getString(R.string.algorithms)
        Tag.GIT -> return context.getString(R.string.git)
        Tag.Mobile -> return context.getString(R.string.mobile)
        Tag.Backend -> return context.getString(R.string.back_end)
        Tag.Modules -> return context.getString(R.string.modules)
        Tag.FrontEnd -> return context.getString(R.string.front_end)
        Tag.Support -> return context.getString(R.string.support)
        Tag.Interviewers -> return context.getString(R.string.interviewers)
        Tag.Candidate -> return context.getString(R.string.candidate)
        Tag.Interviews -> return context.getString(R.string.interviews)
    }
}

fun Tag.getDescribeResource(context: Context): String {
    when (this) {
        Tag.Topics -> return context.getString(R.string.topics_items_describe)
        Tag.ManageInterviews -> return context.getString(R.string.manage_interviews_items_describe)
        Tag.Learn -> return context.getString(R.string.learn_items_describe)
        Tag.ISH -> return context.getString(R.string.ish_items_describe)

        else -> return ""
    }
}




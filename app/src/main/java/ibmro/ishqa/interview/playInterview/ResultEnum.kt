package ibmro.ishqa.interview.playInterview

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import ibmro.ishqa.R

enum class ResultEnum(value: Int) {
    UNKNOW(0),
    STRONG_AGREE(1),
    AGREE(2),
    MEDIUM(3),
    DISAGREE(4),
    STRONG_DISAGREE(5);

    fun toString(context: Context): String {
        when (this) {
            UNKNOW -> return context.getString(R.string.unknown)
            STRONG_AGREE -> return context.getString(R.string.strongly_agree)
            AGREE -> return context.getString(R.string.agree)
            MEDIUM -> return context.getString(R.string.neither_agree_not_disagree)
            DISAGREE -> return context.getString(R.string.disagree)
            STRONG_DISAGREE -> return context.getString(R.string.strongly_disagree)
        }
    }

    fun getColor(context: Context): Int {
        when (this) {
            UNKNOW -> return ResourcesCompat.getColor(context.resources, R.color.colorAccent, null)
            STRONG_AGREE -> return ResourcesCompat.getColor(context.resources, R.color.strong_agree, null)
            AGREE -> return ResourcesCompat.getColor(context.resources, R.color.agree, null)
            MEDIUM -> return ResourcesCompat.getColor(context.resources, R.color.medium, null)
            DISAGREE -> return ResourcesCompat.getColor(context.resources, R.color.disagree, null)
            STRONG_DISAGREE -> return ResourcesCompat.getColor(context.resources, R.color.strong_disagree, null)
        }
    }

    companion object {
        fun getResultForSeekBar(value: Int): ResultEnum {
            when (value) {
                1 -> return ResultEnum.STRONG_DISAGREE
                2 -> return ResultEnum.DISAGREE
                3 -> return ResultEnum.MEDIUM
                4 -> return ResultEnum.AGREE
                5 -> return ResultEnum.STRONG_AGREE
                else -> return ResultEnum.UNKNOW
            }
        }

        fun getResultForDatabase(value: Int): ResultEnum {
            when (value) {
                1 -> return ResultEnum.STRONG_AGREE
                2 -> return ResultEnum.AGREE
                3 -> return ResultEnum.MEDIUM
                4 -> return ResultEnum.DISAGREE
                5 -> return ResultEnum.STRONG_DISAGREE
                else -> return ResultEnum.UNKNOW
            }
        }
    }

}
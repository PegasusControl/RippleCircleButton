package mx.com.pegasus.view_utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by angeeeld on 8/10/17.
 */
class ViewUtils {
    companion object {
        fun dpToPx(context: Context, dp: Int): Float = ((context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT) * dp)
    }
}
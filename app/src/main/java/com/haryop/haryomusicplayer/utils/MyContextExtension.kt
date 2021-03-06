package com.haryop.haryomusicplayer.utils


import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.haryop.haryomusicplayer.R
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.comingSoon(activity: Activity, msg: String = "") {
    val toast = Toast.makeText(activity, "COMING SOON${msg}", Toast.LENGTH_SHORT)
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//        val v = toast.view!!.findViewById<View>(R.id.message) as TextView
//        if (v != null) v.gravity = Gravity.CENTER
//    }
    toast.show()

}

fun Fragment.comingSoon(msg: String) {
    activity?.comingSoon(requireActivity(), msg)
}

fun Activity.comingSoon(msg: String) {
    comingSoon(this, msg)
}

fun Context.showToast(activity: Activity, msg: String = "") {
    val toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//        val v = toast.view!!.findViewById<View>(R.id.message) as TextView
//        if (v != null) v.gravity = Gravity.CENTER
//    }
    toast.show()

}

fun Fragment.showToast(msg: String) {
    activity?.showToast(requireActivity(), msg)
}

fun Activity.showToast(msg: String) {
    showToast(this, msg)
}

fun Context.setDate(publishedAt: String): String {
    var date: String = publishedAt
    try {
        //publishedAt = 2021-07-08T10:30:11+00:00
        if (publishedAt.contains("Z")) {
            publishedAt.replace("Z", "")
        }

        var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val newDate: Date = format.parse(publishedAt)
        format = SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm")
        date = format.format(newDate)

    } catch (e: Exception) {
        if (date.contains("Z")) {
            date.replace("Z", "")
        }

        date.replace("T", " ")
    }

    return date
}

fun Context.setImageGlide(imgUrl: String?, view: View, imageViewTarget: ImageView) {
    com.bumptech.glide.Glide.with(view)
        .load(imgUrl ?: "")
        .placeholder(R.mipmap.ic_launcher_round)
        .into(imageViewTarget)

}

fun Context.isInternetAvailable(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        //You can replace it with your name
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
        .isConnected
}
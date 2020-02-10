package com.github.ixiaow.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseDialogFragment : DialogFragment() {

    open fun show(manager: FragmentManager?) {
        if (manager == null || dialog?.isShowing == true) {
            return
        }
        super.show(manager, javaClass.simpleName)
    }

    override fun dismiss() {
        if (dialog?.isShowing != true) {
            return
        }
        super.dismiss()
    }
}


inline fun <reified T : BaseDialogFragment> FragmentManager.showDialog(block: T.() -> Unit): T {
    T::class.java.newInstance().apply(block).also {
        it.show(this)
        return it
    }
}

inline fun <reified T : BaseDialogFragment> FragmentManager.showDialog(): T {
    T::class.java.newInstance().also {
        it.show(this)
        return it
    }
}

inline fun <reified T : BaseDialogFragment> AppCompatActivity.showDialog(block: T.() -> Unit): T =
    supportFragmentManager.showDialog(block)

inline fun <reified T : BaseDialogFragment> AppCompatActivity.showDialog(): T =
    supportFragmentManager.showDialog()

inline fun <reified T:BaseDialogFragment> Fragment.showDialog(block: T.() -> Unit): T? =
    fragmentManager?.showDialog(block)

inline fun <reified T:BaseDialogFragment> Fragment.showDialog(): T? =
    fragmentManager?.showDialog()


package com.github.ixiaow.fragment

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.github.ixiaow.data.R

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.findFragmentById(@IdRes frameId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(frameId)
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}


inline fun <reified T : Fragment> AppCompatActivity.replaceFragmentInActivity(
        @IdRes id: Int = R.id.container
): T = findFragmentById(id) as? T?
        ?: T::class.java.newInstance().also {
            replaceFragmentInActivity(it, id)
        }


inline fun <reified T : Fragment> AppCompatActivity.replaceFragmentInActivity(
        @IdRes id: Int = R.id.container, block: () -> T
): T = findFragmentById(id) as? T?
        ?: block.invoke().also {
            replaceFragmentInActivity(it, id)
        }


/**
 * 切换到目标fragment
 *
 * @param fragment 目标fragment
 */
fun AppCompatActivity.switchFragment(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        //获取当前fragmentManager中的所有fragment
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (f in fragments) { //遍历所有fragment设置隐藏
            hide(f)
        }
        //判断当前目标fragment是否存在，并且没有被加入
        if (!fragments.contains(fragment) && !fragment.isAdded) {
            add(frameId, fragment)
        } else {
            show(fragment)
        }
    }
}

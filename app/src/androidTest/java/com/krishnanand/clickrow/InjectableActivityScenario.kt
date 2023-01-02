package com.krishnanand.clickrow

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.lifecycle.ActivityLifecycleCallback
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import java.io.Closeable

class InjectableActivityScenario<T : Activity>(private val activityClass: Class<T>) : AutoCloseable, Closeable {

    private var delegate: ActivityScenario<T>? = null

    private val activityInjectors = mutableListOf<ActivityInjector<out Activity>>()
    private val fragmentInjectors = mutableListOf<FragmentInjector<out Fragment>>()

    fun launch(startIntent: Intent? = null): InjectableActivityScenario<T> {
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(activityLifecycleObserver)
        delegate = if (startIntent != null) {
            ActivityScenario.launch(startIntent)
        } else {
            ActivityScenario.launch(activityClass)
        }
        return this
    }

    override fun close() {
        delegate?.close()
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(activityLifecycleObserver)
    }

    fun moveToState(newState: Lifecycle.State): InjectableActivityScenario<T> {
        val d =
            delegate ?: throw IllegalStateException("Cannot move to state $newState since the activity hasn't been launched.")
        d.moveToState(newState)
        return this
    }

    fun recreate(): InjectableActivityScenario<T> {
        val d = delegate ?: throw IllegalStateException("Cannot recreate the activity since it hasn't been launched.")
        d.recreate()
        return this
    }

    fun onActivity(action: (T) -> Unit): InjectableActivityScenario<T> {
        val d = delegate ?: throw IllegalStateException("Cannot run onActivity since the activity hasn't been launched.")
        d.onActivity(action)
        return this
    }

    fun runOnMainThread(action: (T) -> Unit) {
        val d = delegate ?: throw IllegalStateException("Cannot run onActivity since the activity hasn't been launched.")
        d.onActivity(action)
    }

    fun getResult(): Instrumentation.ActivityResult =
        delegate?.result ?: throw IllegalStateException("Cannot get result since activity hasn't been launched.")

    /**
     * Injects the target Activity using the supplied [injector].
     *
     * ```
     * activityTestRule.addActivityInjector {
     *   // this is the target Activity
     *   dependency = fakeDependency
     * }
     * ```
     */
    fun injectActivity(injector: T.() -> Unit) {
        activityInjectors.add(ActivityInjector(activityClass, injector))
    }

    fun <A : Activity> injectActivity(activityClass: Class<A>, injector: A.() -> Unit) {
        activityInjectors.add(ActivityInjector(activityClass, injector))
    }

    fun <F : Fragment> injectFragment(fragmentClass: Class<F>, injector: F.() -> Unit) {
        fragmentInjectors.add(FragmentInjector(fragmentClass, injector))
    }

    fun <F : Fragment> injectFragment(fragment: F, injector: F.() -> Unit) {
        fragmentInjectors.add(
            FragmentInjector(
                fragment::class.java,
                injector
            )
        )
    }

    private class ActivityInjector<A : Activity>(
        private val activityClass: Class<A>,
        private val injector: A.() -> Unit
    ) {
        fun inject(activity: Activity?): Boolean {
            if (activityClass.isInstance(activity)) {
                activityClass.cast(activity)!!.injector()
                return true
            }
            return false
        }
    }

    private class FragmentInjector<F : Fragment>(
        private val fragmentClass: Class<F>,
        private val injection: F.() -> Unit
    ) {
        fun inject(fragment: Fragment?): Boolean {
            if (fragmentClass.isInstance(fragment)) {
                fragmentClass.cast(fragment)!!.injection()
                return true
            }
            return false
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            fragmentInjectors.forEach {
                if (it.inject(f)) return
            }
        }
    }

    private val activityLifecycleObserver = object : ActivityLifecycleCallback {
        override fun onActivityLifecycleChanged(activity: Activity?, stage: Stage?) {
            when (stage) {
                Stage.PRE_ON_CREATE -> {
                    if (activity is FragmentActivity) {
                        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)
                    }
                    activityInjectors.forEach { if (it.inject(activity)) return }
                }
                Stage.DESTROYED -> {
                    if (activity is FragmentActivity) {
                        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
                    }
                }
                else -> {
                    // no op
                }
            }
        }
    }
}
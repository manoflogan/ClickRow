package com.krishnanand.clickrow

import android.app.Activity
import android.content.Intent
import org.junit.rules.ExternalResource

class InjectableScenarioRule<A: Activity> constructor(
    private val activityClass: Class<A>,
    private val initBlock: InjectableActivityScenario<A>.() -> Unit
): ExternalResource() {

    override fun before() {
        if (injectableActivityScenario != null) {
            close()
        }
        injectableActivityScenario = InjectableActivityScenario(activityClass).apply(
            initBlock
        )
    }

    override fun after() {
        super.after()
        close()
    }

    private fun close() {
        if (injectableActivityScenario != null) {
            injectableActivityScenario?.close()
            injectableActivityScenario = null
        }
    }

    var injectableActivityScenario: InjectableActivityScenario<A>? = null

    fun launch(launchIntent: Intent? = null,
               block: (InjectableActivityScenario<A>.() -> Unit)? =  null
    ): InjectableActivityScenario<A> {
        val injectableActivityScenario = injectableActivityScenario ?: throw IllegalStateException("Scenario not initialised")
        injectableActivityScenario.launch(launchIntent)
        if (block != null) {
            injectableActivityScenario.block()
        }
        return injectableActivityScenario
    }
}
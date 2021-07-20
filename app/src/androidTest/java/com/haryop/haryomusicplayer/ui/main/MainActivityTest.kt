package com.haryop.haryomusicplayer.ui.main

import android.content.Context
import android.content.res.Resources
import android.view.KeyEvent
import androidx.lifecycle.Lifecycle
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.utils.BaseRobot
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun openAbout() {
        openActionBarOverflowOrOptionsMenu(
            InstrumentationRegistry.getInstrumentation().getTargetContext()
        );
        onView(withText(R.string.about)).perform(click())
        onView(withId(R.id.webview_about)).check(matches(isDisplayed()))
    }

    private fun getResourceString(id: Int): String? {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        return targetContext.getResources().getString(id)
    }

    @Test
    fun searchContent() {
        onView(withContentDescription(R.string.action_search_label)).perform(click())
        val term = "bruno"
        val searchFieldId = Resources.getSystem().getIdentifier(
            "search_src_text",
            "id", "android"
        )

        onView(withHint(R.string.search_hint))
            .perform(clearText(), typeText(term))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))


        BaseRobot().assertOnView_wihbait(
            withId(R.id.content_labels),
            withId(R.id.found_label),
            matches(withText(containsString(term)))
        )

    }

}
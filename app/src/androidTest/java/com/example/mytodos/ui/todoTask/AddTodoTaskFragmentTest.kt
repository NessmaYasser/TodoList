package com.example.mytodos.ui.todoTask

import com.example.mytodos.R

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mytodos.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddTodoTaskFragmentTest{

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun ensureButtonDisableAfterOneClick() {
        onView(withId(R.id.TaskTitleET)).perform(ViewActions.typeText("Task1"))
        onView(withId(R.id.TaskBodyET)).perform(ViewActions.typeText("Task1"))
        closeSoftKeyboard();
        onView(withId(R.id.startBtn)).perform(click());
    }

}
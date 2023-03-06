package com.example.mytodos


import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mytodos.ui.todoList.TodoListFragment
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.matchers.JUnitMatchers.*


@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Test
    fun navigationToAddTodoTaskFragment() {
//test navController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        var scenario = launchFragmentInContainer<TodoListFragment>(Bundle())


        scenario.onFragment() { fragment ->
            // The fragment’s view has just been created
            //set the graph to navController
            navController.setGraph(R.navigation.nav_graph)
            //make navgraph available with findNavController api
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(R.id.addTodoTaskFragment)
            assertThat(navController.currentDestination?.id).isEqualTo(R.id.addTodoTaskFragment)

        }
    }

}

package com.example.tomasNewsApp.main

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val preferences = mock<SharedPreferences>()
    val editor = mock<SharedPreferences.Editor>()

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        given(preferences.edit()).willReturn(editor)
        given(editor.putBoolean(any(), any())).willReturn(editor)

//        viewModel = MainViewModel(preferences)
    }

    @Test
    fun init_firstTime_showsTutorial() {
        given(preferences.getBoolean("tutorial_is_shown", false)).willReturn(false)
        val viewModel = MainViewModel(preferences)

        val observer = mock<Observer<in Unit>>()
        viewModel.showTutorial.observeForever(observer)

        verify(observer).onChanged(Unit)
    }

    @Test
    fun init_firstTime_setTutorialIsShown() {
        given(preferences.getBoolean("tutorial_is_shown", false)).willReturn(false)
        val viewModel = MainViewModel(preferences)

        val observer = mock<Observer<in Unit>>()
        viewModel.showTutorial.observeForever(observer)

        verify(editor).putBoolean("tutorial_is_shown", true)
        verify(editor).apply()
    }
}
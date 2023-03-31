package com.santog.wizards.ui

import com.santog.wizards.ui.navigation.NavigationTest
import com.santog.wizards.ui.view.HomeScreenTest
import com.santog.wizards.ui.view.StaffScreenTest
import com.santog.wizards.ui.view.StudentsScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * It is recommended to turn off the following animation options on the target device:
 * - Window animation scale
 * - Transition animation scale
 * - Animation duration scale
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeScreenTest::class,
    StaffScreenTest::class,
    StudentsScreenTest::class,
    NavigationTest::class
)

class TestSuite

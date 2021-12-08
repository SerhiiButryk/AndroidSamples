/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app

import com.example.app.fragments.EnterInfoFragmentTests
import com.example.app.fragments.GreetingsFragmentTests
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Runs all app tests
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    EnterInfoFragmentTests::class,
    GreetingsFragmentTests::class
)

class AppTests {
}
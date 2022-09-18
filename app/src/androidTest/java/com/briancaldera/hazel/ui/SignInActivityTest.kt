package com.briancaldera.hazel.ui

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.briancaldera.hazel.repo.HazelRepository
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SignInActivityTest {

    @Mock
    private lateinit var auth: FirebaseAuth

    @InjectMocks
    private lateinit var repository: HazelRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @get:Rule
    val rule = activityScenarioRule<SignInActivity>()

    @Test
    fun assertThat_userIsNull() {
        // Given
        `when`(auth.currentUser).thenReturn(null)
        // When
        val user = repository.getCurrentUser()
        // Then
        Truth.assertThat(user).isNull()
    }

    @Test
    fun assertThat_activityIsLaunched() {
        val scenario = rule.scenario
        if (scenario.state == Lifecycle.State.STARTED) {
            Espresso.pressBack()
        }
        Truth.assertThat(scenario.state).isEqualTo(Lifecycle.State.RESUMED)
    }

    @Test
    fun assertThat_activityIsLaunched2() {
        val scenario = rule.scenario
        Truth.assertThat(scenario.state).isEqualTo(Lifecycle.State.STARTED)
    }
}
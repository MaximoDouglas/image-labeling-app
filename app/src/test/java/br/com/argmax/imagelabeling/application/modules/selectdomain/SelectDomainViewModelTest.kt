package br.com.argmax.imagelabeling.application.modules.selectdomain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.argmax.imagelabeling.application.modules.TestCoroutineRule
import org.junit.Rule

class SelectDomainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

}
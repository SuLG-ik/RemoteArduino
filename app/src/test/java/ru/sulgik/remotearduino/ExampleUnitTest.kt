package ru.sulgik.remotearduino

import org.junit.Test

import java.security.SecureRandom
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val s = CharArray(16){
            Random.nextInt().toChar()
        }.joinToString { "" }
        print(s)
    }
}

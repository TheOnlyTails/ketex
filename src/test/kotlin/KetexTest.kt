import com.theonlytails.ketex.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KetexTest {
    @Test
    fun start() {
        assertTrue {
            regex {
                +start
                +"b"
            }.containsMatchIn("begins with the letter 'b'")
        }

        assertFalse {
            regex {
                +start
                +("a".token between 3..4)
            }.containsMatchIn("doesn't begin with the letter 'a'")
        }
    }

    @Test
    fun end() {
        assertTrue {
            regex {
                +"a"
                +end
            }.containsMatchIn("ends with the letter a")
        }

        assertFalse {
            regex {
                +"a"
                +end
            }.containsMatchIn("doesn't end with the letter 'a'")
        }
    }

    @Test
    fun word() {
        assertTrue {
            regex {
                +set {
                    +('A'..'Z')
                    +('a'..'z')
                    +('0'..'9')
                    +'_'
                }
            }.containsMatchIn("is a word_")
        }
    }
}
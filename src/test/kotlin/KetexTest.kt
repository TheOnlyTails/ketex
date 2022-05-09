import com.theonlytails.ketex.*
import com.theonlytails.ketex.KetexGroup.KetexGroupType
import org.junit.jupiter.api.Test
import java.lang.Character.UnicodeScript
import kotlin.test.assertEquals

// these tests are blatantly copied from the awesome Melody project:
// https://github.com/yoav-lavi/melody/blob/main/crates/melody_compiler/tests/mod.rs

class KetexTest {
    @Test
    fun quantifiers() {
        assertEquals("A{5}",
            regexAsString {
                +('A'.token count 5)
            }
        )
    }

    @Test
    fun `capture groups`() {
        assertEquals("(A{5}[0-9])",
            regexAsString {
                +group {
                    +('A'.token count 5)
                    +('0'..'9')
                }
            }
        )
    }

    @Test
    fun `named capture groups`() {
        assertEquals("(?<name>A{5}[0-9])",
            regexAsString {
                +group(name = "name") {
                    +('A'.token count 5)
                    +('0'..'9')
                }
            }
        )
    }

    @Test
    fun `range quantifier`() {
        assertEquals("A{1,5}",
            regexAsString {
                +('A'.token between 1..5)
            }
        )
    }

    @Test
    fun `uppercase range`() {
        assertEquals("[A-Z][A-Z]{7}",
            regexAsString {
                +('A'..'Z')
                +(('A'..'Z').token count 7)
            }
        )
    }

    @Test
    fun `lowercase range`() {
        assertEquals("[a-z][a-z]{8}",
            regexAsString {
                +('a'..'z')
                +(('a'..'z').token count 8)
            }
        )
    }

    @Test
    fun `min range`() {
        assertEquals("a{5,}",
            regexAsString {
                +('a'.token atLeast 5)
            }
        )
    }

    @Test
    fun `start end`() {
        assertEquals("^a$",
            regexAsString {
                +start
                +'a'
                +end
            }
        )
    }

    @Test
    fun `non capturing group`() {
        assertEquals("(?:A{5}[0-9]){3}",
            regexAsString {
                +(group(type = KetexGroupType.NonCapture) {
                    +('A'.token count 5)
                    +('0'..'9')
                } count 3)
            }
        )
    }

    @Test
    fun any() {
        assertEquals(".{3}",
            regexAsString {
                +(any count 3)
            }
        )
    }

    @Test
    fun `negated range`() {
        assertEquals("[^3-5][^a-z]",
            regexAsString {
                +!set {
                    +('3'..'5')
                }
                +!set {
                    +('a'..'z')
                }
            }
        )
    }

    @Test
    fun some() {
        assertEquals(".+",
            regexAsString {
                +any.some()
            }
        )
        assertEquals("(?:ABC)+",
            regexAsString {
                +group(KetexGroupType.NonCapture) {
                    +"ABC"
                }.some()
            }
        )
    }

    @Test
    fun option() {
        assertEquals(".?",
            regexAsString {
                +any.option()
            }
        )
        assertEquals("(?:ABC)?",
            regexAsString {
                +group(KetexGroupType.NonCapture) {
                    +"ABC"
                }.option()
            }
        )
    }

    @Test
    fun or() {
        assertEquals("(?:first|second|[a-z])(?:first|second)",
            regexAsString {
                +group(KetexGroupType.NonCapture) {
                    +("first".token or "second".token or ('a'..'z').token)
                }
                +group(KetexGroupType.NonCapture) {
                    +("first".token or "second".token)
                }
            }
        )
    }

    @Test
    fun maybe() {
        assertEquals(".*",
            regexAsString {
                +any.maybe()
            }
        )
        assertEquals("(?:ABC)*",
            regexAsString {
                +group(KetexGroupType.NonCapture) {
                    +"ABC"
                }.maybe()
            }
        )
    }

    @Test
    fun count() {
        assertEquals("""\w{5}""",
            regexAsString {
                +(word count 5)
            }
        )
    }

    @Test
    fun `lookahead and behind`() {
        assertEquals("(?=a)(?<=a)(?!a)(?<!a)",
            regexAsString {
                +group(KetexGroupType.PositiveLookahead) {
                    +"a"
                }
                +group(KetexGroupType.PositiveLookbehind) {
                    +"a"
                }
                +group(KetexGroupType.NegativeLookahead) {
                    +"a"
                }
                +group(KetexGroupType.NegativeLookbehind) {
                    +"a"
                }
            }
        )
    }

    @Test
    fun `negated set`() {
        assertEquals("[^abcd][^abcd]{5}",
            regexAsString {
                +!set {
                    +"abcd"
                }

                +(!set {
                    +"abcd"
                } count 5)
            }
        )
    }

    @Test
    fun escaping() {
        assertEquals("""\[\]\(\)\{\}\*\+\?\|\^\$\.\-\\\\""",
            regexAsString {
                +"""[](){}*+?|^$.-\\"""
            }
        )
    }

    @Test
    fun lazy() {
        assertEquals("A*?A+?A??A{5}?A{6,}?A{5,6}?",
            regexAsString {
                +"A".token.maybe().lazy()
                +"A".token.some().lazy()
                +"A".token.option().lazy()
                +("A".token count 5).lazy()
                +("A".token atLeast 6).lazy()
                +("A".token between 5..6).lazy()
            }
        )
    }

    @Test
    fun `inverted tokens`() {
        assertEquals("""\w\W\p{InHebrew}\P{InHebrew}""",
            regexAsString {
                +word
                +!word
                +category(UnicodeScript.HEBREW)
                +!category(UnicodeScript.HEBREW)
            }
        )
    }

    @Test
    fun `category token`() {
        assertEquals("""\p{InHebrew}\p{InArmenian}\p{InArabic}""",
            regexAsString {
                +category(UnicodeScript.HEBREW)
                +category(UnicodeScript.ARMENIAN)
                +category(UnicodeScript.ARABIC)
            }
        )
    }

    @Test
    fun `general tokens (whitespace)`() {
        assertEquals("""\n\r\t""",
            regexAsString {
                +newline
                +carriageReturn
                +tab
            }
        )
    }

    @Test
    fun `parameterized tokens`() {
        assertEquals("""\p{InHebrew}{5}""",
            regexAsString {
                +(category(UnicodeScript.HEBREW) count 5)
            }
        )

        assertEquals("""(?<anychar>.)\k{anychar}""",
            regexAsString {
                +group(name = "anychar") {
                    +any
                }
                +name("anychar")
            }
        )

        assertEquals("""(.)\1""",
            regexAsString {
                +group {
                    +any
                }
                +index(1)
            }
        )
    }

    @Test
    fun `set with operator`() {
        assertEquals("""[abc][a-zA-Z0-9][\w\s]""",
            regexAsString {
                +set["a", "b", "c"]
                +set['a'..'z', 'A'..'Z', '0'..'9']
                +set[word, whitespace]
            }
        )
    }

    @Test
    fun `group with vararg`() {
        assertEquals("""(abc)""",
            regexAsString {
                +group("abc")
            }
        )
    }
}

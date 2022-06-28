package com.theonlytails.ketex

import java.lang.Character.UnicodeScript
import java.lang.Character.UnicodeBlock

@Suppress("unused")
@KetexMarker
enum class CharacterCategory(val id: String) {
    @KetexMarker
    Unassigned("Cn"),

    @KetexMarker
    Letter("L"),

    @KetexMarker
    UppercaseLetter("Lu"),

    @KetexMarker
    LowercaseLetter("Ll"),

    @KetexMarker
    TitlecaseLetter("Lt"),

    @KetexMarker
    ModifierLetter("Lm"),

    @KetexMarker
    OtherLetter("Lo"),

    @KetexMarker
    NonSpacingMark("Mn"),

    @KetexMarker
    EnclosingMark("Me"),

    @KetexMarker
    CombiningSpacingMark("Mc"),

    @KetexMarker
    DecimalDigitNumber("Nd"),

    @KetexMarker
    LetterNumber("Nl"),

    @KetexMarker
    OtherNumber("No"),

    @KetexMarker
    SpaceSeparator("Zs"),

    @KetexMarker
    LineSeparator("Zl"),

    @KetexMarker
    ParagraphSeparator("Zp"),

    @KetexMarker
    Control("Cc"),

    @KetexMarker
    Format("Cf"),

    @KetexMarker
    PrivateUse("Co"),

    @KetexMarker
    Surrogate("Cs"),

    @KetexMarker
    DashPunctuation("Pd"),

    @KetexMarker
    StartPunctuation("Ps"),

    @KetexMarker
    EndPunctuation("Pe"),

    @KetexMarker
    ConnectorPunctuation("Pc"),

    @KetexMarker
    InitialQuotePunctuation("Pi"),

    @KetexMarker
    FinalQuotePunctuation("Pf"),

    @KetexMarker
    OtherPunctuation("Po"),

    @KetexMarker
    Symbol("S"),

    @KetexMarker
    MathSymbol("Sm"),

    @KetexMarker
    CurrencySymbol("Sc"),

    @KetexMarker
    ModifierSymbol("Sk"),

    @KetexMarker
    OtherSymbol("So"),

    @KetexMarker
    Print("Print"),

    @KetexMarker
    Alnum("Alnum"),

    @KetexMarker
    Alpha("Alpha"),

    @KetexMarker
    ASCII("ASCII"),

    @KetexMarker
    Blank("Blank"),

    @KetexMarker
    Cntrl("Cntrl"),

    @KetexMarker
    Digit("Digit"),

    @KetexMarker
    Graph("Graph"),

    @KetexMarker
    Lower("Lower"),

    @KetexMarker
    Punct("Punct"),

    @KetexMarker
    Space("Space"),

    @KetexMarker
    Upper("Upper"),

    @KetexMarker
    XDigit("XDigit"),
}

context(KetexFragment)
@KetexMarker
operator fun KetexToken.not() = with(this()) {
    val singleCharRegex = regex {
        +start
        +word
        +end
    }

    KetexToken {
        if (startsWith("\\") && singleCharRegex matches get(1).toString()) {
            first() + get(1).uppercase() + substring(2)
        } else {
            // only tokens with a backslash are invertible
            System.err.println("Warning: Cannot invert a token which doesn't being with \\ and has a single character: $this")
            ""
        }
    }
}

/**
 * Append an "any character" token (`.`) to the regex.
 *
 * > Matches any character except linebreaks.
 * > Equivalent to [^\n\r].
 */
context(KetexFragment)
@KetexMarker
val any by token(".")

/**
 * Append a newline character token (`\n`) to the regex.
 *
 * > Matches a newline character.
 */
context(KetexFragment)
@KetexMarker
val newline by token("\\n")

/**
 * Append a carriage return token (`\r`) to the regex.
 *
 * > Matches a carriage return, unicode character U+2185.
 */
context(KetexFragment)
@KetexMarker
val carriageReturn by token("\\r")

/**
 * Append a tab character token (`\t`) to the regex.
 *
 * > Matches a tab character.
 * > Historically, tab stops happen every 8 characters.
 */
context(KetexFragment)
@KetexMarker
val tab by token("\\t")

/**
 * Append a word token (`\w`) to the regex.
 *
 * > Matches any word character (alphanumeric & underscore).
 * > Only matches low-ascii characters (no accented or non-roman characters).
 * > Equivalent to [A-Za-z0-9_]
 */
context(KetexFragment)
@KetexMarker
val word by token("\\w")

/**
 * Append a digit token (`\d`) to the regex.
 *
 * > Matches any digit character (0-9).
 * > Equivalent to [0-9].
 */
context(KetexFragment)
@KetexMarker
val digit by token("\\d")

/**
 * Append a whitespace token (`\s`) to the regex.
 *
 * > Matches any whitespace character (spaces, tabs, line breaks).
 */
context(KetexFragment)
@KetexMarker
val whitespace by token("\\s")

/**
 * Append a unicode newlines token (`\R`) to the regex.
 *
 * > Matches any Unicode newline sequence.
 */
context(KetexFragment)
@KetexMarker
val unicodeNewlines by token("\\R")

/**
 * Append a vertical whitespace token (`\v`) to the regex.
 *
 * > Matches unicode vertical whitespace.
 */
context(KetexFragment)
@KetexMarker
val verticalWhitespace by token("\\v")

/**
 * Append a horizontal whitespace token (`\h`) to the regex.
 *
 * > Matches spaces, tabs, non-breaking/mathematical/ideographic spaces, and so on.
 */
context(KetexFragment)
@KetexMarker
val horizontalWhitespace by token("\\h")

/**
 * Append a subpattern/backreference token (`\#`) to the regex.
 *
 * > This will match a repeat of the text matched and captured by the capture group # (number) specified.
 */
context(KetexFragment)
@KetexMarker
val index: (Int) -> KetexToken
    get() = { """\$it""".token }


/**
 * Append a unicode property token (`\p{IsScript}`) to the regex.
 *
 * > Matches a unicode character in the given [script][UnicodeScript].
 */
context(KetexFragment)
@KetexMarker
fun property(script: UnicodeScript): KetexToken {
    val formattedScript = script.name.split("_").joinToString("_") {
        it.first().uppercase() + it.substring(1).lowercase()
    }

    return """\p{Is$formattedScript}""".token
}

/**
 * Append a unicode property token (`\p{InScript}`) to the regex.
 *
 * > Matches a unicode character in the given [block][UnicodeBlock].
 */
context(KetexFragment)
@KetexMarker
fun property(block: UnicodeBlock): KetexToken {
    val formattedScript = block.toString().split("_").joinToString("_") {
        it.first().uppercase() + it.substring(1).lowercase()
    }

    return """\p{In$formattedScript}""".token
}

/**
 * Append a unicode property token (`\p{}`) to the regex.
 *
 * > Matches a unicode character in the given [category][CharacterCategory].
 */
context(KetexFragment)
@KetexMarker
fun property(category: CharacterCategory) = """\p{${category.id}}""".token

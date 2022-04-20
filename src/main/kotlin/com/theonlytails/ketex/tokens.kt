package com.theonlytails.ketex

import java.lang.Character.UnicodeScript

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
 * Append a subpattern/backreference token (`\#`) to the regex.
 *
 * > This will match a repeat of the text matched and captured by the capture group # (number) specified.
 */
context(KetexFragment)
@KetexMarker
val category: (UnicodeScript) -> KetexToken
    get() = { script ->
        val formattedScript = script.name.split("_").joinToString("_") {
            it.first().uppercase() + it.substring(1).lowercase()
        }

        """\p{In$formattedScript}""".token
    }

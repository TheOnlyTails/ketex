package com.theonlytails.ketex

import java.lang.Character.UnicodeScript

/**
 * Append an "any character" token (`.`) to the regex.
 *
 * > Matches any character except linebreaks.
 * > Equivalent to [^\n\r].
 */
context(KetexBuilder)
        @KetexMarker
val any: KetexToken
    get() = { "." }

/**
 * Append a word token (`\w`) to the regex.
 *
 * > Matches any word character (alphanumeric & underscore).
 * > Only matches low-ascii characters (no accented or non-roman characters).
 * > Equivalent to [A-Za-z0-9_]
 */
context(KetexBuilder)
        @KetexMarker
val word: KetexToken
    get() = { """\w""" }

/**
 * Append a digit token (`\d`) to the regex.
 *
 * > Matches any digit character (0-9).
 * > Equivalent to [0-9].
 */
context(KetexBuilder)
        @KetexMarker
val digit: KetexToken
    get() = { """\d""" }

/**
 * Append a whitespace token (`\s`) to the regex.
 *
 * > Matches any whitespace character (spaces, tabs, line breaks).
 */
context(KetexBuilder)
        @KetexMarker
val whitespace: KetexToken
    get() = { """\s""" }

/**
 * Append a unicode newlines token (`\R`) to the regex.
 *
 * > Matches any Unicode newline sequence.
 */
context(KetexBuilder)
        @KetexMarker
val unicodeNewlines: KetexToken
    get() = { """\R""" }

/**
 * Append a vertical whitespace token (`\v`) to the regex.
 *
 * > Matches unicode vertical whitespace.
 */
context(KetexBuilder)
        @KetexMarker
val verticalWhitespace: KetexToken
    get() = { """\v""" }

/**
 * Append a horizontal whitespace token (`\h`) to the regex.
 *
 * > Matches spaces, tabs, non-breaking/mathematical/ideographic spaces, and so on.
 */
context(KetexBuilder)
        @KetexMarker
val horizontalWhitespace: KetexToken
    get() = { """\h""" }

/**
 * Append a subpattern/backreference token (`\#`) to the regex.
 *
 * > This will match a repeat of the text matched and captured by the capture group # (number) specified.
 */
context(KetexBuilder)
        @KetexMarker
val index: (Int) -> KetexToken
    get() = { { """\$it""" } }

/**
 * Append a subpattern/backreference token (`\#`) to the regex.
 *
 * > This will match a repeat of the text matched and captured by the capture group # (number) specified.
 */
context(KetexBuilder)
        @KetexMarker
val category: (UnicodeScript) -> KetexToken
    get() = {
        {
            val script = it.name.first().uppercase() + it.name.substring(1)
            """\p{In$script}"""
        }
    }

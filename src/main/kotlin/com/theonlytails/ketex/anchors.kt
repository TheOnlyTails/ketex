package com.theonlytails.ketex

/**
 * Append a start anchor (`^`) to the regex.
 *
 * > Matches the beginning of the string, or the beginning of a line if the multiline flag (`m`) is enabled.
 * > This matches a position, not a character.
 */
context(KetexBuilder)
@KetexMarker
val start by token("^")

/**
 * Append an ending anchor (`$`) to the regex.
 *
 * > Matches the end of the string, or the end of a line if the multiline flag (`m`) is enabled.
 * > This matches a position, not a character.
 */
context(KetexBuilder)
@KetexMarker
val end by token("$")

/**
 * Append a word boundary anchor (`\b`) to the regex.
 *
 * > Matches a word boundary position between a word character and non-word character or position (start / end of string).
 * > See the word character class ([w][word]) for more info.
 */
context(KetexBuilder)
@KetexMarker
val wordBoundary by token("\\b")
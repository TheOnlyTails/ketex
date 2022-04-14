package com.theonlytails.ketex

/**
 * Append a start anchor (`^`) to the regex.
 *
 * > Matches the beginning of the string, or the beginning of a line if the multiline flag (`m`) is enabled.
 * > This matches a position, not a character.
 */
context(KetexBuilder)
@KetexMarker
val start: KetexToken
    get() = { "^" }

/**
 * Append an ending anchor (`$`) to the regex.
 *
 * > Matches the end of the string, or the end of a line if the multiline flag (`m`) is enabled.
 * > This matches a position, not a character.
 */
context(KetexBuilder)
@KetexMarker
val end: KetexToken
    get() = { "$" }

context(KetexBuilder)
@KetexMarker
val wordBoundary: KetexToken
    get() = { """\b""" }
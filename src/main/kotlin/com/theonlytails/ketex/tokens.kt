package com.theonlytails.ketex

/**
 * Append an "any character" (`.`) token to the regex.
 *
 * > Matches any character except linebreaks.
 * > Equivalent to [^\n\r].
 */
context(KetexBuilder)
@KetexMarker
val any: KetexToken
    get() = { "." }
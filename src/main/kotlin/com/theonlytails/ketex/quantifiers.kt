package com.theonlytails.ketex

/**
 * Appends an exact quantifier (`{3}`) to the current token.
 *
 * > Matches the specified quantity of the previous token.
 * > {3} will match exactly 3.
 */
context(KetexFragment)
@KetexMarker
infix fun KetexToken.count(count: Int) =
	"${this@count()}{$count}".token

/**
 * Appends a range quantifier (`{1,3}`) to the current token.
 *
 * > Matches the specified quantity of the previous token.
 * > {1,3} will match 1 to 3.
 */
context(KetexFragment)
@KetexMarker
infix fun KetexToken.between(range: IntRange) =
    "${this@between()}{${range.first},${range.last}}".token

/**
 * Appends a minimum quantifier (`{3,}`) to the current token.
 *
 * > Matches the specified quantity of the previous token.
 * > {3,} will match 3 or more.
 */
context(KetexFragment)
@KetexMarker
infix fun KetexToken.atLeast(min: Int) =
	"${this@atLeast()}{$min,}".token

/**
 * Appends a plus quantifier (`+`) to the current token.
 *
 * > Matches 1 or more of the preceding token.
 */
context(KetexFragment)
@KetexMarker
fun KetexToken.some() =
	"${this@some()}+".token

/**
 * Appends a star quantifier (`*`) to the current token.
 *
 * > Matches 0 or more of the preceding token.
 */
context(KetexFragment)
@KetexMarker
fun KetexToken.maybe() =
	"${this@maybe()}*".token

/**
 * Appends an optional quantifier (`?`) to the current token.
 *
 * > Matches 0 or 1 of the preceding token, effectively making it optional.
 * @see lazy
 */
context(KetexFragment)
@KetexMarker
fun KetexToken.option() =
	"${this@option()}?".token
/**
 * Appends a lazy modifier (`?`) to the previous quantifier.
 *
 * > Makes the preceding quantifier lazy, causing it to match as few characters as possible.
 * > By default, quantifiers are greedy, and will match as many characters as possible.
 *
 * @see option
 */
context(KetexFragment)
@KetexMarker
fun KetexToken.lazy() = option()

/**
 * Appends an alternation token (`|`) to the current token.
 *
 * > Acts like a boolean OR. Matches the expression before or after the |.
 * > It can operate within a group, or on a whole expression. The patterns will be tested in order.
 */
context(KetexFragment)
@KetexMarker
infix fun KetexToken.or(other: KetexToken) =
	"${this@or()}|${other()}".token

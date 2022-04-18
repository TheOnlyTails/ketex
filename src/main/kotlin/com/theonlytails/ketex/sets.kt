package com.theonlytails.ketex

@KetexMarker
class KetexSet(private var negate: Boolean) : KetexFragment(), KetexToken {
    @KetexMarker
    override operator fun CharRange.unaryPlus() = add { "$first-$last" }

    /**
     * Intersects this set with another set (`[\w&&[^\d]]`), matching the result.
     *
     * > Matches the intersection between the two character classes.
     * > Useful if you want to white or blacklist certain characters from a broader class.
     */
    @KetexMarker
    infix fun intersect(other: KetexSet) = add { "&&$other" }

    @KetexMarker
    override fun toString() = "[" + (if (negate) "^" else "") + rawOutput + "]"

    /**
     * Negates the current set (`[^abcedfu]`), making it match any character *not* in the set.
     */
    @KetexMarker
    operator fun not() = apply {
        negate = !negate
    }
}

/**
 * Create a character set (`[abcedfu]`) to the regex.
 *
 * > Match any character in the set.
 *
 * @param negate when set to true, the set will be negated (`[^abcedfu]`) and match any character not in the set.
 * @see [KetexSet.not]
 */
context(KetexFragment)
@KetexMarker
inline fun set(negate: Boolean = false, block: KetexSet.() -> Unit) = KetexSet(negate).apply(block)
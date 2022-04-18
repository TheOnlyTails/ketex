package com.theonlytails.ketex

@KetexMarker
class KetexSet(private var negate: Boolean) : KetexFragment(), KetexToken {
    @KetexMarker
    override operator fun CharRange.unaryPlus() = add { "$first-$last" }

    @KetexMarker
    infix fun intersect(other: KetexSet) = add { "&&$other" }

    @KetexMarker
    override fun toString() = "[" + (if (negate) "^" else "") + rawOutput + "]"

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

@KetexMarker
inline operator fun KetexFragment.get(block: KetexSet.() -> Unit) = KetexSet(false).apply(block)
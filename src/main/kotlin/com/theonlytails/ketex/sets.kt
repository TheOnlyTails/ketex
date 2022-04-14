package com.theonlytails.ketex

@KetexMarker
class KetexSet(private val negate: Boolean) : KetexFragment() {
    @KetexMarker
    operator fun CharRange.unaryPlus() = add { "$first-$last" }

    @KetexMarker
    infix fun intersect(other: KetexSet) = add { "&&$other" }

    @KetexMarker
    override fun build(tokens: MutableList<KetexToken>): KetexToken = {
        "[" + (if (negate) "^" else "") + tokens.joinToString("") { it() } + "]"
    }
}

/**
 * Create a character set (`[abcedfu]`) to the regex.
 *
 * > Match any character in the set.
 *
 * @param negate when set to true, the set will be negated (`[^abcedfu]`) and match any character not in the set.
 */
context(KetexBuilder)
@KetexMarker
inline fun set(negate: Boolean = false, block: KetexSet.() -> Unit) = KetexSet(negate).apply(block)
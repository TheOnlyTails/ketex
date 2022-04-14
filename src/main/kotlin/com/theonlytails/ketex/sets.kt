package com.theonlytails.ketex

@KetexMarker
class KetexSet(private val negate: Boolean) {
    private val tokens = mutableListOf<KetexToken>()

    @KetexMarker
    operator fun KetexToken.unaryPlus() = this@KetexSet.add(this)

    @KetexMarker
    operator fun unaryPlus() = add(this)

    @KetexMarker
    operator fun CharRange.unaryPlus() = add { "$first-$last" }

    @KetexMarker
    operator fun Char.unaryPlus() = add { "$this" }

    @KetexMarker
    private fun add(token: KetexToken) {
        tokens += token
    }

    @KetexMarker
    private fun add(token: KetexSet) {
        tokens += token.build()
    }

    internal fun build(): KetexToken = {
        "[" + (if (negate) "^" else "") + tokens.joinToString("") { it() } + "]"
    }
}

/**
 * Append a set of characters (`[abcedfu]`) to the regex.
 *
 * > Match any character in the set.
 *
 * @param negate when set to true, the set will be negated (`[^abcedfu]`) and match any character not in the set.
 */
context(KetexBuilder)
@KetexMarker
fun set(negate: Boolean = false, block: KetexSet.() -> Unit) = KetexSet(negate).apply(block).build()
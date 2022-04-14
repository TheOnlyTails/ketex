package com.theonlytails.ketex

abstract class KetexFragment {
    private val tokens = mutableListOf<KetexToken>()

    /**
     * Append a string of characters to match in the regex.
     * This function escapes the string before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun CharSequence.unaryPlus() = add(this)

    /**
     * Append a single character to match in the regex.
     * This function escapes the char before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun Char.unaryPlus() = add("$this")

    /**
     * Append a custom regex token to match in the regex.
     * DOES NOT escape metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun KetexToken.unaryPlus() = this@KetexFragment.add(this)

    /**
     * Append a character set to match in the regex.
     *
     * @see add
     */
    @KetexMarker
    operator fun KetexSet.unaryPlus() = this@KetexFragment.add(this)

    /**
     * Append a regex group to the regex.
     *
     * @see add
     */
    @KetexMarker
    operator fun KetexGroup.unaryPlus() = this@KetexFragment.add(this)

    /**
     * Append a raw string of characters to the regex.
     *
     * @param escape If set to false, the function will not escape the string before appending it to the regex, so it can add metacharacters.
     * @see unaryPlus
     */
    @KetexMarker
    fun add(str: CharSequence, escape: Boolean = true) {
        tokens += { if (escape) str.escape() else str }
    }

    @KetexMarker
    fun add(token: KetexToken) {
        tokens += token
    }

    @KetexMarker
    fun add(set: KetexSet) {
        tokens += set.build(tokens)
    }

    @KetexMarker
    fun add(group: KetexGroup) {
        tokens += group.build(tokens)
    }

    @KetexMarker
    internal abstract fun build(tokens: MutableList<KetexToken>): KetexToken

    @KetexMarker
    override fun toString() = "" + build(tokens)()

    @KetexMarker
    internal fun CharSequence.escape(): CharSequence {
        val reservedChars = listOf('.', '(', ')', '[', ']', '{', '}', '*', '+', '?', '^', '$', '/', '\\', '-', '|')
        return this.map { if (it in reservedChars) """\$it""" else it }.joinToString("")
    }
}
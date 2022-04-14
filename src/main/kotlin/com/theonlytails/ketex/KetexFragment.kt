package com.theonlytails.ketex

interface KetexFragment {
    val tokens: MutableList<KetexToken>
        get() = mutableListOf()

    /**
     * Append a string of characters to match in the regex.
     * This function escapes the string before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun String.unaryPlus() = add(this)

    /**
     * Append a string of characters to match in the regex.
     * This function escapes the string before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun KetexToken.unaryPlus() = this@KetexFragment.add(this)

    /**
     * Append a raw string of characters to the regex.
     *
     * @param escape If set to false, the function will not escape the string before appending it to the regex, so it can add metacharacters.
     * @see unaryPlus
     */
    @KetexMarker
    fun add(str: CharSequence, escape: Boolean = true) = this.apply {
        tokens += { if (escape) str.escape() else str }
    }

    @KetexMarker
    fun add(token: KetexToken) {
        tokens += token
    }

    @KetexMarker
    fun add(set: KetexSet) {
        tokens += set.build()
    }

    @KetexMarker
    fun add(group: KetexGroup) {
        tokens += group.build()
    }

    @KetexMarker
    fun CharSequence.escape(): CharSequence {
        val reservedChars = listOf('.', '(', ')', '[', ']', '{', '}', '*', '+', '?', '^', '$', '/', '\\', '-', '|')
        return this.map { if (it in reservedChars) """\$it""" else it }.joinToString("")
    }
}
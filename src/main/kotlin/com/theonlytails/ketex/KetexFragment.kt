package com.theonlytails.ketex

@KetexMarker
fun interface KetexToken {
    override fun toString(): String

    operator fun invoke() = toString()
}

@KetexMarker
internal fun token(str: String) = lazy {
    KetexToken { str }
}

@KetexMarker
abstract class KetexFragment {
    internal val rawOutput = StringBuilder("")

    /**
     * Append a string of characters to match in the regex.
     * This function escapes the string before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun String.unaryPlus() = add(this)

    /**
     * Append a single character to match in the regex.
     * This function escapes the char before appending it to the regex, so it cannot add metacharacters.
     *
     * @see add
     */
    @KetexMarker
    operator fun Char.unaryPlus() = add("$this")

    @KetexMarker
    open operator fun CharRange.unaryPlus() = add { "[$first-$last]" }

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
    fun add(str: String, escape: Boolean = true) {
        rawOutput += KetexToken { if (escape) str.escape() else str }
    }

    @KetexMarker
    fun add(token: KetexToken) {
        rawOutput += token
    }

    @KetexMarker
    fun add(set: KetexSet) {
        rawOutput += set.toString()
    }

    @KetexMarker
    fun add(group: KetexGroup) {
        rawOutput += group.toString()
    }

    @KetexMarker
    override fun toString() = rawOutput.toString()

    @KetexMarker
    internal fun String.escape(): String {
        val reservedChars = listOf('.', '(', ')', '[', ']', '{', '}', '*', '+', '?', '^', '$', '/', '\\', '-', '|')
        return toCharArray().joinToString("") {
            if (it in reservedChars) """\$it""" else "$it"
        }
    }

    private operator fun StringBuilder.plusAssign(str: CharSequence) {
        append(str)
    }

    private operator fun StringBuilder.plusAssign(str: KetexToken) {
        this += str()
    }
}

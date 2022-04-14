package com.theonlytails.ketex

@KetexMarker
typealias KetexToken = context(KetexBuilder) KetexBuilder.() -> String

@DslMarker
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.TYPEALIAS)
annotation class KetexMarker

@KetexMarker
class KetexBuilder {
    private val regex = StringBuilder("")

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
    operator fun KetexToken.unaryPlus() = this@KetexBuilder.add(this)

    /**
     * Append a raw string of characters to the regex.
     *
     * @param escape If set to false, the function will not escape the string before appending it to the regex, so it can add metacharacters.
     * @see unaryPlus
     */
    @KetexMarker
    fun add(str: String, escape: Boolean = true): KetexBuilder {
        regex.append(if (escape) str.escape() else str)
        return this
    }

    @KetexMarker
    internal fun add(str: KetexToken) = add(str(), false)

    @KetexMarker
    fun toRegex(vararg flags: RegexOption) = toString().toRegex(flags.toSet())

    @KetexMarker
    override fun toString() = regex.toString()

    @KetexMarker
    fun String.escape(): String {
        val reservedChars = listOf('.', '(', ')', '[', ']', '{', '}', '*', '+', '?', '^', '$', '/', '\\', '-', '|')
        return this.map { if (it in reservedChars) """\$it""" else it }.joinToString("")
    }
}

@KetexMarker
inline fun buildRegex(block: KetexBuilder.() -> Unit) = KetexBuilder().apply(block)

@KetexMarker
inline fun regex(vararg flags: RegexOption, block: KetexBuilder.() -> Unit) =
    buildRegex(block).toRegex(*flags)

@KetexMarker
inline fun regexAsString(block: KetexBuilder.() -> Unit) =
    buildRegex(block).toString()
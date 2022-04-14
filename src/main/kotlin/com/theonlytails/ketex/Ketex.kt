package com.theonlytails.ketex

@KetexMarker
typealias KetexToken = () -> CharSequence

@DslMarker
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.TYPEALIAS)
annotation class KetexMarker

@KetexMarker
class KetexBuilder : KetexFragment() {
    @KetexMarker
    fun toRegex(vararg flags: RegexOption) = toString().toRegex(flags.toSet())

    @KetexMarker
    override fun build(tokens: MutableList<KetexToken>): KetexToken = { tokens.joinToString("") { it() } }
}

@KetexMarker
inline fun buildRegex(block: KetexBuilder.() -> Unit) = KetexBuilder().apply(block)

@KetexMarker
inline fun regex(vararg flags: RegexOption, block: KetexBuilder.() -> Unit) =
    buildRegex(block).toRegex(*flags)

@KetexMarker
inline fun regexAsString(block: KetexBuilder.() -> Unit) =
    buildRegex(block).toString()

context(KetexBuilder)
@KetexMarker
inline val CharSequence.token: KetexToken
    get() = { this@token }
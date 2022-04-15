package com.theonlytails.ketex

@DslMarker
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.TYPEALIAS)
annotation class KetexMarker

@KetexMarker
class KetexBuilder : KetexFragment() {
    @KetexMarker
    fun toRegex(vararg flags: RegexOption) = toString().toRegex(flags.toSet())
}

@KetexMarker
inline fun buildRegex(block: KetexBuilder.() -> Unit) = KetexBuilder().apply(block)

@KetexMarker
inline fun regex(vararg flags: RegexOption, block: KetexBuilder.() -> Unit) =
    buildRegex(block).toRegex(*flags)

@KetexMarker
inline fun regexAsString(block: KetexBuilder.() -> Unit) =
    buildRegex(block).toString()

context(KetexFragment)
@KetexMarker
inline val String.token: KetexToken
    get() = KetexToken { this }

context(KetexFragment)
@KetexMarker
inline val Char.token: KetexToken
    get() = KetexToken { this.toString() }

context(KetexFragment)
@KetexMarker
inline val CharRange.token: KetexToken
    get() = KetexToken { "[$first-$last]" }
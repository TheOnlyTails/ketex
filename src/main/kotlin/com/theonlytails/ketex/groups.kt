package com.theonlytails.ketex

import com.theonlytails.ketex.KetexGroup.KetexGroupType

context(KetexFragment)
@KetexMarker
class KetexGroup(private val type: KetexGroupType, private val name: String) : KetexFragment(), KetexToken {
    @KetexMarker
    override fun toString(): String {
        val groupName = if (name.isNotBlank()) {
            if (type == KetexGroupType.Capture) {
                "?<$name>"
            } else {
                if (name.isNotBlank()) System.err.println("Named groups are not supported for group types other than KetexGroupType.Capture")
                ""
            }
        } else ""

        return "(" + type.sign + groupName + rawOutput + ")"
    }

    @KetexMarker
    enum class KetexGroupType(val sign: String) {
        /**
         * Marks a capture (`()`) group.
         *
         * > Isolates part of the full match to be later referred to byb ID within the regex or the matches array.
         * > IDs start at 1.
         */
        @KetexMarker
        Capture(""),

        /**
         * Marks a non-capture (`(?:)`) group.
         *
         * > A non-capturing group allow you to apply quantifiers to part of your regex but does not capture or assign an ID.
         */
        @KetexMarker
        NonCapture("?:"),

        /**
         * Marks a non-capture, positive lookahead (`(?=)`) group.
         *
         * > Asserts that the given subpattern can be matched here, without consuming characters.
         */
        @KetexMarker
        PositiveLookahead("?="),

        /**
         * Marks a non-capture, negative lookahead (`(?!)`) group.
         *
         * > Starting at the current position in the expression, ensures that the given pattern will not match.
         * > Does not consume characters.
         */
        @KetexMarker
        NegativeLookahead("?!"),

        /**
         * Marks a non-capture, positive lookbehind (`(?<=)`) group.
         *
         * > Ensures that the given pattern will match, ending at the current position in the expression.
         * > The pattern must have a fixed width.
         * > Does not consume any characters.
         */
        @KetexMarker
        PositiveLookbehind("?<="),

        /**
         * Marks a non-capture, negative lookbehind (`(?<!)`) group.
         *
         * > Ensures that the given pattern would not match and end at the current position in the expression.
         * > The pattern must have a fixed width.
         * > Does not consume any characters.
         */
        @KetexMarker
        NegativeLookbehind("?<!"),
    }
}

context(KetexFragment)
@KetexMarker
inline fun group(
    type: KetexGroupType = KetexGroupType.Capture,
    name: String = "",
    block: KetexGroup.() -> Unit,
) = KetexGroup(type, name).apply(block)

context(KetexFragment)
@KetexMarker
fun group(vararg tokens: String) = group {
    tokens.forEach { +it }
}

context(KetexFragment)
@KetexMarker
fun group(vararg tokens: Char) = group {
    tokens.forEach { +it }
}

context(KetexFragment)
@KetexMarker
fun group(vararg tokens: CharRange) = group {
    tokens.forEach { +it }
}

context(KetexFragment)
@KetexMarker
fun group(vararg tokens: KetexToken) = group {
    tokens.forEach { +it }
}

package com.theonlytails.ketex

import com.theonlytails.ketex.KetexGroup.KetexGroupType

@KetexMarker
class KetexGroup(private val type: KetexGroupType) : KetexFragment() {
    @KetexMarker
    override fun build(): KetexToken = {
        "(" + type.sign + tokens.joinToString("") { it() } + ")"
    }

    @KetexMarker
    enum class KetexGroupType(val sign: String) {
        @KetexMarker
        Capture(""),

        @KetexMarker
        NonCapture("?:"),

        @KetexMarker
        PositiveLookahead("?="),

        @KetexMarker
        NegativeLookahead("?!"),

        @KetexMarker
        PositiveLookbehind("?<="),

        @KetexMarker
        NegativeLookbehind("?<!"),
    }
}

context(KetexBuilder)
@KetexMarker
inline fun group(type: KetexGroupType = KetexGroupType.Capture, block: KetexGroup.() -> Unit) =
    KetexGroup(type).apply(block)
package com.theonlytails.ketex

import com.theonlytails.ketex.KetexGroup.KetexGroupType

@KetexMarker
class KetexGroup(private val type: KetexGroupType, private val name: String) : KetexFragment() {
    @KetexMarker
    override fun build(tokens: MutableList<KetexToken>): KetexToken = {
        val groupName = if (type == KetexGroupType.Capture) "<$name>" else {
            System.err.println("Named groups are not supported for group types other than KetexGroupType.Capture")
            ""
        }

        "(" + type.sign + groupName + tokens.joinToString("") { it() } + ")"
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
inline fun group(
    type: KetexGroupType = KetexGroupType.Capture,
    name: String = "",
    block: KetexGroup.() -> Unit,
) = KetexGroup(type, name).apply(block)
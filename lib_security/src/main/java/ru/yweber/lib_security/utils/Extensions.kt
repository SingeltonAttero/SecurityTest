package ru.yweber.lib_security.utils

/**
 * Created on 24.05.2020
 * @author YWeber */


internal fun StringBuilder.br() = append("\n")
internal fun StringBuilder.tb() = append("   ")
internal fun StringBuilder.sp(string: String) = append(" $string ")
internal fun StringBuilder.hooks(block: (builder: StringBuilder) -> Unit) = apply {
    sp("{ ")
    block.invoke(this)
    sp(" }")

}
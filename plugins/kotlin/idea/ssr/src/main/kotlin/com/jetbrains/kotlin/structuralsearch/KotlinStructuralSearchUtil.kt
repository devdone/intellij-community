package com.jetbrains.kotlin.structuralsearch

import com.google.common.collect.ImmutableBiMap
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import com.intellij.structuralsearch.impl.matcher.handlers.MatchingHandler
import com.intellij.structuralsearch.impl.matcher.handlers.SubstitutionHandler
import org.jetbrains.kotlin.lexer.KtSingleValueToken
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.renderer.DescriptorRenderer
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.expressions.OperatorConventions

fun getCommentText(comment: PsiComment): String {
    return when (comment.tokenType) {
        KtTokens.EOL_COMMENT -> comment.text.drop(2).trim()
        KtTokens.BLOCK_COMMENT -> comment.text.drop(2).dropLast(2).trim()
        else -> ""
    }
}

private val BINARY_EXPR_OP_NAMES = ImmutableBiMap.builder<KtSingleValueToken, Name>()
    .putAll(OperatorConventions.ASSIGNMENT_OPERATIONS)
    .putAll(OperatorConventions.BINARY_OPERATION_NAMES)
    .build()

fun IElementType.binaryExprOpName(): Name? = BINARY_EXPR_OP_NAMES[this]

fun KotlinType.renderNames(): Array<String> = arrayOf(
    DescriptorRenderer.FQ_NAMES_IN_TYPES.renderType(this),
    DescriptorRenderer.SHORT_NAMES_IN_TYPES.renderType(this),
    "$this"
)

fun String.removeTypeParameters(): String {
    if (!this.contains('<') || !this.contains('>')) return this
    return this.removeRange(
        this.indexOfFirst { c -> c == '<' },
        this.indexOfLast { c -> c == '>' } + 1
    )
}

val MatchingHandler.withinHierarchyTextFilterSet: Boolean
    get() = this is SubstitutionHandler && (this.isSubtype || this.isStrictSubtype)
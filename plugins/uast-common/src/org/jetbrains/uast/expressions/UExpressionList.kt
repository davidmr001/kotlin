/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.uast

import org.jetbrains.uast.internal.acceptList
import org.jetbrains.uast.internal.log
import org.jetbrains.uast.visitor.UastVisitor

/**
 * Represents a generic list of expressions.
 */
interface UExpressionList : UExpression {
    /**
     * Returns the list of expressions.
     */
    val expressions: List<UExpression>

    /**
     * Returns the list kind.
     */
    val kind: UastSpecialExpressionKind

    override fun accept(visitor: UastVisitor) {
        if (visitor.visitExpressionList(this)) return
        annotations.acceptList(visitor)
        expressions.acceptList(visitor)
        visitor.afterVisitExpressionList(this)
    }

    fun firstOrNull(): UExpression? = expressions.firstOrNull()

    override fun asLogString() = log("USpecialExpressionList (${kind.name})", expressions)
    override fun asRenderString() = kind.name + " " + expressions.joinToString(" : ") { it.asRenderString() }
}
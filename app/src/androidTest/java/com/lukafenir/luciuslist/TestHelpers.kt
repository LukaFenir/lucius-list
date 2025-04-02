package com.lukafenir.luciuslist

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag

fun SemanticsNodeInteractionsProvider.onAllNodesWithTagPattern(pattern: String): SemanticsNodeInteractionCollection {
    val regex = pattern.toRegex()
    return onAllNodes(
        SemanticsMatcher("hasTestTag(matches=$pattern)") { node ->
            val testTag = node.config.getOrNull(SemanticsProperties.TestTag)
            testTag != null && regex.matches(testTag)
        }
    )
}
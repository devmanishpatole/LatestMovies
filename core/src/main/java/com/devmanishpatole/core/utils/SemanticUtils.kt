package com.devmanishpatole.core.utils

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */

// Adding drawable id to semantic tree
val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId
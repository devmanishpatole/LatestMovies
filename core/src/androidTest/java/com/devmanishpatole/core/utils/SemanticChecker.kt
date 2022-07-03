package com.devmanishpatole.core.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */

/**
 * Confirms the given drawable id is present in semantic tree
 *
 * @param id of drawable to confirm
 */
fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)
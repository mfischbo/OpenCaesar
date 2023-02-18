package net.fischboeck.caesar.util

import com.badlogic.gdx.math.Matrix4
import kotlin.math.sqrt

object Constants {

    val isoTransform: Matrix4 = Matrix4()
    val invIsoTransform: Matrix4

    const val TILE_WIDTH = 32
    const val TILE_HEIGHT = 32
    const val CAMERA_TRANSLATION_SPEED = 16f;

    init {
        isoTransform.translate(0f, TILE_HEIGHT / 4f, 0f)
        isoTransform.scale(
            (sqrt(2.0) / 2f).toFloat(),
            (sqrt(2.0) / 4f).toFloat(),
            1f)
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45f)

        invIsoTransform = Matrix4(isoTransform).inv()
    }
}
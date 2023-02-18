package net.fischboeck.caesar.model

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import net.fischboeck.caesar.renderer.geometry.Rectangle
import net.fischboeck.caesar.renderer.geometry.Shape
import net.fischboeck.caesar.util.Constants.TILE_HEIGHT
import net.fischboeck.caesar.util.Constants.TILE_WIDTH

class MouseCursorNode(id: String) : SceneNode(id) {

    private var currentX = 0f
    private var currentY = 0f
    private val rectangle = Rectangle(
        ShapeRenderer.ShapeType.Filled,
        Color(1.0f, 0.5f, 0.5f, 0.5f),
        Vector2(currentX, currentY),
        Vector2(TILE_WIDTH.toFloat(), TILE_HEIGHT.toFloat()))

    fun setPosition(x: Float, y: Float) {
        currentX = x
        currentY = y
        rectangle.start.x = currentX * TILE_WIDTH
        rectangle.start.y = currentY * TILE_HEIGHT
    }

    fun getShape(): Shape {
        return rectangle
    }
}
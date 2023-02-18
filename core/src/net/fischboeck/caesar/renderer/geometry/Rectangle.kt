package net.fischboeck.caesar.renderer.geometry

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2

data class Rectangle(
    val shapeType: ShapeType,
    val color: Color,
    val start: Vector2,
    val dimensions: Vector2
) : Shape
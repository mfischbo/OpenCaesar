package net.fischboeck.caesar.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import net.fischboeck.caesar.model.MouseCursorNode
import net.fischboeck.caesar.model.SceneNode
import net.fischboeck.caesar.model.StreetLayerNode
import net.fischboeck.caesar.renderer.geometry.Rectangle
import net.fischboeck.caesar.renderer.geometry.Shape
import net.fischboeck.caesar.util.Constants
import net.fischboeck.caesar.util.Constants.TILE_HEIGHT
import net.fischboeck.caesar.util.Constants.TILE_WIDTH

class WorldRenderer(
    private val tiledMapRenderer: IsometricTiledMapRenderer,
    private val spriteBatch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
    private val sceneGraph: SceneNode
): Disposable {



    fun render(camera: OrthographicCamera, deltaTime: Long) {
        tiledMapRenderer.setView(camera)
        tiledMapRenderer.render()

        Gdx.gl.glEnable(GL30.GL_BLEND)
        camera.update()
        shapeRenderer.transformMatrix = Constants.isoTransform
        shapeRenderer.projectionMatrix = camera.combined

        // TODO: Requires some clever sorting to not have to many draw calls
        collectShapes().forEachIndexed {_, shape ->
            if (shape is Rectangle) {
                shapeRenderer.begin(shape.shapeType)
                shapeRenderer.color = shape.color
                shapeRenderer.rect(shape.start.x, shape.start.y, shape.dimensions.x, shape.dimensions.y)
                shapeRenderer.end()
            }
        }

        camera.update()
        //spriteBatch.transformMatrix = Constants.isoTransform
        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
        collectTextures().forEach {
            spriteBatch.draw(it, 1f, 1f)
        }
        spriteBatch.end()
    }

    fun collectShapes(): List<Shape> {
        // TODO: Traverse scene graph
        val cursor = sceneGraph.findNodeById("MOUSE_CURSOR")
        if (cursor != null) {
            return listOf((cursor as MouseCursorNode).getShape())
        }

        return emptyList()
    }

    private fun collectTextures(): List<TextureRegion> {
        val streetLayer = sceneGraph.findNodeById("STREET_LAYER")
        if (streetLayer != null) {
            return (streetLayer as StreetLayerNode).getSprites()
        }
        return emptyList()
    }


    override fun dispose() {
        TODO("Not yet implemented")
    }
}
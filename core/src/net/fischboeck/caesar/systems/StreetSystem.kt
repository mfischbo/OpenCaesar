package net.fischboeck.caesar.systems

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.msg.MessageManager
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import net.fischboeck.caesar.events.MessageCodes
import net.fischboeck.caesar.events.MouseButtonUpEvent
import net.fischboeck.caesar.model.SceneNode
import net.fischboeck.caesar.model.StreetLayerNode
import net.fischboeck.caesar.util.Constants.TILE_HEIGHT
import net.fischboeck.caesar.util.Constants.TILE_WIDTH

class StreetSystem(
    private val sceneGraph: SceneNode,
    private val assetManager: AssetManager
): Telegraph {

    private lateinit var streetLayer: StreetLayerNode
    private lateinit var streetTiles: TextureRegion
    init {
        MessageManager.getInstance().addListener(this, MessageCodes.MOUSE_UP)
    }

    override fun handleMessage(msg: Telegram): Boolean {

        if (msg.message != MessageCodes.MOUSE_UP) {
            return false
        }

        if (sceneGraph.findNodeById("STREET_LAYER") == null) {

            val texture = assetManager.get("assets/test-tileset.png", Texture::class.java)
            streetTiles = TextureRegion(texture)


            streetLayer = StreetLayerNode("STREET_LAYER", streetTiles)
            sceneGraph.addNode(streetLayer)
        }

        val data = msg.extraInfo as MouseButtonUpEvent

        streetLayer.addStreetAt(data.gridX, data.gridY, 0)


        return true
    }
}
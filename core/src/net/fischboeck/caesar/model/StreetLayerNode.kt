package net.fischboeck.caesar.model

import com.badlogic.gdx.graphics.g2d.TextureRegion
import net.fischboeck.caesar.util.Constants.TILE_HEIGHT
import net.fischboeck.caesar.util.Constants.TILE_WIDTH

class StreetLayerNode(id: String, private val streetTextures: TextureRegion) : SceneNode(id) {

    private val streetParts: MutableList<StreetMap> = mutableListOf()

    fun addStreetAt(gridX: Int, gridY: Int, type: Int) {
        streetParts.add(StreetMap(gridX, gridY, type))
    }

    fun getSprites(): List<TextureRegion> {
        val textures = streetTextures.split(TILE_WIDTH, TILE_HEIGHT)
        return listOf(textures[0][2])
    }

}
data class StreetMap(
    val gridX: Int,
    val gridY: Int,
    val type: Int
)
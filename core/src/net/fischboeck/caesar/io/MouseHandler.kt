package net.fischboeck.caesar.io

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ai.msg.MessageManager
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import net.fischboeck.caesar.events.MessageCodes
import net.fischboeck.caesar.events.MouseButtonUpEvent
import net.fischboeck.caesar.util.Constants
import net.fischboeck.caesar.util.Constants.TILE_HEIGHT
import net.fischboeck.caesar.util.Constants.TILE_WIDTH
import kotlin.math.floor

class MouseHandler(
    private val camera: Camera,
): InputAdapter() {
    private val eventBus = MessageManager.getInstance()

    private var tx: Int = 0
    private var ty: Int = 0

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        val tmp = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
            .mul(Constants.invIsoTransform);

        val newX = floor(tmp.x / TILE_WIDTH).toInt()
        val newY = floor(tmp.y / TILE_HEIGHT).toInt()

        if (newX != tx || newY != ty) {
            tx = newX
            ty = newY
            eventBus.dispatchMessage(MessageCodes.MOUSE_MOVED, Vector2(tx.toFloat(), ty.toFloat()))
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        val temp = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
            .mul(Constants.invIsoTransform)

        eventBus.dispatchMessage(MessageCodes.MOUSE_UP, MouseButtonUpEvent(
            screenX, screenY, button, floor(temp.x / TILE_WIDTH).toInt(), floor(temp.y / TILE_HEIGHT).toInt()))

        return false
    }
}
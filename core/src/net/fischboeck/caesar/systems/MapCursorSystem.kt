package net.fischboeck.caesar.systems

import com.badlogic.gdx.ai.msg.MessageManager
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import com.badlogic.gdx.math.Vector2
import net.fischboeck.caesar.events.MessageCodes
import net.fischboeck.caesar.events.MessageCodes.MOUSE_MOVED
import net.fischboeck.caesar.model.MouseCursorNode
import net.fischboeck.caesar.model.SceneNode

class MapCursorSystem(
    private val sceneGraph: SceneNode
): Telegraph {

    init {
        MessageManager.getInstance().addListener(this, MOUSE_MOVED)
    }

    override fun handleMessage(msg: Telegram): Boolean {
        if (msg.message != MOUSE_MOVED) {
            return false
        }

        val data = msg.extraInfo as Vector2
        val node = sceneGraph.findNodeById(NODE_ID)
        if (node == null) {
            val mouseCursorNode = MouseCursorNode(NODE_ID)
            mouseCursorNode.setPosition(data.x, data.y)
            sceneGraph.addNode(mouseCursorNode)
        } else {
            val cursorNode = node as MouseCursorNode
            cursorNode.setPosition(data.x, data.y)
        }
        return true;
    }

    companion object {
        const val NODE_ID = "MOUSE_CURSOR"
    }
}
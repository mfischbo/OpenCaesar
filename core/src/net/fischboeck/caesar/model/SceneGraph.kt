package net.fischboeck.caesar.model

import java.util.LinkedList

open class SceneNode(val id: String) {
    private val children: MutableList<SceneNode> = LinkedList<SceneNode>()



    fun getChildren(): List<SceneNode> {
        return children
    }

    fun addNode(node: SceneNode) {
        // TODO: Needs to be dispatched in a way that we can easiliy find all nodes
        // when rendering the scene. For now we put everything on the first level
        this.children.add(node)
    }

    fun findNodeById(id: String): SceneNode? {

        if (this.id == id) {
            return this
        }

        if (this.children.isEmpty()) {
            return null
        }

        return children.firstOrNull { child -> child.id == id }
    }
}
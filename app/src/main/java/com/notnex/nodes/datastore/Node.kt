package com.notnex.nodes.datastore

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.notnex.nodes.util.NodeSerializer
import com.notnex.nodes.util.generateNameFromHash
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.UUID

@Serializable
data class Node(
    val id: String = UUID.randomUUID().toString(),
    val name: String = generateNameFromHash(id),
    @Serializable(with = NodeSerializer::class)
    val children: SnapshotStateList<Node> = mutableStateListOf(),
    @Transient var parent: Node? = null
) {
    fun assignParentsRecursively(parentNode: Node? = null) {
        parent = parentNode
        children.forEach { it.assignParentsRecursively(this) }
    }

}

fun Node.root(): Node {
    var node = this
    while (node.parent != null) {
        node = node.parent!!
    }
    return node
}
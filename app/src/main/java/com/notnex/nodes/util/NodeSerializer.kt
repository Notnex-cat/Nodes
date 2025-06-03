package com.notnex.nodes.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.notnex.nodes.datastore.Node
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object NodeSerializer : KSerializer<SnapshotStateList<Node>> {
    private val listSerializer = ListSerializer(Node.serializer())

    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: SnapshotStateList<Node>) {
        listSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): SnapshotStateList<Node> {
        return listSerializer.deserialize(decoder).toMutableStateList()
    }
}

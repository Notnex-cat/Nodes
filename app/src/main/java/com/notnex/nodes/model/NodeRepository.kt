package com.notnex.nodes.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.notnex.nodes.datastore.Node
import com.notnex.nodes.datastore.nodeStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class NodeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
    private val nodeKey = stringPreferencesKey("node_tree")

    suspend fun saveNodeTree(node: Node) {
        val jsonString = json.encodeToString(node)
        context.nodeStore.edit { prefs ->
            prefs[nodeKey] = jsonString
        }
    }

    suspend fun loadNodeTree(): Node {
        val prefs = context.nodeStore.data.first()
        val jsonString = prefs[nodeKey] ?: return Node()
        return json.decodeFromString(jsonString)
    }
}

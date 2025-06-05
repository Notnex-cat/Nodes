package com.notnex.nodes.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notnex.nodes.datastore.Node
import com.notnex.nodes.datastore.root
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@HiltViewModel
class NodesViewModel @Inject constructor(
    private val repository: NodeRepository
) : ViewModel() {

    private val _currentNode = mutableStateOf<Node>(Node())
    val currentNode: State<Node> get() = _currentNode

    init {
        viewModelScope.launch {
            val root = repository.loadNodeTree()
            root.assignParentsRecursively() // возвращаем ссылку на родителя
            _currentNode.value = root
        }
    }

    fun goToChild(child: Node) {
        _currentNode.value = child
    }


    fun addNewChild(): Node {
        val child = Node(parent = _currentNode.value)
        _currentNode.value.children.add(child)
        save()
        return child
    }

    fun goToParent() {
        _currentNode.value.parent?.let {
            _currentNode.value = it
            save()
        }
    }

    private fun save() {
        viewModelScope.launch {
            repository.saveNodeTree(_currentNode.value.root())
            //Log.d("NodeTree", json.encodeToString(_currentNode.value.root()))
        }
    }

    fun removeChild(child: Node) {
        _currentNode.value.children.remove(child)
        save()
    }


    fun flattenTree(root: Node): List<Pair<Int, Node>> {
        val result = mutableListOf<Pair<Int, Node>>()
        fun dfs(node: Node, depth: Int) {
            result.add(depth to node)
            node.children.forEach { dfs(it, depth + 1) }
        }
        dfs(root, 0)
        return result
    }

}

package com.notnex.nodes.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notnex.nodes.datastore.Node
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NodesViewModel @Inject constructor(
    private val repository: NodeRepository
) : ViewModel() {

    private val _currentNode = mutableStateOf<Node>(Node())
    val currentNode: State<Node> get() = _currentNode

    init {
        viewModelScope.launch {
            _currentNode.value = repository.loadNodeTree()
        }
    }

    fun goToChild(child: Node) {
        _currentNode.value = child
    }

    fun goToParent() {
        _currentNode.value.parent?.let {
            _currentNode.value = it
        }
    }

    fun addNewChild(): Node {
        val child = Node(parent = _currentNode.value)
        _currentNode.value.children.add(child)
        return child
    }

    fun removeChild(child: Node) {
        _currentNode.value.children.remove(child)
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

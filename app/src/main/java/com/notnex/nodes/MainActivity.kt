package com.notnex.nodes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.notnex.nodes.model.NodeRepository
import com.notnex.nodes.model.NodesViewModel
import com.notnex.nodes.ui.theme.NodesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = NodeRepository(applicationContext)
        val vm = NodesViewModel(repository)

        setContent {
            NodesTheme {
                Scaffold { padding ->
                    NodeScreen(viewModel = vm, modifier = Modifier.padding(padding))
            }
            }
        }
    }
}

@Composable
fun NodeScreen(viewModel: NodesViewModel, modifier: Modifier = Modifier) {
    val node by viewModel.currentNode

    Column (
        modifier = modifier.padding()
    ) {
        Button(onClick = { viewModel.goToParent() }) {
            Text("Назад")
        }

        Button(onClick = { viewModel.addNewChild() }) {
            Text("Добавить потомка")
        }

        LazyColumn {
            items(node.children.size) { index ->
                val child = node.children[index]
                Button(onClick = { viewModel.goToChild(child) }) {
                    Text(child.name)
                }
            }

        }
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    NodesTheme {
//        NodeScreen(viewModel = NodesViewModel(NodeRepository(applicationContext)))
//    }
//}
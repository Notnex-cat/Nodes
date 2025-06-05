package com.notnex.nodes.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.notnex.nodes.model.NodesViewModel
import com.notnex.nodes.ui.theme.NodesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: NodesViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NodesTheme {
                Scaffold { padding ->
                    NodeScreen(viewModel = viewModel, modifier = Modifier.padding(padding))
            }
            }
        }
    }
}

@Composable
fun NodeScreen(viewModel: NodesViewModel, modifier: Modifier = Modifier) {
    val node by viewModel.currentNode

    Column (
        modifier = modifier.fillMaxSize()
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
                Column {
                    Button(onClick = { viewModel.goToChild(child) }) {
                        Text(child.name)
                    }
                    Button(onClick = { viewModel.removeChild(child) }) {
                        Text("Удалить")
                    }
                }
            }
        }

    }
}
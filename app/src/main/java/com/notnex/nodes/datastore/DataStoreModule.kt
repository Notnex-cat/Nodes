package com.notnex.nodes.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.nodeStore by preferencesDataStore(name = "node_data")

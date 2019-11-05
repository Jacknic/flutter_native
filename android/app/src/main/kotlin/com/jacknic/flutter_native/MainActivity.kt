package com.jacknic.flutter_native

import android.os.Bundle
import com.jacknic.flutter_native.plugins.AudioPlugin
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        AudioPlugin.registerWith(this)
    }
}

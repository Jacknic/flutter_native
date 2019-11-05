package com.jacknic.flutter_native.plugins

import android.content.Context
import android.media.AudioManager
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry


/**
 * 媒体音量设置插件
 *
 * @author Jacknic
 */
class AudioPlugin(appContext: Context) : MethodChannel.MethodCallHandler {

    private var audioManager: AudioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    companion object {
        private const val METHOD_CHANNEL = "methodChannel/audio"
        fun registerWith(pluginRegistry: PluginRegistry) {
            val registrar = pluginRegistry.registrarFor(METHOD_CHANNEL)
            // 实例 Plugin，并绑定到 Channel 上
            val appContext = registrar.activeContext().applicationContext
            val audioPlugin = AudioPlugin(appContext)
            val methodChannel = MethodChannel(registrar.messenger(), METHOD_CHANNEL)
            methodChannel.setMethodCallHandler(audioPlugin)
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getVolume" -> {
                println("调用获取音量方法：")
                val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                result.success(volume)
            }
            "getMaxVolume" -> {
                val volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                result.success(volume)
            }
            "setVolume" -> {
                val newVolume = call.argument<Int>("newVolume")
                println("调用设置音量方法：$newVolume")
                newVolume?.apply {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 1)
                }
                result.success(null)
            }
            else -> {
                println("未知方法 ${call.method}")
                result.notImplemented()
            }
        }
    }
}
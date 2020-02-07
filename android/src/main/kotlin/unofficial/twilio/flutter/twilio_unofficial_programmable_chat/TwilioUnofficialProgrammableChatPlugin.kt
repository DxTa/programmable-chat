package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar

/** TwilioUnofficialProgrammableChatPlugin */
class TwilioUnofficialProgrammableChatPlugin : FlutterPlugin {
    private lateinit var methodChannel: MethodChannel

    private lateinit var chatChannel: EventChannel

    private lateinit var loggingChannel: EventChannel

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    companion object {
        @Suppress("unused")
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val instance = TwilioUnofficialProgrammableChatPlugin()
            instance.onAttachedToEngine(registrar.context(), registrar.messenger())
        }

        @JvmStatic
        val LOG_TAG = "TwilioUnofficial_PChat"

        var loggingSink: EventChannel.EventSink? = null

        var nativeDebug: Boolean = false

        lateinit var chatListener: ChatListener

        @JvmStatic
        fun debug(msg: String) {
            if (nativeDebug) {
                Log.d(LOG_TAG, msg)
                loggingSink?.success(msg)
            }
        }
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        onAttachedToEngine(binding.applicationContext, binding.binaryMessenger)
    }

    private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger) {
        val pluginHandler = PluginHandler(applicationContext)
        methodChannel = MethodChannel(messenger, "twilio_unofficial_programmable_chat")
        methodChannel.setMethodCallHandler(pluginHandler)

        chatChannel = EventChannel(messenger, "twilio_unofficial_programmable_chat/room")
        chatChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
                debug("TwilioUnofficialProgrammableChatPlugin.onAttachedToEngine => Chat eventChannel attached")
                chatListener.events = events
                chatListener.chatClient?.setListener(chatListener)
            }

            override fun onCancel(arguments: Any) {
                debug("TwilioUnofficialProgrammableChatPlugin.onAttachedToEngine => Chat eventChannel detached")
                chatListener.events = null
            }
        })

        loggingChannel = EventChannel(messenger, "twilio_unofficial_programmable_chat/logging")
        loggingChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
                debug("TwilioUnofficialProgrammableChatPlugin.onAttachedToEngine => Logging eventChannel attached")
                loggingSink = events
            }

            override fun onCancel(arguments: Any) {
                debug("TwilioUnofficialProgrammableChatPlugin.onAttachedToEngine => Logging eventChannel detached")
                loggingSink = null
            }
        })
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        methodChannel.setMethodCallHandler(null)
        chatChannel.setStreamHandler(null)
    }
}

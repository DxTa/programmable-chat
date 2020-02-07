package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.ChannelsMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.ChatClientMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.PaginatorMethods

class PluginHandler(private val applicationContext: Context) : MethodCallHandler, ActivityAware {
    private var activity: Activity? = null

    override fun onDetachedFromActivityForConfigChanges() {
        this.activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        this.activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        this.activity = null
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.onMethodCall => received ${call.method}")
        when (call.method) {
            "debug" -> debug(call, result)
            "create" -> create(call, result)

            "ChatClient#updateToken" -> ChatClientMethods.updateToken(call, result)

            "Channels#createChannel" -> ChannelsMethods.createChannel(call, result)
            "Channels#getChannel" -> ChannelsMethods.getChannel(call, result)
            "Channels#getPublicChannelsList" -> ChannelsMethods.getPublicChannelsList(call, result)

            "Paginator#requestNextPage" -> PaginatorMethods.requestNextPage(call, result)

            else -> result.notImplemented()
        }
    }

    private fun create(call: MethodCall, result: MethodChannel.Result) {
        TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.create => called")

        val token = call.argument<String>("token")
        val propertiesObj = call.argument<Map<String, Any>>("properties")
        if (token == null) {
            return result.error("ERROR", "Missing token", null)
        }
        if (propertiesObj == null) {
            return result.error("ERROR", "Missing properties", null)
        }

        try {
            var propertiesBuilder = ChatClient.Properties.Builder()
            if (propertiesObj["region"] != null) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.create => setting Properties.region to '${propertiesObj["region"]}'")
                propertiesBuilder.setRegion(propertiesObj["region"] as String)
            }

            if (propertiesObj["deferCA"] != null) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.create => setting Properties.setDeferCertificateTrustToPlatform to '${propertiesObj["deferCA"]}'")
                propertiesBuilder.setDeferCertificateTrustToPlatform(propertiesObj["deferCA"] as Boolean)
            }

            TwilioUnofficialProgrammableChatPlugin.chatListener = ChatListener(token, propertiesBuilder.createProperties())

            ChatClient.create(applicationContext, TwilioUnofficialProgrammableChatPlugin.chatListener.token, TwilioUnofficialProgrammableChatPlugin.chatListener.properties, object : CallbackListener<ChatClient>() {
                override fun onSuccess(chatClient: ChatClient) {
                    chatClient.users.myUser
                    TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.create => ChatClient.create onSuccess: myIdentity is '${chatClient.myIdentity}'")
                    TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient = chatClient
                    result.success(Mapper.chatClientToMap(chatClient))
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.create => ChatClient.create onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (e: Exception) {
            result.error("ERROR", e.toString(), e)
        }
    }

    private fun debug(call: MethodCall, result: MethodChannel.Result) {
        val enableNative = call.argument<Boolean>("native")
        if (enableNative != null) {
            TwilioUnofficialProgrammableChatPlugin.nativeDebug = enableNative
            result.success(enableNative)
        } else {
            result.error("MISSING_PARAMS", "Missing 'native' parameter", null)
        }
    }
}

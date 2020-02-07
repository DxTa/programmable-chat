package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Paginator
import com.twilio.chat.StatusListener
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

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

            "ChatClient#updateToken" -> updateToken(call, result)

            "Channels#createChannel" -> createChannel(call, result)
            "Channels#getChannel" -> getChannel(call, result)
            "Channels#getPublicChannelsList" -> getPublicChannelsList(call, result)

            "Paginator#requestNextPage" -> result.notImplemented()
            else -> result.notImplemented()
        }
    }

    private fun getPublicChannelsList(call: MethodCall, result: MethodChannel.Result) {
        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getPublicChannelsList(object : CallbackListener<Paginator<ChannelDescriptor>>() {
            override fun onSuccess(paginator: Paginator<ChannelDescriptor>) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.getPublicChannelsList => Channels.getPublicChannelsList onSuccess")
                val pageId = PaginatorManager.setPaginator(paginator)
                result.success(Mapper.paginatorToMap(pageId, paginator))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.getPublicChannelsList => Channels.getPublicChannelsList onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    private fun getChannel(call: MethodCall, result: MethodChannel.Result) {
        val channelSidOrUniqueName = call.argument<String>("channelSidOrUniqueName")
                ?: return result.error("ERROR", "Missing 'channelSidOrUniqueName'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSidOrUniqueName, object : CallbackListener<Channel>() {
            override fun onSuccess(newChannel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.getChannel => Channels.getChannel onSuccess")
                result.success(Mapper.channelToMap(newChannel))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.getChannel => Channels.getChannel onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    private fun createChannel(call: MethodCall, result: MethodChannel.Result) {
        val friendlyName = call.argument<String>("friendlyName")
                ?: return result.error("ERROR", "Missing 'friendlyName'", null)

        val channelTypeValue = call.argument<String>("channelType")
                ?: return result.error("ERROR", "Missing 'channelType'", null)

        val channelType = when (channelTypeValue) {
            "PRIVATE" -> Channel.ChannelType.PRIVATE
            "PUBLIC" -> Channel.ChannelType.PUBLIC
            else -> null
        } ?: return result.error("ERROR", "Wrong value for 'channelType'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.createChannel(friendlyName, channelType, object : CallbackListener<Channel>() {
            override fun onSuccess(newChannel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.createChannel => Channels.createChannel onSuccess")
                result.success(Mapper.channelToMap(newChannel))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.createChannel => Channels.createChannel onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    private fun updateToken(call: MethodCall, result: MethodChannel.Result) {
        val token = call.argument<String>("token")
                ?: return result.error("ERROR", "Missing 'token'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.updateToken(token, object : StatusListener() {
            override fun onSuccess() {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.updateToken => ChatClient.updateToken onSuccess")
                result.success(null)
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("TwilioUnofficialProgrammableChatPlugin.updateToken => ChatClient.updateToken onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
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

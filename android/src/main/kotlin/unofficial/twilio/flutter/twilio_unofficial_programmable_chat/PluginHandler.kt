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
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.listeners.ChatListener
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.ChannelMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.ChannelsMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.ChatClientMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.MemberMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.MembersMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.MessageMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.MessagesMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.PaginatorMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.UserMethods
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods.UsersMethods

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
            "ChatClient#shutdown" -> ChatClientMethods.shutdown(call, result)

            "User#unsubscribe" -> UserMethods.unsubscribe(call, result)

            "Users#getChannelUserDescriptors" -> UsersMethods.getChannelUserDescriptors(call, result)
            "Users#getUserDescriptor" -> UsersMethods.getUserDescriptor(call, result)
            "Users#getAndSubscribeUser" -> UsersMethods.getAndSubscribeUser(call, result)

            "Channel#join" -> ChannelMethods.join(call, result)
            "Channel#leave" -> ChannelMethods.leave(call, result)
            "Channel#typing" -> ChannelMethods.typing(call, result)
            "Channel#declineInvitation" -> ChannelMethods.declineInvitation(call, result)
            "Channel#destroy" -> ChannelMethods.destroy(call, result)
            "Channel#getMessagesCount" -> ChannelMethods.getMessagesCount(call, result)
            "Channel#getUnconsumedMessagesCount" -> ChannelMethods.getUnconsumedMessagesCount(call, result)
            "Channel#getMembersCount" -> ChannelMethods.getMembersCount(call, result)

            "Channels#createChannel" -> ChannelsMethods.createChannel(call, result)
            "Channels#getChannel" -> ChannelsMethods.getChannel(call, result)
            "Channels#getPublicChannelsList" -> ChannelsMethods.getPublicChannelsList(call, result)
            "Channels#getUserChannelsList" -> ChannelsMethods.getUserChannelsList(call, result)
            "Channels#getMembersByIdentity" -> ChannelsMethods.getMembersByIdentity(call, result)

            "Member#getUserDescriptor" -> MemberMethods.getUserDescriptor(call, result)
            "Member# getAndSubscribeUser" -> MemberMethods.getAndSubscribeUser(call, result)

            "Members#addByIdentity" -> MembersMethods.addByIdentity(call, result)
            "Members#inviteByIdentity" -> MembersMethods.inviteByIdentity(call, result)
            "Members#removeByIdentity" -> MembersMethods.removeByIdentity(call, result)

            "Message#updateMessageBody" -> MessageMethods.updateMessageBody(call, result)

            "Messages#sendMessage" -> MessagesMethods.sendMessage(call, result)
            "Messages#removeMessage" -> MessagesMethods.removeMessage(call, result)
            "Messages#getMessagesBefore" -> MessagesMethods.getMessagesBefore(call, result)
            "Messages#getMessagesAfter" -> MessagesMethods.getMessagesAfter(call, result)
            "Messages#getLastMessages" -> MessagesMethods.getLastMessages(call, result)
            "Messages#getMessageByIndex" -> MessagesMethods.getMessageByIndex(call, result)
            "Messages#setLastConsumedMessageIndexWithResult" -> MessagesMethods.setLastConsumedMessageIndexWithResult(call, result)
            "Messages#advanceLastConsumedMessageIndexWithResult" -> MessagesMethods.advanceLastConsumedMessageIndexWithResult(call, result)
            "Messages#setAllMessagesConsumedWithResult" -> MessagesMethods.setAllMessagesConsumedWithResult(call, result)
            "Messages#setNoMessagesConsumedWithResult" -> MessagesMethods.setNoMessagesConsumedWithResult(call, result)

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
            val propertiesBuilder = ChatClient.Properties.Builder()
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

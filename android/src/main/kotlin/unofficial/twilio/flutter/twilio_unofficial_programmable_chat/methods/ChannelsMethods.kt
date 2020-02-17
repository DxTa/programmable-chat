package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Paginator
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.Mapper
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.PaginatorManager
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object ChannelsMethods {
    fun createChannel(call: MethodCall, result: MethodChannel.Result) {
        val friendlyName = call.argument<String>("friendlyName")
                ?: return result.error("ERROR", "Missing 'friendlyName'", null)

        val channelTypeValue = call.argument<String>("channelType")
                ?: return result.error("ERROR", "Missing 'channelType'", null)

        val channelType = when (channelTypeValue) {
            "PRIVATE" -> Channel.ChannelType.PRIVATE
            "PUBLIC" -> Channel.ChannelType.PUBLIC
            else -> null
        } ?: return result.error("ERROR", "Wrong value for 'channelType'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.createChannel(friendlyName, channelType, object : CallbackListener<Channel>() {
                override fun onSuccess(newChannel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.createChannel => onSuccess")
                    result.success(Mapper.channelToMap(newChannel))
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.createChannel => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun getChannel(call: MethodCall, result: MethodChannel.Result) {
        val channelSidOrUniqueName = call.argument<String>("channelSidOrUniqueName")
                ?: return result.error("ERROR", "Missing 'channelSidOrUniqueName'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSidOrUniqueName, object : CallbackListener<Channel>() {
            override fun onSuccess(newChannel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getChannel => onSuccess")
                result.success(Mapper.channelToMap(newChannel))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getChannel => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getPublicChannelsList(call: MethodCall, result: MethodChannel.Result) {
        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getPublicChannelsList(object : CallbackListener<Paginator<ChannelDescriptor>>() {
            override fun onSuccess(paginator: Paginator<ChannelDescriptor>) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getPublicChannelsList => onSuccess")
                val pageId = PaginatorManager.setPaginator(paginator)
                result.success(Mapper.paginatorToMap(pageId, paginator, "channelDescriptor"))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getPublicChannelsList => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getUserChannelsList(call: MethodCall, result: MethodChannel.Result) {
        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getUserChannelsList(object : CallbackListener<Paginator<ChannelDescriptor>>() {
            override fun onSuccess(paginator: Paginator<ChannelDescriptor>) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUserChannelsList => onSuccess")
                val pageId = PaginatorManager.setPaginator(paginator)
                result.success(Mapper.paginatorToMap(pageId, paginator, "channelDescriptor"))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUserChannelsList => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getMembersByIdentity(call: MethodCall, result: MethodChannel.Result) {
        val identity = call.argument<String>("identity")
                ?: return result.error("ERROR", "Missing 'identity'", null)

        val memberList = TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getMembersByIdentity(identity)
        val membersListMap = memberList?.map { Mapper.memberToMap(it) }
        result.success(membersListMap)
    }
}

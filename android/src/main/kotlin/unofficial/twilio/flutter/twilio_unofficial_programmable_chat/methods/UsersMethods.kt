package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Paginator
import com.twilio.chat.User
import com.twilio.chat.UserDescriptor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.Mapper
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.PaginatorManager
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object UsersMethods {
    fun getChannelUserDescriptors(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.users?.getChannelUserDescriptors(channelSid, object : CallbackListener<Paginator<UserDescriptor>>() {
            override fun onSuccess(paginator: Paginator<UserDescriptor>) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getChannelUserDescriptors => onSuccess")
                val pageId = PaginatorManager.setPaginator(paginator)
                result.success(Mapper.paginatorToMap(pageId, paginator, "userDescriptor"))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getChannelUserDescriptors => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getUserDescriptor(call: MethodCall, result: MethodChannel.Result) {
        val identity = call.argument<String>("identity")
                ?: return result.error("ERROR", "Missing 'identity'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.users?.getUserDescriptor(identity, object : CallbackListener<UserDescriptor>() {
            override fun onSuccess(userDescriptor: UserDescriptor) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getUserDescriptor => onSuccess")
                result.success(Mapper.userDescriptorToMap(userDescriptor))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getUserDescriptor => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getAndSubscribeUser(call: MethodCall, result: MethodChannel.Result) {
        val identity = call.argument<String>("identity")
                ?: return result.error("ERROR", "Missing 'identity'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.users?.getAndSubscribeUser(identity, object : CallbackListener<User>() {
            override fun onSuccess(user: User) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getAndSubscribeUser => onSuccess")
                result.success(Mapper.userToMap(user))
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("UsersMethods.getAndSubscribeUser => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }
}

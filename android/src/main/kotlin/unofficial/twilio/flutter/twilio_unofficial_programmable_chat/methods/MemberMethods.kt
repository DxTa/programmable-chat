package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Paginator
import com.twilio.chat.StatusListener
import com.twilio.chat.User
import com.twilio.chat.UserDescriptor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.Mapper
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.PaginatorManager
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object MemberMethods {
    fun getUserDescriptor(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)
        
        val memberSid = call.argument<String>("memberSid")
                ?: return result.error("ERROR", "Missing 'memberSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("MemberMethods.getUserDescriptor => onSuccess")
                    val member = channel.members.membersList.find { it.sid == memberSid }
                    if (member != null) {
                        member.getUserDescriptor(object : CallbackListener<UserDescriptor>() {
                            override fun onSuccess(userDescriptor: UserDescriptor) {
                                TwilioUnofficialProgrammableChatPlugin.debug("MemberMethods.getUserDescriptor (Member.getUserDescriptor) => onSuccess")
                                result.success(Mapper.userDescriptorToMap(userDescriptor))
                            }

                            override fun onError(errorInfo: ErrorInfo) {
                                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUserDescriptor => getUserDescriptor onError: $errorInfo")
                                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                            }
                        })
                    } else {
                        return result.error("ERROR", "No member found on channel '$channelSid' with sid '$memberSid'", null)
                    }
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUserDescriptor => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun getAndSubscribeUser(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        val memberSid = call.argument<String>("memberSid")
                ?: return result.error("ERROR", "Missing 'memberSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("MemberMethods.getAndSubscribeUser => onSuccess")
                    val member = channel.members.membersList.find { it.sid == memberSid }
                    if (member != null) {
                        member.getAndSubscribeUser(object : CallbackListener<User>() {
                            override fun onSuccess(user: User) {
                                TwilioUnofficialProgrammableChatPlugin.debug("MemberMethods.getAndSubscribeUser (Member.getAndSubscribeUser) => onSuccess")
                                result.success(Mapper.userToMap(user))
                            }

                            override fun onError(errorInfo: ErrorInfo) {
                                TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getAndSubscribeUser => getAndSubscribeUser onError: $errorInfo")
                                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                            }
                        })
                    } else {
                        return result.error("ERROR", "No member found on channel '$channelSid' with sid '$memberSid'", null)
                    }
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getAndSubscribeUser => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }
}

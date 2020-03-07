package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Message
import com.twilio.chat.StatusListener
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object MessageMethods {
    fun updateMessageBody(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)
        val messageIndex = call.argument<Long>("messageIndex")
                ?: return result.error("ERROR", "Missing 'messageIndex'", null)
        val body = call.argument<String>("body")
                ?: return result.error("ERROR", "Missing 'body'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Channels.getChannel) => onSuccess")

                channel.messages.getMessageByIndex(messageIndex, object : CallbackListener<Message>() {
                    override fun onSuccess(message: Message) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Messages.getMessageByIndex) => onSuccess")

                        message.updateMessageBody(body, object : StatusListener() {
                            override fun onSuccess() {
                                TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Message.updateMessageBody) => onSuccess")
                                result.success(null)
                            }

                            override fun onError(errorInfo: ErrorInfo) {
                                TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Message.updateMessageBody) => onError: $errorInfo")
                                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                            }
                        })
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Messages.getMessageByIndex) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessageMethods.updateMessageBody (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }
}

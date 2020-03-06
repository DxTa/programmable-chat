package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Message
import com.twilio.chat.StatusListener
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.Mapper
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object MessagesMethods {
    fun sendMessage(call: MethodCall, result: MethodChannel.Result) {
        val options = call.argument<Map<String, Any>>("options")
                ?: return result.error("ERROR", "Missing 'options'", null)
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        val messageOptions = Message.options()
        if (options["body"] != null) {
            messageOptions.withBody(options["body"] as String)
        }

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.sendMessage (Channels.getChannel) => onSuccess")

                channel.messages.sendMessage(messageOptions, object : CallbackListener<Message>() {
                    override fun onSuccess(message: Message) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.sendMessage (Message.sendMessage) => onSuccess")
                        result.success(Mapper.messageToMap(message))
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.sendMessage (Message.sendMessage) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.sendMessage (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }
    
    fun removeMessage(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)
        val messageIndex = call.argument<Long>("messageIndex")
                ?: return result.error("ERROR", "Missing 'messageIndex'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.removeMessage (Channels.getChannel) => onSuccess")

                channel.messages.getMessageByIndex(messageIndex, object : CallbackListener<Message>() {
                    override fun onSuccess(message: Message) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.removeMessage (Messages.getMessageByIndex) => onSuccess")

                        channel.messages.removeMessage(message, object : StatusListener() {
                            override fun onSuccess() {
                                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.removeMessage (Messages.removeMessage) => onSuccess")
                                result.success(null)
                            }

                            override fun onError(errorInfo: ErrorInfo) {
                                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.removeMessage (Messages.removeMessage) => onError: $errorInfo")
                                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                            }
                        })
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.removeMessage (Messages.getMessageByIndex) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.sendMessage (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getMessagesBefore(call: MethodCall, result: MethodChannel.Result) {
        val index = call.argument<Long>("index")
                ?: return result.error("ERROR", "Missing 'index'", null)
        val count = call.argument<Int>("count")
                ?: return result.error("ERROR", "Missing 'count'", null)
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesBefore (Channels.getChannel) => onSuccess")

                channel.messages.getMessagesBefore(index, count, object : CallbackListener<List<Message>>() {
                    override fun onSuccess(messages: List<Message>) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesBefore (Message.getMessagesBefore) => onSuccess")
                        val messagesListMap = messages?.map { Mapper.messageToMap(it) }
                        result.success(messagesListMap)
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesBefore (Message.getMessagesBefore) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesBefore (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getMessagesAfter(call: MethodCall, result: MethodChannel.Result) {
        val index = call.argument<Long>("index")
                ?: return result.error("ERROR", "Missing 'index'", null)
        val count = call.argument<Int>("count")
                ?: return result.error("ERROR", "Missing 'count'", null)
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesAfter (Channels.getChannel) => onSuccess")

                channel.messages.getMessagesAfter(index, count, object : CallbackListener<List<Message>>() {
                    override fun onSuccess(messages: List<Message>) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesAfter (Message.getMessagesAfter) => onSuccess")
                        val messagesListMap = messages?.map { Mapper.messageToMap(it) }
                        result.success(messagesListMap)
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesAfter (Message.getMessagesAfter) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessagesAfter (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getLastMessages(call: MethodCall, result: MethodChannel.Result) {
        val count = call.argument<Int>("count")
                ?: return result.error("ERROR", "Missing 'count'", null)
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getLastMessages (Channels.getChannel) => onSuccess")

                channel.messages.getLastMessages(count, object : CallbackListener<List<Message>>() {
                    override fun onSuccess(messages: List<Message>) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getLastMessages (Message.getLastMessages) => onSuccess")
                        val messagesListMap = messages.map { Mapper.messageToMap(it) }
                        result.success(messagesListMap)
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getLastMessages (Message.getLastMessages) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getLastMessages (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }

    fun getMessageByIndex(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)
        val messageIndex = call.argument<Long>("messageIndex")
                ?: return result.error("ERROR", "Missing 'messageIndex'", null)

        TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessageByIndex (Channels.getChannel) => onSuccess")

                channel.messages.getMessageByIndex(messageIndex, object : CallbackListener<Message>() {
                    override fun onSuccess(message: Message) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessageByIndex (Message.getMessageByIndex) => onSuccess")
                        result.success(Mapper.messageToMap(message))
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessageByIndex (Message.getMessageByIndex) => onError: $errorInfo")
                        result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                    }
                })
            }

            override fun onError(errorInfo: ErrorInfo) {
                TwilioUnofficialProgrammableChatPlugin.debug("MessagesMethods.getMessageByIndex (Channels.getChannel) => onError: $errorInfo")
                result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
            }
        })
    }
}

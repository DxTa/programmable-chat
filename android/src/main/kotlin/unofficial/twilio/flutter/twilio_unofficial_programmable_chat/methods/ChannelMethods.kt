package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ErrorInfo
import com.twilio.chat.StatusListener
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object ChannelMethods {
    fun join(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.join => onSuccess")
                    channel.join(object : StatusListener() {
                        override fun onSuccess() {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.join (Channel.join) => onSuccess")
                            result.success(null)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.join (Channel.join) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.join => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun leave(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.leave => onSuccess")
                    channel.leave(object : StatusListener() {
                        override fun onSuccess() {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.leave (Channel.leave) => onSuccess")
                            result.success(null)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.leave (Channel.leave) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.leave => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun typing(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.typing => onSuccess")
                    channel.typing()
                    result.success(null)
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.typing => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun declineInvitation(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.declineInvitation => onSuccess")
                    channel.declineInvitation(object : StatusListener() {
                        override fun onSuccess() {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.declineInvitation (Channel.declineInvitation) => onSuccess")
                            result.success(null)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.declineInvitation (Channel.declineInvitation) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.declineInvitation => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun destroy(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.destroy => onSuccess")
                    channel.destroy(object : StatusListener() {
                        override fun onSuccess() {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.destroy (Channel.destroy) => onSuccess")
                            result.success(null)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.destroy (Channel.destroy) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.destroy => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun getMessagesCount(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getMessagesCount => onSuccess")
                    channel.getMessagesCount(object : CallbackListener<Long>() {
                        override fun onSuccess(messageCount: Long) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getMessagesCount (Channel.getMessagesCount) => onSuccess: $messageCount")
                            result.success(messageCount)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getMessagesCount (Channel.getMessagesCount) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getMessagesCount => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun getUnconsumedMessagesCount(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getUnconsumedMessagesCount => onSuccess")
                    channel.getUnconsumedMessagesCount(object : CallbackListener<Long>() {
                        override fun onSuccess(unconsumedMessageCount: Long) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getUnconsumedMessagesCount (Channel.getUnconsumedMessagesCount) => onSuccess: $unconsumedMessageCount")
                            result.success(unconsumedMessageCount)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUnconsumedMessagesCount (Channel.getUnconsumedMessagesCount) => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getUnconsumedMessagesCount => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }

    fun getMembersCount(call: MethodCall, result: MethodChannel.Result) {
        val channelSid = call.argument<String>("channelSid")
                ?: return result.error("ERROR", "Missing 'channelSid'", null)

        try {
            TwilioUnofficialProgrammableChatPlugin.chatListener.chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getMembersCount => onSuccess")
                    channel.getMembersCount(object : CallbackListener<Long>() {
                        override fun onSuccess(membersCount: Long) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelMethods.getMembersCount (Channel.getMembersCount) => onSuccess: $membersCount")
                            result.success(membersCount)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getMembersCount (Channel.getMembersCount)  => onError: $errorInfo")
                            result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                        }
                    })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("ChannelsMethods.getMembersCount => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalArgumentException) {
            return result.error("IllegalArgumentException", err.message, null)
        }
    }
}

package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import com.twilio.chat.Channel
import com.twilio.chat.ChatClient
import com.twilio.chat.ChatClientListener
import com.twilio.chat.ErrorInfo
import com.twilio.chat.User
import io.flutter.plugin.common.EventChannel

class ChatListener(val token: String, val properties: ChatClient.Properties) : ChatClientListener {
    var events: EventChannel.EventSink? = null

    var chatClient: ChatClient? = null

    override fun onAddedToChannelNotification(p0: String?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onAddedToChannelNotification => NOT IMPLEMENTED")
    }

    override fun onChannelAdded(channel: Channel?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelAdded => sid = ${channel?.sid}")
        sendEvent("channelAdded", mapOf("channel" to Mapper.channelToMap(channel)))
    }

    override fun onChannelDeleted(channel: Channel?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelDeleted => sid = ${channel?.sid}")
        sendEvent("channelDeleted", mapOf("channel" to Mapper.channelToMap(channel)))
    }

    override fun onChannelInvited(channel: Channel?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelInvited => sid = ${channel?.sid}")
        sendEvent("channelInvited", mapOf("channel" to Mapper.channelToMap(channel)))
    }

    override fun onChannelJoined(channel: Channel?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelJoined => sid = ${channel?.sid}")
        sendEvent("channelJoined", mapOf("channel" to Mapper.channelToMap(channel)))
    }

    override fun onChannelSynchronizationChange(channel: Channel?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelSynchronizationChange => sid = ${channel?.sid}")
        sendEvent("channelSynchronizationChange", mapOf("channel" to Mapper.channelToMap(channel)))
    }

    override fun onChannelUpdated(channel: Channel?, updateReason: Channel.UpdateReason?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onChannelUpdated => sid = ${channel?.sid}")
        sendEvent("channelUpdated", mapOf("channel" to Mapper.channelToMap(channel), "channelUpdateReason" to updateReason.toString()))
    }

    override fun onClientSynchronization(synchronizationStatus: ChatClient.SynchronizationStatus?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onClientSynchronization => status = $synchronizationStatus")
        sendEvent("clientSynchronization", mapOf("synchronizationStatus" to synchronizationStatus.toString()))
    }

    override fun onConnectionStateChange(connectionState: ChatClient.ConnectionState?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onConnectionStateChange => status = $connectionState")
        sendEvent("connectionStateChange", mapOf("connectionState" to connectionState.toString()))
    }

    override fun onError(errorInfo: ErrorInfo) {
        sendEvent("error", null, errorInfo)
    }

    override fun onInvitedToChannelNotification(p0: String?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onInvitedToChannelNotification => NOT IMPLEMENTED, received: '$p0'")
    }

    override fun onNewMessageNotification(p0: String?, p1: String?, p2: Long) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onNewMessageNotification => NOT IMPLEMENTED, received: $p0, $p1, $p2")
    }

    override fun onNotificationFailed(errorInfo: ErrorInfo?) {
        sendEvent("notificationFailed", null, errorInfo)
    }

    override fun onNotificationSubscribed() {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onNotificationSubscribed")
        sendEvent("notificationSubscribed", null)
    }

    override fun onRemovedFromChannelNotification(p0: String?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onRemovedFromChannelNotification => NOT IMPLEMENTED, received: $p0")
    }

    override fun onUserSubscribed(p0: User?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onUserSubscribed => NOT IMPLEMENTED")
    }

    override fun onUserUnsubscribed(p0: User?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onUserUnsubscribed => NOT IMPLEMENTED")
    }

    override fun onUserUpdated(p0: User?, p1: User.UpdateReason?) {
        TwilioUnofficialProgrammableChatPlugin.debug("ChatListener.onUserUpdated => NOT IMPLEMENTED")
    }

    private fun sendEvent(name: String, data: Any?, e: ErrorInfo? = null) {
        val eventData = mapOf("name" to name, "data" to data, "error" to Mapper.errorInfoToMap(e))
        events?.success(eventData)
    }
}

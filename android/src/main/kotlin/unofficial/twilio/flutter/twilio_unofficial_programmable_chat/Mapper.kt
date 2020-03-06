package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.Channels
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Member
import com.twilio.chat.Members
import com.twilio.chat.Message
import com.twilio.chat.Messages
import com.twilio.chat.Paginator
import com.twilio.chat.User
import com.twilio.chat.UserDescriptor
import com.twilio.chat.Users
import io.flutter.plugin.common.EventChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.listeners.ChannelListener
import java.text.SimpleDateFormat
import java.util.Date

object Mapper {
    fun chatClientToMap(chatClient: ChatClient): Map<String, Any> {
        chatClient.channels
        return mapOf(
                "properties" to propertiesToMap(chatClient.properties),
                "channels" to channelsToMap(chatClient.channels),
                "myIdentity" to chatClient.myIdentity,
                "connectionState" to chatClient.connectionState.toString(),
                "users" to usersToMap(chatClient.users),
                "isReachabilityEnabled" to chatClient.isReachabilityEnabled
        )
    }

    private fun propertiesToMap(properties: ChatClient.Properties): Map<String, Any> {
        return mapOf(
                "region" to properties.region,
                "deferCA" to properties.deferCA
        )
    }

    private fun channelsToMap(channels: Channels): Map<String, Any> {
        val subscribedChannelsMap = channels.subscribedChannels.map { channelToMap(it) }
        return mapOf(
                "subscribedChannels" to subscribedChannelsMap
        )
    }

    fun channelToMap(channel: Channel?, compareChannel: Channel? = null): Map<String, Any>? {
        if (channel == null || compareChannel != null && channel.sid == compareChannel.sid) {
            return null
        }

        // Setting flutter event listener for the given channel if one does not yet exist.
        if (!TwilioUnofficialProgrammableChatPlugin.channelChannels.containsKey(channel.sid)) {
            TwilioUnofficialProgrammableChatPlugin.channelChannels[channel.sid] = EventChannel(TwilioUnofficialProgrammableChatPlugin.messenger, "twilio_unofficial_programmable_chat/${channel.sid}")
            TwilioUnofficialProgrammableChatPlugin.channelChannels[channel.sid]?.setStreamHandler(object : EventChannel.StreamHandler {
                override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
                    TwilioUnofficialProgrammableChatPlugin.debug("Mapper.channelToMap => EventChannel for Channel(${channel.sid}) attached")
                    TwilioUnofficialProgrammableChatPlugin.channelListeners[channel.sid] = ChannelListener(events)
                    channel.addListener(TwilioUnofficialProgrammableChatPlugin.channelListeners[channel.sid])
                }

                override fun onCancel(arguments: Any) {
                    TwilioUnofficialProgrammableChatPlugin.debug("Mapper.channelToMap => EventChannel for Channel(${channel.sid}) detached")
                    channel.removeListener(TwilioUnofficialProgrammableChatPlugin.channelListeners[channel.sid])
                    TwilioUnofficialProgrammableChatPlugin.channelListeners.remove(channel.sid)
                }
            })
        }

        return mapOf(
                "sid" to channel.sid,
                "friendlyName" to channel.friendlyName,
                "notificationLevel" to channel.notificationLevel.toString(),
                "type" to channel.type.toString(),
                "uniqueName" to channel.uniqueName,
                // TODO: This crashes it "attributes" to channel.attributes,
                "messages" to messagesToMap(channel.messages),
                "status" to channel.status.toString(),
                "members" to membersToMap(channel.members, channel),
                "synchronizationStatus" to channel.synchronizationStatus.toString(),
                "dateCreated" to dateToString(channel.dateCreatedAsDate),
                "createdBy" to channel.createdBy,
                "dateUpdated" to dateToString(channel.dateUpdatedAsDate),
                "lastMessageDate" to channel.lastMessageDate,
                "lastMessageIndex" to channel.lastMessageIndex
        )
    }

    private fun usersToMap(users: Users): Map<String, Any> {
        val subscribedUsersMap = users.subscribedUsers.map { userToMap(it) }
        return mapOf(
                "subscribedUsers" to subscribedUsersMap,
                "myUser" to userToMap(users.myUser)
        )
    }

    fun userToMap(user: User): Map<String, Any> {
        return mapOf(
                "friendlyName" to user.friendlyName,
                // TODO: This crashes it "attributes" to user.attributes,
                "identity" to user.identity,
                "isOnline" to user.isOnline,
                "isNotifiable" to user.isNotifiable,
                "isSubscribed" to user.isSubscribed
        )
    }

    private fun messagesToMap(messages: Messages): Map<String, Any> {
        return mapOf(
                "lastConsumedMessageIndex" to messages.lastConsumedMessageIndex
        )
    }

    fun messageToMap(message: Message): Map<String, Any?> {
        return mapOf(
                "sid" to message.sid,
                "author" to message.author,
                "dateCreated" to dateToString(message.dateCreatedAsDate),
                "messageBody" to message.messageBody,
                "channelSid" to message.channelSid,
                "channel" to channelToMap(message.channel),
                "messageIndex" to message.messageIndex,
                // TODO doesnt work "attributes" to message.attributes,
                "type" to message.type.toString(),
                "hasMedia" to message.hasMedia(),
                "media" to mediaToMap(message.media)
        )
    }

    private fun mediaToMap(media: Message.Media): Map<String, Any> {
        return mapOf(
                "sid" to media.sid,
                "fileName" to media.fileName,
                "type" to media.type,
                "size" to media.size
        )
    }
    
    fun membersToMap(members: Members, partOfChannel: Channel): Map<String, Any?> {
        val membersListMap = members.membersList.map { memberToMap(it, partOfChannel) }
        return mapOf(
                "channel" to channelToMap(members.channel, partOfChannel),
                "membersList" to membersListMap
        )
    }

    fun memberToMap(member: Member, partOfChannel: Channel? = null): Map<String, Any?> {
        return mapOf(
                "sid" to member.sid,
                "lastConsumedMessageIndex" to member.lastConsumedMessageIndex,
                "lastConsumptionTimestamp" to member.lastConsumptionTimestamp,
                "channel" to channelToMap(member.channel, partOfChannel),
                "identity" to member.identity,
                // TODO doesnt work "attributes" to channelDescriptor.attributes
                "type" to member.type.toString()
        )
    }

    inline fun <reified T> paginatorToMap(pageId: String, paginator: Paginator<T>, itemType: String): Map<String, Any> {
        val itemsListMap = paginator.items.map {
            when (itemType) {
                "userDescriptor" -> userDescriptorToMap(it as UserDescriptor)
                "channelDescriptor" -> channelDescriptorToMap(it as ChannelDescriptor)
                else -> throw Exception("Unknown item type received '$itemType'")
            }
        }

        return mapOf(
                "pageId" to pageId,
                "pageSize" to paginator.pageSize,
                "hasNextPage" to paginator.hasNextPage(),
                "items" to itemsListMap,
                "itemType" to itemType
        )
    }

    fun userDescriptorToMap(userDescriptor: UserDescriptor): Map<String, Any> {
        return mapOf(
                "friendlyName" to userDescriptor.friendlyName,
                // TODO doesnt work "attributes" to channelDescriptor.attributes
                "identity" to userDescriptor.identity,
                "isOnline" to userDescriptor.isOnline,
                "isNotifiable" to userDescriptor.isNotifiable
        )
    }

    fun channelDescriptorToMap(channelDescriptor: ChannelDescriptor): Map<String, Any> {
        return mapOf(
                "sid" to channelDescriptor.sid,
                "friendlyName" to channelDescriptor.friendlyName,
                // TODO doesnt work "attributes" to channelDescriptor.attributes
                "uniqueName" to channelDescriptor.uniqueName,
                "dateUpdated" to dateToString(channelDescriptor.dateUpdated),
                "dateCreated" to dateToString(channelDescriptor.dateCreated),
                "createdBy" to channelDescriptor.createdBy,
                "getMembersCount" to channelDescriptor.membersCount,
                "messagesCount" to channelDescriptor.messagesCount,
                "unconsumedMessagesCount" to channelDescriptor.unconsumedMessagesCount,
                "status" to channelDescriptor.status.toString()
        )
    }

    fun errorInfoToMap(e: ErrorInfo?): Map<String, Any?>? {
        if (e == null)
            return null
        return mapOf(
                "code" to e.code,
                "message" to e.message,
                "status" to e.status
        )
    }

    private fun dateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        return dateFormat.format(date)
    }
}

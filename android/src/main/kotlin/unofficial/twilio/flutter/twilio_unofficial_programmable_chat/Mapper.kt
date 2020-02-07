package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import com.twilio.chat.Channel
import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.Channels
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Member
import com.twilio.chat.Members
import com.twilio.chat.Messages
import com.twilio.chat.Paginator
import com.twilio.chat.User
import com.twilio.chat.Users

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
        return mapOf(
                "sid" to channel.sid,
                "friendlyName" to channel.friendlyName,
                "type" to channel.type.toString(),
                "uniqueName" to channel.uniqueName,
                // TODO: This crashes it "attributes" to channel.attributes,
                "messages" to messagesToMap(channel.messages),
                "status" to channel.status.toString(),
                "members" to membersToMap(channel.members, channel),
                "dateCreated" to channel.dateCreatedAsDate,
                "dateUpdated" to channel.dateUpdatedAsDate,
                "lastMessageDate" to channel.lastMessageDate,
                "lastMessageIndex" to channel.lastMessageIndex,
                "createdBy" to channel.createdBy
        )
    }

    private fun usersToMap(users: Users): Map<String, Any> {
        val subscribedUsersMap = users.subscribedUsers.map { userToMap(it) }
        return mapOf(
                "subscribedUsers" to subscribedUsersMap,
                "myUser" to userToMap(users.myUser)
        )
    }

    private fun userToMap(user: User): Map<String, Any> {
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

    private fun membersToMap(members: Members, partOfChannel: Channel): Map<String, Any?> {
        val membersListMap = members.membersList.map { memberToMap(it, partOfChannel) }
        return mapOf(
                "channel" to channelToMap(members.channel, partOfChannel),
                "membersList" to membersListMap
        )
    }

    private fun memberToMap(member: Member, partOfChannel: Channel): Map<String, Any?> {
        return mapOf(
                "sid" to member.sid,
                "lastConsumedMessageIndex" to member.lastConsumptionTimestamp,
                "channel" to channelToMap(member.channel, partOfChannel),
                "identity" to member.identity
        )
    }

    inline fun <reified T> paginatorToMap(pageId: String, paginator: Paginator<T>, realItemType: String? = null): Map<String, Any> {
        var itemType = realItemType
        if (itemType == null) {
            itemType = when (T::class) {
                ChannelDescriptor::class -> "channelDescriptor"
                else -> "unknown"
            }
        }

        val itemsListMap = paginator.items.map {
            when (itemType) {
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

    fun channelDescriptorToMap(channelDescriptor: ChannelDescriptor): Map<String, Any> {
        return mapOf(
//                "channels" to channelsToMap(channelDescriptor.);
                "sid" to channelDescriptor.sid,
                "friendlyName" to channelDescriptor.friendlyName,
                // TODO doesnt work "attributes" to channelDescriptor.attributes
                "uniqueName" to channelDescriptor.uniqueName,
                "dateUpdated" to channelDescriptor.dateUpdated,
                "dateCreated" to channelDescriptor.dateCreated,
                "createdBy" to channelDescriptor.createdBy,
                "membersCount" to channelDescriptor.membersCount,
                "messagesCount" to channelDescriptor.messagesCount,
                "unconsumedMessagesCount" to channelDescriptor.unconsumedMessagesCount,
                "status" to channelDescriptor.status
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
}

part of twilio_unofficial_programmable_chat;

/// Container for channel object.
class Channel {
  final String _sid;

  String _friendlyName;

  NotificationLevel _notificationLevel;

  final ChannelType _type;

  String _uniqueName;

  Map<String, dynamic> _attributes;

  Messages _messages;

  ChannelStatus _status;

  Members _members;

  ChannelSynchronizationStatus _synchronizationStatus;

  final DateTime _dateCreated;

  final String _createdBy;

  DateTime _dateUpdated;

  DateTime _lastMessageDate;

  int _lastMessageIndex;

  Channel(this._sid, this._createdBy, this._dateCreated, this._type)
      : assert(_sid != null),
        assert(_createdBy != null),
        assert(_dateCreated != null),
        assert(_type != null);

  /// Get unique identifier for this channel.
  ///
  /// This identifier can be used to get this [Channel] again using [Channels.getChannel].
  /// The channel SID is persistent and globally unique.
  String get sid {
    return _sid;
  }

  /// Get the friendly name of this channel.
  //
  /// Friendly name is a free-form text string, it is not unique and could be used for user-friendly channel name display in the UI.
  String get friendlyName {
    return _friendlyName;
  }

  /// The current user's notification level on this channel.
  ///
  /// This property reflects whether the user will receive push notifications for activity on this channel.
  NotificationLevel get notificationLevel {
    return _notificationLevel;
  }

  /// The channel type.
  ChannelType get type {
    return _type;
  }

  /// Get unique name of the channel.
  ///
  /// Unique name is similar to SID but can be specified by the user.
  String get uniqueName {
    return _uniqueName;
  }

  // attirbutes

  /// Get messages object that allows access to messages in the channel.
  Messages get messages {
    return _messages;
  }

  /// Get the current user's participation status on this channel.
  ChannelStatus get status {
    return _status;
  }

  /// Get members object that allows access to member roster in the channel.
  ///
  /// You need to synchronize the channel before you can call this method unless you just joined the channel, in which case it synchronizes automatically.
  Members get members {
    return _members;
  }

  /// Get the current synchronization status for channel.
  ChannelSynchronizationStatus get synchronizationStatus {
    return _synchronizationStatus;
  }

  /// Get creation date of the channel.
  DateTime get dateCreated {
    return _dateCreated;
  }

  /// Get creator of the channel.
  String get createdBy {
    return _createdBy;
  }

  /// Get update date of the channel.
  ///
  /// Update date changes when channel attributes, friendly name or unique name are modified. It will not change in response to messages posted or members added or removed.
  DateTime get dateUpdated {
    return _dateUpdated;
  }

  /// Get last message date in the channel.
  DateTime get lastMessageDate {
    return _lastMessageDate;
  }

  /// Get last message's index in the channel.
  int get lastMessageIndex {
    return _lastMessageIndex;
  }

  /// Construct from a map.
  factory Channel._fromMap(Map<String, dynamic> map) {
    var channel = Channel(
      map['sid'],
      map['createdBy'],
      DateTime.parse(map['dateCreated']),
      EnumToString.fromString(ChannelType.values, map['type']),
    );
    channel._updateFromMap(map);
    return channel;
  }

  /// Join the current user to this channel.
  ///
  /// Joining the channel is a prerequisite for sending and receiving messages in the channel. You can join the channel or you could be added to it by another channel member.
  ///
  /// You could also be invited to the channel by another member. In this case you will not be added to the channel right away but instead receive a [ChatClient.onChannelInvited] callback.
  /// You accept the invitation by calling [Channel.join] or decline it by calling [Channel.declineInvitation].
  Future<void> join() async {}

  /// Leave this channel.
  Future<void> leave() async {}

  /// Decline an invite to this channel.
  ///
  /// If a user is invited to the channel, they can choose to either [Channel.join] the channel to accept the invitation or [Channel.declineInvitation] to decline.
  Future<void> declineInvitation() async {}

  /// Destroy this channel.
  ///
  /// Note: this will delete the [Channel] and all associated metadata from the service instance. [Members] in the channel and all channel messages, including posted media will be lost.
  /// There is no undo for this operation!
  Future<void> destroy() async {}

  /// Get total number of messages in the channel.
  //
  /// This method is semi-realtime. This means that this data will be eventually correct, but will also possibly be incorrect for a few seconds.
  /// The Chat system does not provide real time events for counter values changes.
  ///
  /// So this is quite useful for any UI badges, but is not recommended to build any core application logic based on these counters being accurate in real time.
  ///
  /// This function performs an async call to service to obtain up-to-date message count.
  /// The retrieved value is then cached for 5 seconds so there is no reason to call this function more often than once in 5 seconds.
  Future<int> messageCount() async {}

  /// Get number of unconsumed messages in the channel.
  ///
  /// This method is semi-realtime. This means that this data will be eventually correct, but will also possibly be incorrect for a few seconds.
  /// The Chat system does not provide real time events for counter values changes.
  ///
  /// So this is quite useful for any “unread messages count” badges, but is not recommended to build any core application logic based on these counters being accurate in real time.
  ///
  /// This function performs an async call to service to obtain up-to-date message count.
  /// The retrieved value is then cached for 5 seconds so there is no reason to call this function more often than once in 5 seconds.
  Future<int> unconsumedMessageCount() async {}

  /// Get total number of members in the channel roster.
  ///
  /// This method is semi-realtime. This means that this data will be eventually correct, but will also possibly be incorrect for a few seconds.
  /// The Chat system does not provide real time events for counter values changes.
  ///
  /// So this is quite useful for any UI badges, but is not recommended to build any core application logic based on these counters being accurate in real time.
  ///
  /// This function performs an async call to service to obtain up-to-date member count.
  /// The retrieved value is then cached for 5 seconds so there is no reason to call this function more often than once in 5 seconds.
  Future<int> membersCount() async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _friendlyName = map['friendlyName'];
    _uniqueName = map['uniqueName'];
    _attributes = map['attributes'];
    _synchronizationStatus = EnumToString.fromString(ChannelSynchronizationStatus.values, map['synchronizationStatus']);
    _notificationLevel = EnumToString.fromString(NotificationLevel.values, map['notificationLevel']);

    final messagesMap = Map<String, dynamic>.from(map['messages']);
    _messages ??= Messages._fromMap(messagesMap, this);
    _messages._updateFromMap(messagesMap);

    _status = EnumToString.fromString(ChannelStatus.values, map['status']);

    final membersMap = Map<String, dynamic>.from(map['members']);
    _members ??= Members._fromMap(membersMap, this);
    _members._updateFromMap(membersMap);

    _dateUpdated = map['dateUpdated'];
    _lastMessageDate = map['lastMessageDate'];
    _lastMessageIndex = map['lastMessageIndex'];
  }

  // TODO: Event streams.
}

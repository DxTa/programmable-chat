part of twilio_unofficial_programmable_chat;

/// Contains channel information.
///
/// Unlike [Channel], this information won't be updated in realtime.
/// To have refreshed data, user should query channel descriptors again.
///
/// From the channel descriptor you could obtain full [Channel] object by calling [ChannelDescriptor.getChannel].
class ChannelDescriptor {
  final Channels _channels;

  final String _sid;

  final String _friendlyName;

  final String _uniqueName;

  final Map<String, dynamic> _attributes;

  final ChannelStatus _status;

  final DateTime _dateCreated;

  final DateTime _dateUpdated;

  final String _createdBy;

  final int _membersCount;

  final int _messagesCount;

  final int _unconsumedMessagesCount;

  /// Get channel SID.
  String get sid {
    return _sid;
  }

  /// Get channel friendly name.
  String get friendlyName {
    return _friendlyName;
  }

  /// Get channel unique name.
  String get uniqueName {
    return _uniqueName;
  }

  Map<String, dynamic> get attributes {
    return {..._attributes};
  }

  /// Get the current user's participation status on this channel.
  ///
  /// Since for [ChannelDescriptor]s the status is unknown this function will always return [ChannelStatus.UNKNOWN].
  ChannelStatus get status {
    return _status;
  }

  /// Get channel create date.
  DateTime get dateCreated {
    return _dateCreated;
  }

  /// Get channel update date.
  DateTime get dateUpdated {
    return _dateUpdated;
  }

  /// Get creator of the channel.
  String get createdBy {
    return _createdBy;
  }

  /// Get number of members.
  int get membersCount {
    return _membersCount;
  }

  /// Get number of messages.
  int get messagesCount {
    return _messagesCount;
  }

  /// Get number of unconsumed messages.
  int get unconsumedMessagesCount {
    return _unconsumedMessagesCount;
  }

  ChannelDescriptor(
    this._sid,
    this._friendlyName,
    this._uniqueName,
    this._attributes,
    this._status,
    this._dateCreated,
    this._dateUpdated,
    this._createdBy,
    this._membersCount,
    this._messagesCount,
    this._unconsumedMessagesCount,
    this._channels,
  )   : assert(_sid != null),
        assert(_friendlyName != null),
        assert(_uniqueName != null),
        assert(_attributes != null),
        assert(_status != null),
        assert(_dateCreated != null),
        assert(_dateUpdated != null),
        assert(_createdBy != null),
        assert(_membersCount != null),
        assert(_messagesCount != null),
        assert(_unconsumedMessagesCount != null),
        assert(_channels != null);

  /// Construct from a map.
  factory ChannelDescriptor._fromMap(Map<String, dynamic> map, Channels channels) {
    return ChannelDescriptor(
      map['sid'],
      map['friendlyName'],
      map['uniqueName'],
      map['attributes'],
      EnumToString.fromString(ChannelStatus.values, map['status']),
      DateTime.parse(map['dateCreated']),
      DateTime.parse(map['dateUpdated']),
      map['createdBy'],
      map['membersCount'],
      map['messagesCount'],
      map['unconsumedMessagesCount'],
      channels,
    );
  }

  /// Retrieve a full [Channel] object.
  Future<Channel> getChannel() async {
    return _channels.getChannel(_sid);
  }
}

part of twilio_unofficial_programmable_chat;

class ChannelDescriptor {
  final Channels _channels;

  final String _sid;

  String _friendlyName;

  String _uniqueName;

  Map<String, dynamic> _attributes;

  ChannelStatus _status;

  final DateTime _dateCreated;

  DateTime _dateUpdated;

  final String _createdBy;

  int _membersCount;

  int _messagesCount;

  int _unconsumedMessagesCount;

  String get sid {
    return _sid;
  }

  String get friendlyName {
    return _friendlyName;
  }

  String get uniqueName {
    return _uniqueName;
  }

  Map<String, dynamic> get attributes {
    return {..._attributes};
  }

  ChannelStatus get status {
    return _status;
  }

  DateTime get dateCreated {
    return _dateCreated;
  }

  DateTime get dateUpdated {
    return _dateUpdated;
  }

  String get createdBy {
    return _createdBy;
  }

  int get membersCount {
    return _membersCount;
  }

  int get messagesCount {
    return _messagesCount;
  }

  int get unconsumedMessagesCount {
    return _unconsumedMessagesCount;
  }

  ChannelDescriptor(this._sid, this._createdBy, this._dateCreated, this._channels)
      : assert(_sid != null),
        assert(_createdBy != null),
        assert(_dateCreated != null),
        assert(_channels != null);

  /// Construct from a map.
  factory ChannelDescriptor._fromMap(Map<String, dynamic> map, Channels channels) {
    var channelDescriptor = ChannelDescriptor(map['sid'], map['createdBy'], map['dateCreated'], channels);
    channelDescriptor._updateFromMap(map);
    return channelDescriptor;
  }

  Future<Channel> getChannel() async {
    return _channels.getChannel(_sid);
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _friendlyName = map['friendlyName'];
    _uniqueName = map['uniqueName'];
    _attributes = map['attributes'];
    _status = EnumToString.fromString(ChannelStatus.values, map['status']);
    _dateUpdated = map['dateUpdated'];
    _membersCount = map['membersCount'];
    _messagesCount = map['messagesCount'];
    _unconsumedMessagesCount = map['unconsumedMessagesCount'];
  }
}

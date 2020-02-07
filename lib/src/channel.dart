part of twilio_unofficial_programmable_chat;

class Channel {
  final String _sid;

  String _friendlyName;

  final ChannelType _type;

  String _uniqueName;

  Map<String, dynamic> _attributes;

  Messages _messages;

  ChannelStatus _status;

  Members _members;

  final DateTime _dateCreated;

  DateTime _dateUpdated;

  DateTime _lastMessageDate;

  int _lastMessageIndex;

  final String _createdBy;

  Channel(this._sid, this._createdBy, this._dateCreated, this._type)
      : assert(_sid != null),
        assert(_createdBy != null),
        assert(_dateCreated != null),
        assert(_type != null);

  /// Construct from a map.
  factory Channel._fromMap(Map<String, dynamic> map) {
    var channel = Channel(
      map['sid'],
      map['createdBy'],
      map['dateCreated'],
      EnumToString.fromString(ChannelType.values, map['type']),
    );
    channel._updateFromMap(map);
    return channel;
  }

  void join() {}

  void leave() {}

  void declineInvitation() {}

  Future<int> messageCount() async {}

  Future<int> unConsumedMessageCount() async {}

  Future<int> membersCount() async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _friendlyName = map['friendlyName'];
    _uniqueName = map['uniqueName'];
    _attributes = map['attributes'];

    _messages ??= Messages._fromMap(map['messages']);
    _messages._updateFromMap(map['messages']);

    _status = EnumToString.fromString(ChannelStatus.values, map['status']);

    _members ??= Members._fromMap(map['members'], this);
    _members._updateFromMap(map['members']);

    _dateUpdated = map['dateUpdated'];
    _lastMessageDate = map['lastMessageDate'];
    _lastMessageIndex = map['lastMessageIndex'];
  }

  // TODO: Event streams.
}

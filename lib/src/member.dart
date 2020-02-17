part of twilio_unofficial_programmable_chat;

/// Representation of a [Channel] member object.
class Member {
  final String _sid;

  int _lastConsumedMessageIndex;

  String _lastConsumptionTimestamp;

  Channel _channel;

  // TODO: Could be final?
  String _identity;

  Map<String, dynamic> _attributes;

  final MemberType _type;

  /// Returns unique identifier of a member on a [Channel].
  String get sid {
    return _sid;
  }

  /// Returns members last consumed message index for this channel.
  int get lastConsumedMessageIndex {
    return _lastConsumedMessageIndex;
  }

  /// Return members last consumed message timestamp for this channel.
  String get lastConsumptionTimestamp {
    return _lastConsumptionTimestamp;
  }

  /// Returns the channel this member belong<s to.
  Channel get channel {
    return _channel;
  }

  /// Returns user identity for the current member.
  String get identity {
    return _identity;
  }

  /// Returns [MemberType] of current member.
  MemberType get type {
    return _type;
  }

  Member(this._sid, this._type, this._channel)
      : assert(_sid != null),
        assert(_type != null);

  /// Construct from a map.
  factory Member._fromMap(Map<String, dynamic> map, {Channel channel}) {
    var member = Member(
      map['sid'],
      EnumToString.fromString(MemberType.values, map['type']),
      channel,
    );
    member._updateFromMap(map);
    return member;
  }

  Future<UserDescriptor> getUserDescriptor() async {}

  Future<User> getAndSubscribeUser() async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _lastConsumedMessageIndex = map['lastConsumedMessageIndex'];
    _lastConsumptionTimestamp = map['lastConsumptionTimestamp'];
    _identity = map['identity'];
    _attributes = map['attributes'];

    if (map['channel'] != null) {
      final channelMap = Map<String, dynamic>.from(map['channel']);
      _channel ??= Channel._fromMap(channelMap);
      _channel._updateFromMap(channelMap);
    }
  }
}

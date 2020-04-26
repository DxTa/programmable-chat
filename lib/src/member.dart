part of twilio_programmable_chat;

/// Representation of a [Channel] member object.
class Member {
  //#region Private API properties
  final String _sid;

  int _lastConsumedMessageIndex;

  String _lastConsumptionTimestamp;

  Channel _channel;

  // TODO: Could be final?
  String _identity;

  final MemberType _type;
  //#endregion

  //#region Public API properties
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
  //#endregion

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

  //#region Public API methods
  /// Return user descriptor for current member.
  Future<UserDescriptor> getUserDescriptor() async {
    throw UnimplementedError("getUserDescriptor");
//    try {
//      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Member#getUserDescriptor', {'memberSid': _sid, 'channelSid': _channel.sid});
//      final userDescriptorMap = Map<String, dynamic>.from(methodData);
//      return UserDescriptor._fromMap(userDescriptorMap);
//    } on PlatformException catch (err) {
//      throw TwilioUnofficialProgrammableChat._convertException(err);
//    }
  }

  /// Return subscribed user object for current member.
  Future<User> getAndSubscribeUser() async {
    try {
      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Member#getAndSubscribeUser', {'memberSid': _sid, 'channelSid': _channel.sid});
      final userMap = Map<String, dynamic>.from(methodData);
      return User._fromMap(userMap);
    } on PlatformException catch (err) {
      throw TwilioUnofficialProgrammableChat._convertException(err);
    }
  }

  /// Return custom attributes associated with this member.
  Future<Map<String, dynamic>> getAttributes() async {
    try {
      return await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Member#getAttributes', {'memberSid': _sid, 'channelSid': _sid}) as Map<String, dynamic>;
    } on PlatformException catch (err) {
      throw TwilioUnofficialProgrammableChat._convertException(err);
    }
  }

  /// Set attributes associated with this member.
  Future<Map<String, dynamic>> setAttributes(Map<String, dynamic> attributes) async {
    try {
      return await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Member#setAttributes', {'memberSid': _sid, 'channelSid': _sid, 'attributes': attributes}) as Map<String, dynamic>;
    } on PlatformException catch (err) {
      throw TwilioUnofficialProgrammableChat._convertException(err);
    }
  }
  //#endregion

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _lastConsumedMessageIndex = map['lastConsumedMessageIndex'];
    _lastConsumptionTimestamp = map['lastConsumptionTimestamp'];
    _identity = map['identity'];
    
    if (map['channel'] != null) {
      final channelMap = Map<String, dynamic>.from(map['channel']);
      _channel ??= Channel._fromMap(channelMap);
      _channel._updateFromMap(channelMap);
    }
  }
}

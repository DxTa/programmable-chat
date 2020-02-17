part of twilio_unofficial_programmable_chat;

/// Provides access to channel members and allows to add/remove members.
class Members {
  final Channel _channel;

  final List<Member> _membersList = [];

  /// Return channel this member list belongs to.
  Channel get channel {
    return _channel;
  }

  /// Obtain an array of members of this channel.
  List<Member> get membersList {
    return [...membersList];
  }

  Members(this._channel) : assert(_channel != null);

  /// Construct from a map.
  factory Members._fromMap(Map<String, dynamic> map, Channel channel) {
    var members = Members(channel);
    members._updateFromMap(map);
    return members;
  }

  /// Get a channel member by identity.
  Member getMember(String identity) {
    return _membersList.firstWhere((m) => m.identity == identity, orElse: () => null);
  }

  /// Add member to the channel.
  ///
  /// The member object could refer to the member in some different channel, the add will be performed based on the member's identity.
  /// If the member is already present in the channel roster an error will be returned.
  Future<void> add(Member member) async {
    return addByIdentity(member.identity);
  }

  /// Add specified username to this channel without inviting.
  ///
  /// If the member is already present in the channel roster an error will be returned.
  Future<void> addByIdentity(String identity) async {
    try {
      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Members#addByIdentity', {'identity': identity, 'channelSid': _channel.sid});
      final membersMap = Map<String, dynamic>.from(methodData);
      return _updateFromMap(membersMap);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Invite specified member to this channel.
  Future<void> invite(Member member) async {
    return inviteByIdentity(member.identity);
  }

  /// Invite specified username to this channel.
  Future<void> inviteByIdentity(String identity) async {
    try {
      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Members#removeByIdentity', {'identity': identity, 'channelSid': _channel.sid});
      final membersMap = Map<String, dynamic>.from(methodData);
      return _updateFromMap(membersMap);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Remove specified member from this channel.
  Future<void> remove(Member member) async {
    return removeByIdentity(member.identity);
  }

  /// Remove specified username from this channel.
  Future<void> removeByIdentity(String identity) async {
    try {
      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Members#removeByIdentity', {'identity': identity, 'channelSid': _channel.sid});
      final membersMap = Map<String, dynamic>.from(methodData);
      return _updateFromMap(membersMap);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    if (map['membersList'] != null) {
      final List<Map<String, dynamic>> membersList = map['membersList'].map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();
      for (final memberMap in membersList) {
        final member = _membersList.firstWhere(
          (m) => m._sid == memberMap['sid'],
          orElse: () => Member._fromMap(memberMap, channel: _channel),
        );
        if (!_membersList.contains(member)) {
          _membersList.add(member);
        }
        member._updateFromMap(memberMap);
      }
    }
  }
}

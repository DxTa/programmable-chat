part of twilio_unofficial_programmable_chat;

class Members {
  final Channel _channel;

  final List<Member> _membersList = [];

  Members(this._channel) : assert(_channel != null);

  /// Construct from a map.
  factory Members._fromMap(Map<String, dynamic> map, channel) {
    var members = Members(channel);
    members._updateFromMap(map);
    return members;
  }

  Future<Member> get(String identity) {}

  Future<void> add(Member member) {}

  Future<void> addByIdentity(String identity) {}

  Future<void> invite(Member member) {}

  Future<void> inviteByIdentity(String identity) {}

  Future<void> remove(Member member) {}

  Future<void> removeByIdentity(String identity) {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    if (map['membersList'] != null) {
      final List<Map<String, dynamic>> membersList = map['membersList'].map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();
      for (final memberMap in membersList) {
        final member = _membersList.firstWhere(
          (m) => m._sid == memberMap['sid'],
          orElse: () => Member._fromMap(memberMap, _channel),
        );
        if (!_membersList.contains(member)) {
          _membersList.add(member);
        }
        member._updateFromMap(memberMap);
      }
    }
  }
}

part of twilio_unofficial_programmable_chat;

class Users {
  final List<User> _subscribedUsers = [];

  User _myUser;

  Users();

  /// Construct from a map.
  factory Users._fromMap(Map<String, dynamic> map) {
    var users = Users();
    users._updateFromMap(map);
    return users;
  }

  Future<void> getUserDescriptor(String identity) async {

  }

  Future<void> getAndSubscribeUser(String identity) async {

  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    if (map['myUser'] != null) {
      final myUserMap = Map<String, dynamic>.from(map['myUser']);
      _myUser ??= User._fromMap(myUserMap);
      _myUser._updateFromMap(myUserMap);
    }
    if (map['subscribedUsers'] != null) {
      final List<Map<String, dynamic>> subscribedUsersList = map['subscribedUsers'].map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();
      for (final subscribedUserMap in subscribedUsersList) {
        final subscribedUser = _subscribedUsers.firstWhere(
              (c) => c._identity == subscribedUserMap['identity'],
          orElse: () => User._fromMap(subscribedUserMap),
        );
        if (!_subscribedUsers.contains(subscribedUser)) {
          _subscribedUsers.add(subscribedUser);
        }
        subscribedUser._updateFromMap(subscribedUserMap);
      }
    }
  }
}
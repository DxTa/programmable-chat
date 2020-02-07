part of twilio_unofficial_programmable_chat;

class User {
  String _friendlyName;

  Map<String, dynamic> _attributes;

  final String _identity;

  bool _isOnline;

  bool _isNotifiable;

  bool _isSubscribed;

  User(this._identity) : assert(_identity != null);

  /// Construct from a map.
  factory User._fromMap(Map<String, dynamic> map) {
    var user = User(map['identity']);
    user._updateFromMap(map);
    return user;
  }

  Future<void> unsubscribe() async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _friendlyName = map['friendlyName'];
    _attributes = map['attributes'];
    _isOnline = map['isOnline'];
    _isNotifiable = map['isNotifiable'];
    _isSubscribed = map['isSubscribed'];
  }
}

part of twilio_unofficial_programmable_chat;

class User {
  //#region Private API properties
  String _friendlyName;

  Map<String, dynamic> _attributes;

  final String _identity;

  bool _isOnline;

  bool _isNotifiable;

  bool _isSubscribed;
  //#endregion

  //#region Public API properties
  /// Method that returns the friendlyName from the user info.
  String get friendlyName {
    return _friendlyName;
  }

  /// Returns the identity of the user.
  String get identity {
    return _identity;
  }

  /// Return user's online status, if available
  bool get isOnline {
    return _isOnline;
  }

  /// Return user's push reachability.
  bool get isNotifiable {
    return _isNotifiable;
  }

  /// Check if this user receives real-time status updates.
  bool get isSubscribed {
    return _isSubscribed;
  }
  //#endregion

  User(this._identity) : assert(_identity != null);

  /// Construct from a map.
  factory User._fromMap(Map<String, dynamic> map) {
    var user = User(map['identity']);
    user._updateFromMap(map);
    return user;
  }

  //#region Public API methods
  Future<void> unsubscribe() async {
    try {
      // TODO: It is still in the [Users.subscribedUsers] list...
      await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('User#unsubscribe', {'identity': _identity });
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }
  //#endregion

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _friendlyName = map['friendlyName'];
    _attributes = map['attributes'];
    _isOnline = map['isOnline'];
    _isNotifiable = map['isNotifiable'];
    _isSubscribed = map['isSubscribed'];
  }
}

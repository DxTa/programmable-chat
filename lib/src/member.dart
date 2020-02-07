part of twilio_unofficial_programmable_chat;

class Member {
  final String _sid;

  int _lastConsumedMessageIndex;

  DateTime _lastConsumptionTime;

  final Channel _channel;

  // TODO: Could be final?
  String _identity;

  Member(this._sid, this._channel)
      : assert(_sid != null),
        assert(_channel != null);

  /// Construct from a map.
  factory Member._fromMap(Map<String, dynamic> map, channel) {
    var member = Member(map['sid'], channel);
    member._updateFromMap(map);
    return member;
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _lastConsumedMessageIndex = map['lastConsumedMessageIndex'];
    _lastConsumptionTime = map['lastConsumptionTime'];
    _identity = map['identity'];
  }
}

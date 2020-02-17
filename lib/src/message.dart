part of twilio_unofficial_programmable_chat;

class Message {
  final String _sid;

  final String _author;

  final DateTime _dateCreated;

  String _messageBody;

  final String _channelSid;

  final Channel _channel;

  final Messages _messages;

  final int _messageIndex;

  Map<String, dynamic> _attributes;

  final MessageType _type;

  final bool _hasMedia;

  final MessageMedia _media;

  Message(
    this._sid,
    this._author,
    this._dateCreated,
    this._channelSid,
    this._channel,
    this._messages,
    this._messageIndex,
    this._type,
    this._hasMedia,
    this._media,
  )   : assert(_sid != null),
        assert(_author != null),
        assert(_dateCreated != null),
        assert(_channelSid != null),
        assert(_channel != null),
        assert(_messages != null),
        assert(_messageIndex != null),
        assert(_type != null),
        assert(_hasMedia != null),
        assert(_hasMedia == true && _media != null);

  /// Construct from a map.
  factory Message._fromMap(Map<String, dynamic> map, Messages messages) {
    var message = Message(
      map['sid'],
      map['author'],
      DateTime.parse(map['dateCreated']),
      map['channelSid'],
      Channel._fromMap(map['channel']),
      messages,
      map['messageIndex'],
      EnumToString.fromString(MessageType.values, map['type']),
      map['hasMedia'],
      MessageMedia._fromMap(map['media']),
    );
    message._updateFromMap(map);
    return message;
  }

  Future<void> updateMessageBody(String body) async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _messageBody = map['messageBody'];
  }
}

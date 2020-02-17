part of twilio_unofficial_programmable_chat;

class Messages {
  final Channel _channel;

  int _lastConsumedMessageIndex;

  /// Return user last consumed message index for the channel.
  int get lastConsumedMessageIndex {
    return _lastConsumedMessageIndex;
  }

  Messages(this._channel) : assert(_channel != null);

  /// Construct from a map.
  factory Messages._fromMap(Map<String, dynamic> map, Channel channel) {
    var messages = Messages(channel);
    messages._updateFromMap(map);
    return messages;
  }

  /// Sends a message to the channel.
  Future<Message> sendMessage(MessageOptions options) async {
    try {
      final methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Messages#sendMessage', {
        'options': options._toMap(),
        'channelSid': _channel.sid,
      });
      final messageMap = Map<String, dynamic>.from(methodData);
      return Message._fromMap(messageMap, this);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  Future<void> removeMessage(Message message) async {}

  /// Fetch at most count messages including and prior to the specified index.
  Future<List<Message>> getMessagesBefore(int index, int count) async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Messages#getMessagesBefore', {
        'index': index,
        'count': count,
        'channelSid': _channel.sid,
      });
      final List<Map<String, dynamic>> messageMapList = methodData.map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();

      var messages = [];
      for (final messageMap in messageMapList) {
        messages.add(Message._fromMap(messageMap, this));
      }
      return messages;
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Fetch at most count messages including and subsequent to the specified index.
  Future<List<Message>> getMessagesAfter(int index, int count) async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Messages#getMessagesAfter', {
        'index': index,
        'count': count,
        'channelSid': _channel.sid,
      });
      final List<Map<String, dynamic>> messageMapList = methodData.map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();

      var messages = [];
      for (final messageMap in messageMapList) {
        messages.add(Message._fromMap(messageMap, this));
      }
      return messages;
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  Future<List<Message>> getLastMessages(int count) async {}

  Future<Message> getMessageByIndex(int index) async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _lastConsumedMessageIndex = map['lastConsumedMessageIndex'];
  }
}

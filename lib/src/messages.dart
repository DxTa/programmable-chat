part of twilio_unofficial_programmable_chat;

class Messages {
  int _lastConsumedMessageIndex;

  Messages();

  /// Construct from a map.
  factory Messages._fromMap(Map<String, dynamic> map) {
    var messages = Messages();
    messages._updateFromMap(map);
    return messages;
  }

  Future<Message> sendMessage() async {

  }

  Future<void> removeMessage() async {

  }

  Future<List<Message>> getMessagesBefore(int index, int count) async {

  }

  Future<List<Message>> getMessagesAfter(int index, int count) async {

  }

  Future<List<Message>> getLastMessages(int count) async {

  }

  Future<Message> getMessageByIndex(int index) async {

  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _lastConsumedMessageIndex = map['lastConsumedMessageIndex'];
  }
}
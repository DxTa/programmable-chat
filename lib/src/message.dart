part of twilio_unofficial_programmable_chat;

class Message {
  String _sid;

  String _author;

  DateTime _dateCreated;

  String _messageBody;

  String _channelSid;

  Channel _channel;

  Messages _messages;

  int _messageIndex;

  Map<String, dynamic> _attributes;

  Options _options;

  // TODO: Should be Type enum.
  dynamic _type;

  bool _hasMedia;

  Media _media;

  Future<void> updateMessageBody(String body) async {

  }
}
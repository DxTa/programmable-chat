import 'package:flutter/widgets.dart';
import 'package:rxdart/rxdart.dart';
import 'package:twilio_unofficial_programmable_chat/twilio_unofficial_programmable_chat.dart';

class ChatBloc {
  final ChatClient chatClient;

  ChatBloc({@required this.chatClient}) : assert(chatClient != null) {
  }

  void dispose() {
  }
}

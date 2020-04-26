import 'package:flutter/widgets.dart';
import 'package:twilio_programmable_chat/twilio_programmable_chat.dart';

class ChatBloc {
  final ChatClient chatClient;

  ChatBloc({@required this.chatClient}) : assert(chatClient != null);

  Future<void> test() async {
    var paginator = await chatClient.channels.getPublicChannelsList();
//    print(await chatClient.channels.createChannel("First channel", ChannelType.PUBLIC));
    print(paginator.items);
  }

  void dispose() {
  }
}

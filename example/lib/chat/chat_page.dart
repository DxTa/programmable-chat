import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:twilio_programmable_chat/twilio_programmable_chat.dart';
import 'package:twilio_programmable_chat_example/chat/chat_bloc.dart';
import 'package:twilio_programmable_chat_example/join/join_model.dart';

class ChatPage extends StatefulWidget {
  final ChatBloc chatBloc;

  const ChatPage({
    Key key,
    @required this.chatBloc,
  }) : super(key: key);

  static Widget create(BuildContext context, JoinModel joinModel) {
    return Provider<ChatBloc>(
      create: (BuildContext context) => ChatBloc(chatClient: joinModel.chatClient),
      child: Consumer<ChatBloc>(
        builder: (BuildContext context, ChatBloc chatBloc, _) => ChatPage(
          chatBloc: chatBloc,
        ),
      ),
      dispose: (BuildContext context, ChatBloc chatBloc) => chatBloc.dispose(),
    );
  }

  @override
  _ChatPageState createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  bool isLoading = false;

  List<ChannelDescriptor> items = [];

  Paginator<ChannelDescriptor> paginator;

  @override
  void initState() {
    super.initState();
    widget.chatBloc.test();
    widget.chatBloc.chatClient.channels.getPublicChannelsList().then((paginator) {
      this.paginator = paginator;
      items.addAll(paginator.items);
    });
  }

  Widget _channelList() {
    ScrollController _scrollController = ScrollController();
    _scrollController.addListener(() async {
      if (_scrollController.position.maxScrollExtent == _scrollController.position.pixels) {
        if (!isLoading) {
          if (paginator.hasNextPage) {
            isLoading = !isLoading;
            paginator = await paginator.requestNextPage();

            items.addAll(paginator.items);
          }
        }
      }
    });
    return ListView.builder(
      controller: _scrollController,
      itemCount: items.length,
      itemBuilder: (BuildContext context, int index) {
        return Row(
          children: [
            ListTile(
              title: Text(items[index].friendlyName),
              subtitle: Text(items[index].sid),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        appBar: AppBar(
          title: Text('Chat'),
        ),
        body: _channelList(),
      ),
    );
  }
}

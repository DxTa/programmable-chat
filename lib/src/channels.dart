part of twilio_unofficial_programmable_chat;

class Channels {
  final List<Channel> _subscribedChannels = [];

  Channels();

  /// Construct from a map.
  factory Channels._fromMap(Map<String, dynamic> map) {
    var channels = Channels();
    channels._updateFromMap(map);
    return channels;
  }

  Future<Channel> createChannel(String friendlyName, ChannelType channelType) async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Channels#createChannel', <String, Object>{'friendlyName': friendlyName, 'channelType': EnumToString.parse(channelType)});
      final channelMap = Map<String, dynamic>.from(methodData);
      return Channel._fromMap(channelMap);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  Future<Channel> getChannel(String channelSidOrUniqueName) async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Channels#getChannel', <String, Object>{'channelSidOrUniqueName': channelSidOrUniqueName});
      final channelMap = Map<String, dynamic>.from(methodData);
      return Channel._fromMap(channelMap);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  Future<Paginator<ChannelDescriptor>> getPublicChannelsList() async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Channels#getPublicChannelsList');
      final paginatorMap = Map<String, dynamic>.from(methodData);
      return Paginator<ChannelDescriptor>._fromMap(paginatorMap, passOn: {'channels': this});
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  Future<Channel> getUserChannelsList(String channelSidOrUniqueName) async {}

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    if (map['subscribedChannels'] != null) {
      final List<Map<String, dynamic>> subscribedChannelsList = map['subscribedChannels'].map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();
      for (final subscribedChannelMap in subscribedChannelsList) {
        final subscribedChannel = _subscribedChannels.firstWhere(
          (c) => c._sid == subscribedChannelMap['sid'],
          orElse: () => Channel._fromMap(subscribedChannelMap),
        );
        if (!_subscribedChannels.contains(subscribedChannel)) {
          _subscribedChannels.add(subscribedChannel);
        }
        subscribedChannel._updateFromMap(subscribedChannelMap);
      }
    }
  }
}

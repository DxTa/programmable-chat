part of twilio_unofficial_programmable_chat;

/// The event class for all [ChatClient] events.
class ChatClientEvent {
  /// The chat client.
  final ChatClient chatClient;

  /// The exception of the event.
  ///
  /// Can be null.
  final ErrorInfo exception;

  ChatClientEvent(this.chatClient, this.exception) : assert(chatClient != null);
}

class ChatClient {
  /// Stream for the native chat events.
  StreamSubscription<dynamic> _chatStream;

  Properties _properties;

  Channels _channels;

  ConnectionState _connectionState;

  String _myIdentity;

  Users _users;

  bool _isReachabilityEnabled;

  final StreamController<SynchronizationStatus> _onClientSynchronizationCtrl = StreamController<SynchronizationStatus>.broadcast();
  Stream<SynchronizationStatus> onClientSynchronization;

  final StreamController<ConnectionState> _onConnectionStateCtrl = StreamController<ConnectionState>.broadcast();
  Stream<ConnectionState> onConnectionState;

  final StreamController<ChatClientEvent> _onErrorCtrl = StreamController<ChatClientEvent>.broadcast();
  Stream<ChatClientEvent> onError;

  Properties get properties {
    return _properties;
  }

  Channels get channels {
    return _channels;
  }

  ConnectionState get connectionState {
    return _connectionState;
  }

  String get myIdentity {
    return _myIdentity;
  }

  Users get users {
    return _users;
  }

  bool get isReachabilityEnabled {
    return _isReachabilityEnabled;
  }

  ChatClient(EventChannel chatChannel) : assert(chatChannel != null) {
    _chatStream = chatChannel.receiveBroadcastStream(0).listen(_parseEvents);

    onClientSynchronization = _onClientSynchronizationCtrl.stream;
    onConnectionState = _onConnectionStateCtrl.stream;
    onError = _onErrorCtrl.stream;
  }

  /// Construct from a map.
  factory ChatClient._fromMap(Map<String, dynamic> map, EventChannel chatChannel) {
    var chatClient = ChatClient(chatChannel);
    chatClient._updateFromMap(map);
    return chatClient;
  }

  Future<void> updateToken(String token) async {
    try {
      return await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('ChatClient#updateToken', <String, Object>{'token': token});
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    print(map);
    _myIdentity = map['myIdentity'];
    _connectionState = EnumToString.fromString(ConnectionState.values, map['connectionState']);
    _isReachabilityEnabled = map['isReachabilityEnabled'];

    if (map['properties'] != null) {
      final propertiesMap = Map<String, dynamic>.from(map['properties']);
      _properties ??= Properties._fromMap(propertiesMap);
      _properties._updateFromMap(propertiesMap);
    }

    if (map['channels'] != null) {
      final channelsMap = Map<String, dynamic>.from(map['channels']);
      _channels ??= Channels._fromMap(channelsMap);
      _channels._updateFromMap(channelsMap);
    }

    if (map['users'] != null) {
      final usersMap = Map<String, dynamic>.from(map['users']);
      _users ??= Users._fromMap(usersMap);
      _users._updateFromMap(usersMap);
    }
  }

  /// Parse native chat client events to the right event streams.
  void _parseEvents(dynamic event) {
    final String eventName = event['name'];
    TwilioUnofficialProgrammableChat._log("ChatClient => Event '$eventName' => ${event["data"]}, error: ${event["error"]}");
    final data = Map<String, dynamic>.from(event['data']);

    // If no chatClient data is received, skip the event.
    if (data['chatClient'] != null) {
      final chatClientMap = Map<String, dynamic>.from(data['chatClient']);
      _updateFromMap(chatClientMap);
    }

    ErrorInfo exception;
    if (event['error'] != null) {
      final errorMap = Map<String, dynamic>.from(event['error'] as Map<dynamic, dynamic>);
      exception = ErrorInfo(errorMap['code'] as int, errorMap['message'], errorMap['status'] as int);
    }

    final chatClient = ChatClientEvent(this, exception);

    switch (eventName) {
      case 'clientSynchronization':
        var synchronizationStatus = EnumToString.fromString(SynchronizationStatus.values, data['synchronizationStatus']);
        assert(synchronizationStatus != null);
        _onClientSynchronizationCtrl.add(synchronizationStatus);
        break;
      case 'connectionStateChange':
        var connectionState = EnumToString.fromString(ConnectionState.values, data['connectionState']);
        assert(connectionState != null);
        _connectionState = connectionState;
        _onConnectionStateCtrl.add(connectionState);
        break;
      case 'error':
        assert(exception != null);
        _onErrorCtrl.add(chatClient);
        break;
      default:
        TwilioUnofficialProgrammableChat._log("Event '$eventName' not yet implemented");
        break;
    }
  }
}

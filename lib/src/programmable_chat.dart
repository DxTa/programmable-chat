part of twilio_unofficial_programmable_chat;

/// Entry point for the Twilio Unofficial Programmable Dart.
class TwilioUnofficialProgrammableChat {
  static const MethodChannel _methodChannel = MethodChannel('twilio_unofficial_programmable_chat');

  static const EventChannel _chatChannel = EventChannel('twilio_unofficial_programmable_chat/room');

  static const EventChannel _loggingChannel = EventChannel('twilio_unofficial_programmable_chat/logging');

  static StreamSubscription _loggingStream;

  static bool _dartDebug = false;

  /// Internal logging method for dart.
  static void _log(dynamic msg) {
    if (_dartDebug) {
      print('[   DART   ] $msg');
    }
  }

  /// Enable debug logging.
  ///
  /// For native logging set [native] to `true` and for dart set [dart] to `true`.
  static Future<void> debug({bool dart = false, bool native = false}) async {
    assert(dart != null);
    assert(native != null);
    _dartDebug = dart;
    await _methodChannel.invokeMethod('debug', {'native': native});
    if (native && _loggingStream == null) {
      _loggingStream = _loggingChannel.receiveBroadcastStream().listen((dynamic event) {
        if (native) {
          print('[  NATIVE  ] $event');
        }
      });
    } else if (!native && _loggingStream != null) {
      await _loggingStream.cancel();
      _loggingStream = null;
    }
  }

  /// Create to a [ChatClient].
  static Future<ChatClient> create(String token, Properties properties) async {
    assert(token != null);
    assert(token != '');
    assert(properties != null);

    try {
      final methodData = await _methodChannel.invokeMethod('create', <String, Object>{'token': token, 'properties': properties._toMap()});
      final chatClientMap = Map<String, dynamic>.from(methodData);
      return ChatClient._fromMap(chatClientMap, _chatChannel);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }

  }
}

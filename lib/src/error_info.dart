part of twilio_unofficial_programmable_chat;

/// Twilio Chat SDK Exception.
class ErrorInfo implements Exception {
  static int CLIENT_ERROR = 0;
  static int CANNOT_GET_MESSAGE_BY_INDEX = -4;
  static int MISMATCHING_TOKEN_UPDATE = -5;
  static int CHANNEL_NOT_SYNCHRONIZED = -6;

  /// Code indicator, should match any of the [ErrorInfo] static properties.
  final int code;

  /// Message containing a short explanation.
  final String message;

  final int status;

  ErrorInfo(this.code, this.message, this.status)
      : assert(code != null),
        assert(message != null),
        assert(status != null);

  @override
  String toString() {
    return 'ErrorInfo: code: $code, message: $message, status: $status';
  }
}

part of twilio_programmable_chat;

class MessageMedia {
  final String _sid;

  final String _fileName;

  final String _type;

  final int _size;

  /// Get SID of media stream.
  String get sid {
    return _sid;
  }

  /// Get file name of media stream.
  String get fileName {
    return _fileName;
  }

  /// Get mime-type of media stream.
  String get type {
    return _type;
  }

  /// Get size of media stream.
  int get size {
    return _size;
  }

  MessageMedia(
    this._sid,
    this._fileName,
    this._type,
    this._size,
  )   : assert(_sid != null),
        assert(_fileName != null),
        assert(_type != null),
        assert(_size != null);

  /// Construct from a map.
  factory MessageMedia._fromMap(Map<String, dynamic> map) {
    if (map == null) {
      return null;
    }
    return MessageMedia(
      map['sid'],
      map['fileName'],
      map['type'],
      map['size'],
    );
  }

  /// Save media content stream that could be streamed or downloaded by client.
  ///
  /// Provided stream could be any subclass of java.io.OutputStream, including ByteArrayOutputStream, so you could download media into memory for further processing.
  Future<void> download() async {

  }
}

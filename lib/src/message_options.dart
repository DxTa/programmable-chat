part of twilio_programmable_chat;

class MessageOptions {
  String _body;

  Map<String, dynamic> _attributes;

  File _input;

  String _mimeType;

  String _filename;

  /// Create message with given body text.
  ///
  /// If you specify [MessageOptions.withBody] then you will not be able to specify [MessageOptions.withMedia] because they are mutually exclusive message types.
  /// Created message type will be [MessageType.TEXT].
  void withBody(String body) {
    assert(body != null);
    if (_input != null) {
      throw Exception('MessageOptions.withMedia has already been specified');
    }
    _body = body;
  }

  /// Set new message attributes.
  void withAttributes(Map<String, dynamic> attributes) {
    _attributes = attributes;
  }

  /// Create message with given media stream.
  ///
  /// If you specify [MessageOptions.withMedia] then you will not be able to specify [MessageOptions.withBody] because they are mutually exclusive message types. Created message type will be [MessageType.MEDIA].
  void withMedia(File input, String mimeType) {
    assert(input != null);
    assert(mimeType != null);
    if (_body != null) {
      throw Exception('MessageOptions.withBody has already been specified');
    }
    _input = input;
    _mimeType = mimeType;
  }

  /// Provide optional filename for media.
  void withMediaFileName(String filename) {
    assert(filename != null);
    _filename = filename;
  }

  void withMediaProgressListener({
    void Function() onStarted,
    void Function(int bytes) onProgress,
    void Function(String mediaSid) onCompleted,
  }) {
    // uniqueID genereren
  }

  /// Create map from properties.
  Map<String, dynamic> _toMap() {
    return {
      'body': _body,
      // TODO: 'attributes': _attributes,
      'input': _input?.path,
      'mimeType': _mimeType,
      'filename': _filename,
    };
  }
}

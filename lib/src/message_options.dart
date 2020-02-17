part of twilio_unofficial_programmable_chat;

class MessageOptions {
  String _body;

  Map<String, dynamic> _attributes;

  /// Create message with given body text.
  ///
  /// If you specify [MessageOptions.withBody] then you will not be able to specify [MessageOptions.withMedia] because they are mutually exclusive message types.
  /// Created message type will be [MessageType.TEXT].
  void withBody(String body) {
    assert(body != null);
    _body = body;
  }

  /// Set new message attributes.
  void withAttributes(Map<String, dynamic> attributes) {
    _attributes = attributes;
  }

  /// Create map from properties.
  Map<String, dynamic> _toMap() {
    return {
      'body': _body,
      // TODO: 'attributes': _attributes,
    };
  }
}
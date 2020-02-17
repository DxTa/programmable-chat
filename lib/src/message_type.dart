part of twilio_unofficial_programmable_chat;

/// Represents the type of message.
enum MessageType {
  /// [Message] is a regular text message.
  TEXT,

  /// [Message] is a media message.
  ///
  /// [Message.getMedia] will return the associated media object.
  MEDIA,
}

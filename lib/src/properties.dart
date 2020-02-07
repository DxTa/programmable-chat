part of twilio_unofficial_programmable_chat;

/// Represents options when connecting to a [ChatClient].
class Properties {
  String _region = 'us1';

  bool _deferCA = false;

  Properties();

  void region(String region) {
    assert(region != null);
    _region = region;
  }

  void deferCertificateTrustToPlatform(bool defer) {
    _deferCA = defer;
  }

  /// Construct from a map.
  factory Properties._fromMap(Map<String, dynamic> map) {
    var properties = Properties();
    properties._updateFromMap(map);
    return properties;
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    _region = map['region'];
    _deferCA = map['deferCA'];
  }

  /// Create map from properties.
  Map<String, Object> _toMap() {
    return {
      'region': _region,
      'deferCA': _deferCA,
    };
  }
}

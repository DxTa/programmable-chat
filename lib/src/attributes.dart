// @dart=2.9

part of twilio_programmable_chat;

class Attributes {
  //#region Private API properties
  final AttributesType _type;

  final String _json;
  //#endregion

  /// Returns attributes type
  AttributesType get type => _type;

  Attributes(this._type, this._json)
      : assert(_type != null),
        assert(_json != null);

  factory Attributes.fromMap(Map<String, dynamic> map) {
    var type = AttributesType.values.firstWhere((type) {
      return type.toString().split('.')[1] == (map['type']);
    });
    var json = map['data'];
    return Attributes(type, json);
  }

  Map<String, dynamic> getJSONObject() {
    if (type != AttributesType.OBJECT) {
      return null;
    } else {
      return jsonDecode(_json);
    }
  }

  List<Map<String, dynamic>> getJSONArray() {
    if (type != AttributesType.ARRAY) {
      return null;
    } else {
      return List<Map<String, dynamic>>.from(jsonDecode(_json));
    }
  }

  String getString() {
    if (type != AttributesType.STRING) {
      return null;
    } else {
      return _json;
    }
  }

  num getNumber() {
    if (type != AttributesType.NUMBER) {
      return null;
    } else {
      return num.tryParse(_json);
    }
  }

  bool getBoolean() {
    if (type != AttributesType.BOOLEAN) {
      return null;
    } else {
      return _json == 'true';
    }
  }
}

enum AttributesType {
  OBJECT,
  ARRAY,
  STRING,
  NUMBER,
  BOOLEAN,
  NULL,
}

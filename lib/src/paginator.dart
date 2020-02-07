part of twilio_unofficial_programmable_chat;

class Paginator<T> {
  final Map<String, dynamic> _passOn;

  final String _pageId;

  final List<T> _items = [];

  final int _pageSize;

  final bool _hasNextPage;

  List<T> get items {
    return [..._items];
  }

  int get pageSize {
    return _pageSize;
  }

  bool get hasNextPage {
    return _hasNextPage;
  }

  Paginator(this._pageId, this._pageSize, this._hasNextPage, this._passOn)
      : assert(_pageId != null),
        assert(_pageSize != null),
        assert(_hasNextPage != null);

  /// Construct from a map.
  factory Paginator._fromMap(Map<String, dynamic> map, {Map<String, dynamic> passOn}) {
    var paginator = Paginator(map['pageId'], map['pageSize'], map['hasNextPage'], passOn);
    paginator._updateFromMap(map);
    return paginator;
  }

  Future<Paginator<T>> requestNextPage() async {
    try {
      var methodData = await TwilioUnofficialProgrammableChat._methodChannel.invokeMethod('Paginator#requestNextPage', <String, Object>{'pageId': _pageId});
      final paginatorMap = Map<String, dynamic>.from(methodData);
      return Paginator<T>._fromMap(paginatorMap, passOn: _passOn);
    } on PlatformException catch (err) {
      if (err.code == 'ERROR') {
        rethrow;
      }
      // TODO it can throw IllegalStateException. Doesn't get handled here
      throw ErrorInfo(int.parse(err.code), err.message, err.details as int);
    }
  }

  /// Update properties from a map.
  void _updateFromMap(Map<String, dynamic> map) {
    if (map['items'] != null) {
      final itemType = map['type'];
      final List<Map<String, dynamic>> itemsList = map['items'].map<Map<String, dynamic>>((r) => Map<String, dynamic>.from(r)).toList();
      for (final itemMap in itemsList) {
        var item;
        switch (itemType) {
          case 'channelDescriptor':
            assert(_passOn['channels'] != null);
            item = (_items as List<ChannelDescriptor>).firstWhere(
              (c) => c._sid == itemMap['sid'],
              orElse: () => ChannelDescriptor._fromMap(itemMap, _passOn['channels']),
            );
            break;
        }
        assert(item != null);
        if (!_items.contains(item)) {
          _items.add(item);
        }
        item._updateFromMap(itemMap);
      }
    }
  }
}

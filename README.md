## [Notice: Twilio Sunsetting Programmable Chat on July 25, 2022](https://www.twilio.com/changelog/programmable-chat-end-of-life)

# twilio_programmable_chat
Flutter plugin for [Twilio Programmable Chat](https://www.twilio.com/chat?utm_source=opensource&utm_campaign=flutter-plugin), which enables you to build a chat application. \
This Flutter plugin is a community-maintained project for [Twilio Programmable Chat](https://www.twilio.com/vidchateo?utm_source=opensource&utm_campaign=flutter-plugin) and not maintained by Twilio. If you have any issues, please file an issue instead of contacting support.

This package is currently work-in-progress and should not be used for production apps. We can't guarantee that the current API implementation will stay the same between versions, until we have reached v1.0.0.

# Example
Check out our comprehensive [example](https://gitlab.com/twilio-flutter/programmable-chat/tree/master/example) provided with this plugin.

## Join the community
If you have any question or problems, please join us on [Discord](https://discord.gg/MWnu4nW)

## FAQ
Read the [Frequently Asked Questions](https://gitlab.com/twilio-flutter/programmable-chat/blob/master/FAQ.md) first before creating a new issue.

## Supported platforms
* Android
* iOS
* ~~Web~~ (not yet)

### Initializing Chat Client
Call `TwilioProgrammableChat.create()` in your Flutter application to create a `ChatClient`. Once synchronized, you can start joining channels and sending messages.
```dart
ChatClient _chatClient;

void _onClientSynchronization(ChatClientSynchronizationStatus event) {
  print('ChatClient synchoronization status change: $event');
  if (ChatClientSynchronizationStatus.COMPLETED == event) {
    print('ChatClient is synchronized');
  }
  if (ChatClientSynchronizationStatus.FAILED == event) {
    print('Failed synchronization of ChatClient');
  }
}

void _onError(ErrorInfo event) {
  print('Received error: ${event.message}');
}

void init() async {
  var properties = Properties(
    region: region, // Optional region.
  );
  _chatClient = await TwilioProgrammableChat.create(token, properties);
  _chatClient.onClientSynchronization.listen(_onClientSynchronization);
  _chatClient.onError.listen(_onError);
}
```

### Creating a channel
Before you are able to send messages, you need a Channel to send them in. 
```dart
var channel = await _chatClient.channels.createChannel('friendlyName', ChannelType.PUBLIC);
```

### Joining a a channel
Once you have created a channel, a user can join that channel to receive or send messages.
```dart
var channel = await _chatClient.channels.getChannel('channelSidOrUniqueName');
await channel.join();
```

You **must** pass the [Access Token](https://gitlab.com/twilio-flutter/programmable-video/-/tree/master/programmable_video/README.md#access-tokens) when connecting to a Room.

## Enable debug logging
Using the `TwilioProgrammableChat` class, you can enable native and dart logging of the plugin and enable the SDK logging.

```dart
var sdkEnabled = true;
var nativeEnabled = true;
var dartEnabled = true;
TwilioProgrammableVideo.debug(
  native: nativeEnabled,
  dart: dartEnabled,
  sdk: sdkEnabled,
);
```

## Access Tokens
Keep in mind, you can't generate access tokens for programmable-chat using the [TestCredentials](https://www.twilio.com/docs/iam/test-credentials#supported-resources), make use of the LIVE credentials.

You can easily generate an access token in the Twilio dashboard with the [Testing Tools](https://www.twilio.com/console/video/project/testing-tools) to start testing your code. But we recommend you setup a backend to generate these tokens for you and secure your Twilio credentials. Like we do in our [example app](https://gitlab.com/twilio-flutter/programmable-video/-/tree/master/programmable_video/example).

# Push Notifications

## General

**Required steps for enabling push notifications in iOS and Android apps apply.**

Some links for more information:
- [Push notification configuration](https://www.twilio.com/docs/chat/push-notification-configuration)
- [iOS info](https://www.twilio.com/docs/chat/ios/push-notifications-ios)
- [Android info](https://www.twilio.com/docs/chat/android/push-notifications)

**Note:** At this time it seems that both the Android and iOS SDKs only support registering for push notifications once per connection.

## Platform Specifics

The iOS and Android SDKs take different approaches to push notifications. **Notable differences include:**

## iOS
1. The iOS SDK uses APNs whereas Android uses FCM.
2. The iOS SDK handles receiving and displaying push notifications.
3. Due to the fact that APNs token format has changed across iOS implementations, we have elected to retrieve the token from the OS ourselves at time of registration rather than attempting to anticipate what method of encoding might be used when transferring the token back and forth across layers of the app, or what format the token might take.

## Android
1. The Android SDK offers options for GCM and FCM. As GCM has largely been deprecated by Google, we have elected to only handle FCM.
2. The Android SDK does not receive messages or handle notifications.
3. Rather than introducing a dependency on `firebase` to the plugin, we have elected to leave token retrieval, message and notification handling to the user of the plugin.
    - An example of this can be seen in the example app.
    - Notable parts of the implementation in the example app include:
      * `main.dart` - which configures `FirebaseMessaging` with message handlers,
       initializes `FlutterLocalNotificationsPlugin`, and creates a notification channel.
      * `chat_bloc.dart` - which retrieves tokens (if on Android), and registers and unregisters for notifications.


# Development and Contributing
Interested in contributing? We love merge requests! See the [Contribution](https://gitlab.com/twilio-flutter/programmable-chat/blob/master/CONTRIBUTING.md) guidelines.

# Contributions By

[![HomeX - Home Repairs Made Easy](https://homex.com/static/brand/homex-logo-green.svg)](https://homex.com)

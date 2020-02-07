import Flutter
import UIKit

public class SwiftTwilioUnofficialProgrammableChatPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "twilio_unofficial_programmable_chat", binaryMessenger: registrar.messenger())
    let instance = SwiftTwilioUnofficialProgrammableChatPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}

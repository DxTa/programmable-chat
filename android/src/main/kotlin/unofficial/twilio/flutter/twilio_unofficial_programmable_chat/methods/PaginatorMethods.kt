package unofficial.twilio.flutter.twilio_unofficial_programmable_chat.methods

import com.twilio.chat.CallbackListener
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Paginator
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.Mapper
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.PaginatorManager
import unofficial.twilio.flutter.twilio_unofficial_programmable_chat.TwilioUnofficialProgrammableChatPlugin

object PaginatorMethods {
    fun requestNextPage(call: MethodCall, result: MethodChannel.Result) {
        val pageId = call.argument<String>("pageId")
                ?: return result.error("ERROR", "Missing 'pageId'", null)
        val itemType = call.argument<String>("itemType")
                ?: return result.error("ERROR", "Missing 'itemType'", null)

        val paginator = PaginatorManager.getPaginator<Any>(pageId)
        try {
            paginator.requestNextPage(object : CallbackListener<Paginator<Any>>() {
                override fun onSuccess(paginator: Paginator<Any>) {
                    TwilioUnofficialProgrammableChatPlugin.debug("PaginatorMethods.requestNextPage => onSuccess")
                    val pageId = PaginatorManager.setPaginator(paginator)
                    result.success(Mapper.paginatorToMap(pageId, paginator, itemType))
                }

                override fun onError(errorInfo: ErrorInfo) {
                    TwilioUnofficialProgrammableChatPlugin.debug("PaginatorMethods.requestNextPage => onError: $errorInfo")
                    result.error("${errorInfo.code}", errorInfo.message, errorInfo.status)
                }
            })
        } catch (err: IllegalStateException) {
            return result.error("IllegalStateException", err.message, null)
        }
    }
}

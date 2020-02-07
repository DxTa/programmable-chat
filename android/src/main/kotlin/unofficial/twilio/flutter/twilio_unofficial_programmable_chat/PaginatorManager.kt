package unofficial.twilio.flutter.twilio_unofficial_programmable_chat

import com.twilio.chat.Paginator
import java.util.UUID
import kotlin.collections.HashMap

object PaginatorManager {
    val paginators: HashMap<String, Object> = hashMapOf()

    fun <T> setPaginator(paginator: Paginator<T>): String {
        val pageId = UUID.randomUUID().toString()
        paginators[pageId] = paginator
        return pageId
    }

    fun <T> getPaginator(pageId: String): Paginator<T> {
        if (!paginators.containsKey(pageId)) {
            throw Exception("Could not find paginator with pageId '$pageId'")
        }
        return paginators[pageId]
    }
}

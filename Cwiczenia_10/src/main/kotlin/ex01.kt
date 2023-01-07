import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.selectUnbiased
import kotlin.random.Random

fun main() = runBlocking{

    val numberOfMiddlemen= 5
    val numberOfProducers = 50
    val numberOfConsumers = 20
    val middlemenChannels = Array(numberOfMiddlemen) { _ ->
        val newChannel = Channel<String>()
        newChannel
    }

    val consumerChannel = Channel<String>()

    suspend fun producer(number: Number) {
        while (true) {
            selectUnbiased {
                middlemenChannels.forEachIndexed { i, channel ->
                    channel.onSend("piece$number") {
                        println("Producer $number sent an item piece$number to channel $i")
                    }
                }
            }
            delay(Random.nextLong(0, 2000))
        }
    }

    suspend fun middleman(number: Number) {
        while (true) {
            val piece = selectUnbiased {
                middlemenChannels.forEachIndexed { i, channel ->
                    channel.onReceive {
                        println("Middleman $number received an item $it from channel $i")
                        it
                    }
                }
            }
            println("Middleman $number forwarded an item $piece")
            consumerChannel.send(piece)
        }
    }

    suspend fun consumer(number: Number) {
        while (true) {
            val piece = consumerChannel.receive()
            println("Consumer $number received an item $piece")
            delay(Random.nextLong(0, 2000))
        }
    }

    for (i in 0 until numberOfProducers) {
        launch { producer(i) }
    }
    for (i in 0 until numberOfMiddlemen) {
        launch { middleman(i) }
    }
    for (i in 0 until numberOfConsumers) {
        launch { consumer(i) }
    }
}

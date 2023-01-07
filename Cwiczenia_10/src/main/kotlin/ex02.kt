import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() = runBlocking{

    val pipelineLength = 10
    val numberOfProcessors = 5

    val pipelineChannels = Array(pipelineLength) { _ ->
        val newChannel = Channel<Int>(1)
        newChannel.send(-1)
        newChannel
    }

    val pipeline = Array(pipelineLength) { _ -> -1 }


    fun printPipeline() {
        print("[")
        pipeline.forEach { element ->
            print(" | $element")
        }
        println(" | ]")
    }

    fun processFunction(oldValue: Int): Int {
        if (oldValue == numberOfProcessors) {
            return -1
        }
        return oldValue + 1
    }

    suspend fun processor(number: Number) {
        var i = 0
        while (true) {
            val oldValue = pipelineChannels[i].receive()
            val newValue = processFunction(oldValue)
            pipeline[i] = newValue
            println("Processor $number processed value at $i from $oldValue to $newValue")
            printPipeline()
            pipelineChannels[i].send(newValue)
            i = (i + 1) % pipelineLength
            delay(Random.nextLong(1000))
        }
    }

    for (i in 0 until numberOfProcessors + 2) {
        launch { processor(i) }
    }
}

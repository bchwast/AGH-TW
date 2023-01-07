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

    suspend fun producer(number: Number) {
        var i = 0
        while (true) {
            val oldValue = pipelineChannels[i].receive()
            if (oldValue == -1) {
                val newValue = 0
                pipeline[i] = newValue
                println("Producer $number created value at $i")
                printPipeline()
                pipelineChannels[i].send(newValue)
                i = (i + 1) % pipelineLength
            } else {
                pipelineChannels[i].send(oldValue)
            }
            delay(Random.nextLong(1000))
        }
    }

    suspend fun processor(number: Number) {
        var i = 0
        while (true) {
            val oldValue = pipelineChannels[i].receive()
            if (oldValue == -1 || oldValue == numberOfProcessors) {
                pipelineChannels[i].send(oldValue)
            } else {
                val newValue = oldValue + 1
                pipeline[i] = newValue
                println("Processor $number processed value at $i from $oldValue to $newValue")
                printPipeline()
                pipelineChannels[i].send(newValue)
                i = (i + 1) % pipelineLength
                }
                delay(Random.nextLong(1000))
        }
    }

    suspend fun consumer(number: Number) {
        var i = 0
        while (true) {
            val oldValue = pipelineChannels[i].receive()
            if (oldValue == numberOfProcessors) {
                val newValue = -1
                pipeline[i] = newValue
                println("Conusmer $number consumed value at $i")
                printPipeline()
                pipelineChannels[i].send(newValue)
                i = (i + 1) % pipelineLength
            } else {
                pipelineChannels[i].send(oldValue)
            }
            delay(Random.nextLong(1000))
        }
    }

    for (i in 0 until 1) {
        launch { producer(i) }
    }
    for (i in 0 until numberOfProcessors) {
        launch { processor(i) }
    }
    for (i in 0 until 1) {
        launch { consumer(i) }
    }
}
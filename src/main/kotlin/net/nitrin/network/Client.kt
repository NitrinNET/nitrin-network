package net.nitrin.network

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.EventLoopGroup
import net.nitrin.network.packet.WriteablePacket
import java.net.SocketAddress
import java.util.concurrent.TimeUnit

class Client(private val address: SocketAddress, private val handler: ComponentHandler): NetworkComponent {

    private val eventGroup: EventLoopGroup = createEventLoopGroup()
    private var channel: Channel? = null

    fun connect() {
        val bootstrap: Bootstrap = Bootstrap()
            .channel(socketChannel())
            .group(eventGroup)
            .handler(DefaultChannelInitializer(object : ComponentFactory {
                override fun create(): NetworkComponent {
                    return this@Client
                }
            }, handler))
        println("Connecting to the network server...")
        val future = bootstrap.connect(address)
        future.awaitUninterruptibly(100, TimeUnit.SECONDS)

        if (!future.isSuccess) {
            throw RuntimeException("Couldn't connect to the network server on $address")
        } else {
            channel = future.channel()
            println("Connected to network server")
        }
    }

    fun disconnect() {
        println("Shutting down the network client")
        channel?.close()
        eventGroup.shutdownGracefully()
        println("Network client shutdown")
    }

    override fun sendPacket(packet: WriteablePacket) {
        channel?.writeAndFlush(packet)
    }
}
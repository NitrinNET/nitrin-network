package net.nitrin.network

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.EventLoopGroup
import net.nitrin.network.packet.WriteablePacket
import java.net.SocketAddress
import java.util.concurrent.TimeUnit

/**
 * Used to connect to [Server] and support our component system
 *
 * @param listener used to listen to disconnect & exception
 */
class Client(private val listener: ComponentListener): NetworkComponent {

    private var eventGroup: EventLoopGroup? = null
    private var channel: Channel? = null

    /**
     * Connects to specified address
     *
     * @param address which connect to
     * @throws RuntimeException when couldn't connect to specified address
     */
    fun connect(address: SocketAddress) {
        eventGroup = createEventLoopGroup()

        val bootstrap: Bootstrap = Bootstrap()
            .channel(socketChannel())
            .group(eventGroup)
            .handler(DefaultChannelInitializer(DefaultComponentFactory(), listener))
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

    /**
     * Disconnects from current connection. Shuts current [EventLoopGroup] down
     */
    fun disconnect() {
        println("Shutting down the network client")
        channel?.close()
        eventGroup?.shutdownGracefully()
        println("Network client shutdown")
    }

    override fun sendPacket(packet: WriteablePacket) {
        channel?.writeAndFlush(packet)
    }
}
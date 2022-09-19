package net.nitrin.network

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import net.nitrin.network.component.ComponentFactory
import net.nitrin.network.component.ComponentListener
import net.nitrin.network.component.DefaultComponentFactory
import java.net.SocketAddress
import java.util.concurrent.TimeUnit

/**
 * Used to start a [Server] and support our component system
 *
 * @param listener used to listen to disconnect & exception
 */
class Server(private val factory: ComponentFactory, private val listener: ComponentListener) {

    constructor(handler: ComponentListener): this(DefaultComponentFactory(), handler)

    private var bossGroup: EventLoopGroup? = null
    private var workerGroup: EventLoopGroup? = null
    private var channel: Channel? = null

    /**
     * Starts server on specified address
     *
     * @param address which to start on
     * @throws RuntimeException when couldn't start with specified address
     */
    fun start(address: SocketAddress) {
        bossGroup = createEventLoopGroup()
        workerGroup = createEventLoopGroup()

        val bootstrap: ServerBootstrap = ServerBootstrap()
            .channel(serverSocketChannel())
            .group(bossGroup, workerGroup)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(DefaultChannelInitializer(factory, listener))
        println("Starting network server...")
        val future = bootstrap.bind(address)
        future.awaitUninterruptibly(100, TimeUnit.SECONDS)

        if (!future.isSuccess) {
            throw RuntimeException("Couldn't start the network server on $address")
        } else {
            channel = future.channel()
            println("Server is now listening on $address")
        }
    }

    /**
     * Stops current connection. Shuts current [EventLoopGroup]s down
     */
    fun shutdown() {
        println("Shutting down the network server")
        channel?.close()
        workerGroup?.shutdownGracefully()
        bossGroup?.shutdownGracefully()
        println("Network server shutdown")
    }
}
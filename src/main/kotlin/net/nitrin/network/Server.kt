package net.nitrin.network

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import java.net.SocketAddress
import java.util.concurrent.TimeUnit

class Server(private val address: SocketAddress, private val factory: ComponentFactory, private val handler: ComponentHandler) {

    constructor(address: SocketAddress, handler: ComponentHandler): this(address, DefaultComponentFactory(), handler)

    private val bossGroup: EventLoopGroup = createEventLoopGroup()
    private val workerGroup: EventLoopGroup = createEventLoopGroup()
    private var channel: Channel? = null

    fun start() {
        val bootstrap: ServerBootstrap = ServerBootstrap()
            .channel(serverSocketChannel())
            .group(bossGroup, workerGroup)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(DefaultChannelInitializer(factory, handler))
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

    fun shutdown() {
        println("Shutting down the network server")
        channel?.close()
        workerGroup.shutdownGracefully()
        bossGroup.shutdownGracefully()
        println("Network server shutdown")
    }
}
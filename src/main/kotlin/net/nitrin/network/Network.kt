package net.nitrin.network

import io.netty.buffer.ByteBuf
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.epoll.EpollSocketChannel
import io.netty.channel.kqueue.KQueue
import io.netty.channel.kqueue.KQueueEventLoopGroup
import io.netty.channel.kqueue.KQueueServerSocketChannel
import io.netty.channel.kqueue.KQueueSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.util.concurrent.DefaultThreadFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.ThreadFactory

private val THREAD_FACTORY = createThreadFactory()
private val PROCESSORS = Runtime.getRuntime().availableProcessors()

/**
 * Creates a new [ThreadFactory]
 */
private fun createThreadFactory(): ThreadFactory {
    return DefaultThreadFactory("fluorite-network")
}

/**
 * Creates a new [EventLoopGroup] using [THREAD_FACTORY] as the factory and [PROCESSORS] as the thread count
 *
 * @return create [EventLoopGroup]
 */
fun createEventLoopGroup(): EventLoopGroup {
    return if (Epoll.isAvailable())
        EpollEventLoopGroup(PROCESSORS, THREAD_FACTORY)
    else if (KQueue.isAvailable())
        KQueueEventLoopGroup(PROCESSORS, THREAD_FACTORY)
    else
        NioEventLoopGroup(PROCESSORS, THREAD_FACTORY)
}

/**
 * @return supported [Class] of [ServerSocketChannel]
 */
fun serverSocketChannel(): Class<out ServerSocketChannel> {
    return if (Epoll.isAvailable())
        EpollServerSocketChannel::class.java
    else if (KQueue.isAvailable())
        KQueueServerSocketChannel::class.java
    else
        NioServerSocketChannel::class.java
}

/**
 * @return supported [Class] of [SocketChannel]
 */
fun socketChannel(): Class<out SocketChannel> {
    return if (Epoll.isAvailable())
        EpollSocketChannel::class.java
    else if (KQueue.isAvailable())
        KQueueSocketChannel::class.java
    else
        NioSocketChannel::class.java
}

/**
 * Reads a string from [ByteBuf]
 *
 * @return message
 */
fun ByteBuf.readString(): String {
    val length = readInt()
    val array = ByteArray(length)
    readBytes(array)
    return String(array, StandardCharsets.UTF_8)
}

/**
 * Writes a string to [ByteBuf]
 *
 * @param message
 */
fun ByteBuf.writeString(message: String) {
    val array = message.toByteArray(StandardCharsets.UTF_8)
    writeInt(array.size)
    writeBytes(array)
}
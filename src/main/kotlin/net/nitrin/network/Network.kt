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

private fun createThreadFactory(): ThreadFactory {
    return DefaultThreadFactory("fluorite-network")
}

fun createEventLoopGroup(): EventLoopGroup {
    return if (Epoll.isAvailable())
        EpollEventLoopGroup(PROCESSORS, THREAD_FACTORY)
    else if (KQueue.isAvailable())
        KQueueEventLoopGroup(PROCESSORS, THREAD_FACTORY)
    else
        NioEventLoopGroup(PROCESSORS, THREAD_FACTORY)
}

fun serverSocketChannel(): Class<out ServerSocketChannel> {
    return if (Epoll.isAvailable())
        EpollServerSocketChannel::class.java
    else if (KQueue.isAvailable())
        KQueueServerSocketChannel::class.java
    else
        NioServerSocketChannel::class.java
}

fun socketChannel(): Class<out SocketChannel> {
    return if (Epoll.isAvailable())
        EpollSocketChannel::class.java
    else if (KQueue.isAvailable())
        KQueueSocketChannel::class.java
    else
        NioSocketChannel::class.java
}

fun ByteBuf.readString(): String {
    val length = readInt()
    val array = ByteArray(length)
    readBytes(array)
    return String(array, StandardCharsets.UTF_8)
}

fun ByteBuf.writeString(value: String) {
    val array = value.toByteArray(StandardCharsets.UTF_8)
    writeInt(array.size)
    writeBytes(array)
}
package net.nitrin.network.packet.idle

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent

class IdleHandler(): ChannelDuplexHandler() {

    override fun userEventTriggered(context: ChannelHandlerContext, event: Any) {
        if (event is IdleStateEvent) {
            val channel = context.channel()
            when (event.state()) {
                IdleState.READER_IDLE -> {
                    channel.close()
                }

                IdleState.WRITER_IDLE -> {
                    channel.writeAndFlush(PingPacket())
                }

                else -> {
                    channel.close()
                }
            }
        }
    }
}
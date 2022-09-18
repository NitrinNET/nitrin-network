package net.nitrin.network

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

/**
 * Handles inactivity and exceptions from [NetworkComponent]
 *
 * @param handler handles message pass through
 * @param component which to handle
 */
class ChannelHandler(private val handler: ComponentHandler, var component: NetworkComponent): ChannelInboundHandlerAdapter() {

    override fun channelInactive(context: ChannelHandlerContext) {
        val channel = context.channel()
        if (channel.isOpen) {
            channel.close()
        }
        handler.handleDisconnect(NetworkContext(component, channel))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun exceptionCaught(context: ChannelHandlerContext, cause: Throwable) {
        val channel = context.channel()
        if (channel.isOpen) {
            channel.close()
        }
        handler.handleException(NetworkContext(component, channel))
    }
}
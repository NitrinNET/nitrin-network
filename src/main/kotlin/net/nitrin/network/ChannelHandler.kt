package net.nitrin.network

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import net.nitrin.network.component.ComponentListener
import net.nitrin.network.component.NetworkComponent

/**
 * Handles inactivity and exceptions from [NetworkComponent]
 *
 * @param listener gets notified when event happens
 * @param component which to handle
 */
class ChannelHandler(private val listener: ComponentListener, var component: NetworkComponent): ChannelInboundHandlerAdapter() {

    override fun channelInactive(context: ChannelHandlerContext) {
        val channel = context.channel()
        if (channel.isOpen) {
            channel.close()
        }
        listener.disconnect(NetworkContext(component, channel))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun exceptionCaught(context: ChannelHandlerContext, cause: Throwable) {
        val channel = context.channel()
        if (channel.isOpen) {
            channel.close()
        }
        listener.exception(NetworkContext(component, channel))
    }
}
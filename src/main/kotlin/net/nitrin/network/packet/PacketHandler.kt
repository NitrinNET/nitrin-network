package net.nitrin.network.packet

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.nitrin.network.component.NetworkComponent
import net.nitrin.network.NetworkContext

class PacketHandler(var component: NetworkComponent): SimpleChannelInboundHandler<Packet>() {

    override fun channelRead0(context: ChannelHandlerContext, packet: Packet) {
        PacketRegistry.handle(NetworkContext(component, context.channel()), packet)
    }
}
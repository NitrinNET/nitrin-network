package net.nitrin.network

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.handler.timeout.IdleStateHandler
import net.nitrin.network.component.ComponentFactory
import net.nitrin.network.component.ComponentListener
import net.nitrin.network.packet.PacketHandler
import net.nitrin.network.packet.codec.PacketDecoder
import net.nitrin.network.packet.codec.PacketEncoder
import net.nitrin.network.packet.idle.IdleHandler
import java.util.concurrent.TimeUnit

class DefaultChannelInitializer(private val factory: ComponentFactory, private val listener: ComponentListener): ChannelInitializer<Channel>() {

    override fun initChannel(channel: Channel) {
        val component = factory.create(channel)
        channel.pipeline()
            .addLast("idle-state-handler", IdleStateHandler(30, 15, 0, TimeUnit.SECONDS))
            .addLast("idle-handler", IdleHandler())
            .addLast("packet-decoder", PacketDecoder())
            .addLast("packet-encoder", PacketEncoder())
            .addLast("packet-handler", PacketHandler(component))
            .addLast("channel-handler", ChannelHandler(listener, component))
    }
}
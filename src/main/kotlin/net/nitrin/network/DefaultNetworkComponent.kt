package net.nitrin.network

import io.netty.channel.Channel
import net.nitrin.network.packet.WriteablePacket

class DefaultNetworkComponent(private val channel: Channel): NetworkComponent {

    override fun sendPacket(packet: WriteablePacket) {
        channel.writeAndFlush(packet)
    }
}
package net.nitrin.network.packet.idle

import io.netty.channel.Channel
import net.nitrin.network.component.NetworkComponent
import net.nitrin.network.NetworkContext
import net.nitrin.network.packet.PacketInHandler

class PingPacketInHandler(): PacketInHandler<PingPacket> {

    override fun handle(context: NetworkContext, packet: PingPacket) {
        val (component: NetworkComponent, _: Channel) = context

        component.sendPacket(PongPacket())
    }
}
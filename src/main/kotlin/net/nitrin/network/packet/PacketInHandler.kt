package net.nitrin.network.packet

import net.nitrin.network.NetworkContext

interface PacketInHandler<T: Packet> {

    fun handle(context: NetworkContext, packet: T)
}
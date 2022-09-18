package net.nitrin.network

import io.netty.channel.Channel
import net.nitrin.network.packet.WriteablePacket

interface NetworkComponent {

    /**
     * Sends packet to current [Channel]
     *
     * @param packet to send
     */
    fun sendPacket(packet: WriteablePacket)
}
package net.nitrin.network

import net.nitrin.network.packet.WriteablePacket

interface NetworkComponent {

    fun sendPacket(packet: WriteablePacket)
}
package net.nitrin.network.packet.idle

import io.netty.buffer.ByteBuf
import net.nitrin.network.packet.Packet
import net.nitrin.network.packet.PacketRegistry
import net.nitrin.network.packet.WriteablePacket

class PingPacket: WriteablePacket(PacketRegistry.DEFAULT + 0), Packet {

    override fun write(buffer: ByteBuf) {}
}
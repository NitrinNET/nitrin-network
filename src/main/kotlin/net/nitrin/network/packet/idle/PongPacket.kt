package net.nitrin.network.packet.idle

import io.netty.buffer.ByteBuf
import net.nitrin.network.packet.Packet
import net.nitrin.network.packet.PacketRegistry
import net.nitrin.network.packet.WriteablePacket

class PongPacket: WriteablePacket(PacketRegistry.DEFAULT + 1), Packet {

    override fun write(buffer: ByteBuf) {}
}
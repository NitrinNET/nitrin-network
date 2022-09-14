package net.nitrin.network.packet.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import net.nitrin.network.packet.PacketNotFoundException
import net.nitrin.network.packet.PacketRegistry

class PacketDecoder: ByteToMessageDecoder() {

    override fun decode(context: ChannelHandlerContext, buffer: ByteBuf, output: MutableList<Any>) {
        val id = buffer.readInt()

        val packet = PacketRegistry.createPacket(id, buffer)

        if (packet == null) {
            buffer.clear() // Clearing buffer for packets left

            val channel = context.channel()
            val address = channel.remoteAddress()

            channel.close()

            throw PacketNotFoundException(address, id)
        }
        output.add(packet)
    }
}
package net.nitrin.network.packet.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import net.nitrin.network.packet.WriteablePacket

class PacketEncoder: MessageToByteEncoder<WriteablePacket>() {

    override fun encode(context: ChannelHandlerContext, message: WriteablePacket, buffer: ByteBuf) {
        buffer.writeInt(message.id)
        message.write(buffer)
    }
}
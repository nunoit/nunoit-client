package de.nunoit.client;

import de.nunoit.networking.FrameDecoder;
import de.nunoit.networking.FramePrepender;
import de.nunoit.networking.PacketDecoder;
import de.nunoit.networking.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerConnection extends ChannelHandlerAdapter {

	private final String host;
	private final int port;

	private EventLoopGroup eventLoops;

	public void connect() throws InterruptedException {
		try {
			eventLoops = new NioEventLoopGroup();

			Bootstrap b = new Bootstrap();
			b.group(eventLoops);
			b.channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel)
						throws Exception {
					channel.pipeline().addLast(new FramePrepender());
					channel.pipeline().addLast(new FrameDecoder());
					channel.pipeline().addLast(new PacketEncoder());
					channel.pipeline().addLast(new PacketDecoder());
					channel.pipeline().addLast(
							new ClientConnectionHandler(channel));
				}
			});
			ChannelFuture f = b.connect(host, port).sync();

			f.channel().closeFuture().sync();
		} finally {
			eventLoops.shutdownGracefully();
		}
	}

}

package de.nunoit.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import de.nunoit.networking.PacketHandler;
import de.nunoit.networking.protocol.packets.Connect;
import de.nunoit.networking.protocol.packets.ImageRequest;
import de.nunoit.networking.protocol.packets.ImageTask;
import de.nunoit.networking.protocol.packets.TaskCancel;

@RequiredArgsConstructor
public class ClientPacketHandler extends PacketHandler {

	@NonNull
	private ClientConnectionHandler connection;

	public void connected() {
		connection.send(new Connect(Client.CLIENT_UUID));
		connection.send(new ImageRequest());
		connection.flush();
	}

	@Override
	public void handle(ImageTask task) {
		// TODO
		// Uunnd... direkt Abbrechen :P Zum testen halt^^
		connection.send(new TaskCancel(task.getTaskId()));
		connection.flush();
	}

}

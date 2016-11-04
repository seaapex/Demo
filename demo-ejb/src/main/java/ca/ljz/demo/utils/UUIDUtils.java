package ca.ljz.demo.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

	public static UUID byteArrayToUUID(byte[] byteArray) {
		final ByteBuffer bb = ByteBuffer.wrap(byteArray);
		
		long most = bb.getLong();
		long least = bb.getLong();

		return new UUID(most, least);
	}
	
	public static String byteArrayToUUIDString(byte[] byteArray) {
		UUID uuid = byteArrayToUUID(byteArray);
		return uuid.toString();
	}

	public static byte[] uuidToByteArray(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);

		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());

		return bb.array();
	}
	
	public static byte[] uuidToByteArray(String id) {
		UUID uuid = UUID.fromString(id);
		return uuidToByteArray(uuid);
	}
}

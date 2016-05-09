
public class Assignment2 {

/*====================================TODO===================================*/
	/**
	 * TODO: Allocate a buffer of the correct size and initialize the
	 * ethernet header here.
	 */
	private static byte[] buildEtherHeader(byte[] dstmac, byte[] srcmac) {

		byte[] etherBuff = new byte[14];

		int k = 0;
		
		for (byte b : dstmac) etherBuff[k++] = b;

		for (byte b : srcmac) etherBuff[k++] = b;

		etherBuff[k++] = 0x08;
		etherBuff[k]   = 0x42;

		return etherBuff;
		
	}

	/**
	 * TODO: Allocate a buffer of the correct size and initialize the WoL
	 * payload here.
	 */
	private static byte[] buildWolPayload(byte[] dstmac) {

		byte[] payload = new byte[102];

		for (int i = 0; i < 6; i++) payload[i] = (byte)0xff;

		for (int i = 6; i < 102; i = i + 6)
			for (int j = 0; j < 6; j++)
				payload[i + j] = dstmac[j];

		return payload;
		
	}

	/**
	 * TODO: Parse the MAC address (string) and return the result as byte
	 * array for use in packet building. Return null on error.
	 */
	private static byte[] parseMAC(String hwaddr) {

		byte[] macBuff = new byte[6];

		int k = 0;

		try {

			for (String b : hwaddr.trim().split(":")) {
				macBuff[k++] = (byte) ((Character.digit(b.charAt(0), 16) << 4) + Character.digit(b.charAt(1), 16));
			}

		} catch (NumberFormatException e) { return null; }

		return macBuff;
		
	}
/*===========================================================================*/

	public static void run(GRNVS_RAW sock, String dst) {
		byte[] buffer = new byte[1514];
		byte[] dstmac;
		byte[] srcmac;
		byte[] header;
		byte[] payload;
		int length;

/*====================================TODO===================================*/
		//TODO: buildwrite  ether-header
		// -> use buildEtherHeader()
		// -> getMac() in GRNVS_RAW returns your source address
		// -> determine the correct ethertype and take care of endianess

		//TODO: build WoL payload
		// -> use buildWolPayload()

		//TODO: Concatinate the ether header and the WoL payload into
		// buffer.
		// Determine the size of the WoL packet and set it here.

		dstmac = parseMAC(dst);

		srcmac = sock.getMac();

		if(dstmac == null) {
			System.err.println("Your destination input format is broken, it should be: xx:xx:xx:xx:xx:xx");
			return;
		}

		header = buildEtherHeader(dstmac, srcmac);

		payload = buildWolPayload(dstmac);

		for (int i = 0; i < header.length; i++)
			buffer[i] = header[i];

		for (int i = 0; i < payload.length; i++)
			buffer[i + header.length] = payload[i];
		
		
		length = header.length + payload.length;
		sock.hexdump(buffer, length);
		sock.write(buffer, length);
/*===========================================================================*/
	}



	public static void main(String[] argv) {
		Arguments args = new Arguments(argv);
		GRNVS_RAW sock = null;
		try{
			sock = new GRNVS_RAW(args.iface, 3);
			run(sock, args.dst);
			sock.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}

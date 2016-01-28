package ir.ac.aut.ceit.sa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * I fetch whole data from
 * 
 * @author Atefeh Zareh Chahoki
 * 
 */
public class MiningActivityDetector {

	private static final String DATE_FORMAT_PATTERN = "(yyyy/MM/dd, HH:mm:ss)";
	private static String BLOCKS_LOG_PATH = "C:\\botcointrap-data\\log\\latestBlockHash.txt";
	private static String DEBUG_LOG_PATH = "C:\\botcointrap-data\\log\\pinatrace.out";
	private static short DEBUG_HEADER_COUNT = 3;
	private static long BLOCK_PROPAGATION_MIN_TIME = 1000l; // cover time in
	// millisecond.
	private static long BLOCK_PROPAGATION_MAX_TIME = 30000l; // cover time in
	// millisecond.

	private static int BLOCK_LOG_LINE_SIZE = 110;
	private static int BLOCK_CHUNK_LINE_COUNT = 10;

	// debug line size is not constant
	private static int DEBUG_CHUNK_SIZE = 1000;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, ParseException {

		/*
		 * Reading blocks log file
		 */
		List<BlockLog> blockLogs = getBlockLogs();
		/*
		 * Reading debug log file
		 */
		List<DebugLog> debugLogs = getDebugLogs();
		/*
		 * creating dummy data
		 */
		// List<BlockLog> blockLogs = new ArrayList<BlockLog>();
		// List<DebugLog> debugLogs = new ArrayList<DebugLog>();
		//
		// // is receiving date, important?
		// blockLogs.add(new BlockLog("0", new Date(2016, 1, 15, 0, 5, 0),
		// new Date(2016, 1, 15, 0, 0, 0)));
		// blockLogs.add(new BlockLog("0", new Date(2016, 1, 16, 0, 5, 0),
		// new Date(2016, 1, 16, 0, 0, 0)));
		// blockLogs.add(new BlockLog("0", new Date(2016, 1, 17, 0, 5, 0),
		// new Date(2016, 1, 17, 0, 0, 0)));
		// blockLogs.add(new BlockLog("1", new Date(2016, 1, 17, 0, 6, 0),
		// new Date(2016, 1, 17, 0, 5, 0)));
		//		
		//		
		//		
		//		
		//		
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 1, 0), "i1",
		// "address1", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 2, 0), "i2",
		// "address2", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 3, 0), "i1",
		// "address1", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 4, 0), "i1",
		// "address1", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 5, 0), "i1",
		// "address1", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 6, 0), "i1",
		// "address1", "8", "0x8768276"));
		// debugLogs.add(new DebugLog(new Date(2016, 1, 17, 0, 7, 0), "i1",
		// "address1", "8", "0x8768276"));

		Integer blockLogIdx = 0;
		Integer debugLogIdx = 0;

		/*
		 * Skip some old blocks that are not used in execution.
		 */
		while (blockLogs.get(blockLogIdx).getBlockDate().getTime() > debugLogs
				.get(debugLogIdx).getExecuteDate().getTime()
				+ BLOCK_PROPAGATION_MIN_TIME) {
			// blockTime + MinPropagationTime > executionTime
			blockLogIdx++;
		}

		/*
		 * Iterate debug logs with fixed block.
		 * 
		 * debug that is executed in this time: block1+minPropagation to
		 * block2+maxPropagation
		 */
		do {

		} while (blockLogs.get(blockLogIdx + 1).getBlockDate().getTime()
				+ BLOCK_PROPAGATION_MAX_TIME < debugLogs.get(debugLogIdx)
				.getExecuteDate().getTime());

		// while(blockLogs.get(blockLogIdx).getBlockDate().getTime() > debugLogs
		// .get(debugLogIdx).getExecuteDate().getTime()
		// + BLOCK_PROPAGATION_MIN_TIME){
		//			 
		// }

		/*
		 * Correlating data
		 */

		Boolean hasMiningActivity = checkMiner(blockLogs, debugLogs);
	}

	private static Boolean checkMiner(List<BlockLog> blockLogs,
			List<DebugLog> debugLogs) {
		int blockIdx = 0;

		/**
		 * these tow indexes is used to separate each chunk for processing
		 */
		int debugStartIdx = 0;
		int debuhEndIdx = 0;
		Date startDate = null;

		return null;
	}

	/**
	 * 
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private static List<DebugLog> getDebugLogs() throws IOException,
			ParseException {
		List<DebugLog> debugLogs = new ArrayList<DebugLog>();
		RandomAccessFile debugFile = new RandomAccessFile(DEBUG_LOG_PATH, "r");
		FileChannel inChannel = debugFile.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(DEBUG_CHUNK_SIZE);
		String lastLineRemaning = "";
		while (inChannel.read(buffer) > 0) {
			buffer.flip();
			String chunk = new String(buffer.array());
			chunk = lastLineRemaning + chunk;
			String[] lines = chunk.split("\n");
			for (int i = DEBUG_HEADER_COUNT; i < lines.length - 1; i++) {
				String line = lines[i];
				DebugLog debugLog = getDebugLog(line);
				debugLogs.add(debugLog);
			}
			lastLineRemaning = lines[lines.length - 1];
			buffer.clear();
		}
		inChannel.close();
		debugFile.close();
		return debugLogs;
	}

	/**
	 * (2016/01/12, 09:08:24) 000007FB6E23028F: R 000007FB6E33CB00 8
	 * 0x7fb6ca21828
	 * 
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	private static DebugLog getDebugLog(String line) throws ParseException {
		try {
			String executeDateStr = line.substring(0, 23);
			String instruction = line.substring(23, 39);
			String remaning = line.substring(46);
			String[] parts = remaning.split("[\\s]+");
			String readAddress = parts[0];
			String readCount = parts[1];
			String readContent = parts[2];

			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			Date executeDate = dateFormat.parse(executeDateStr);

			DebugLog debugLog = new DebugLog(executeDate, instruction,
					readAddress, readCount, readContent);
			System.out.println(line);
			return debugLog;
		} catch (Throwable t) {
			System.out.println("error in processing debug line:" + line);
			return null;
		}

	}

	private static List<BlockLog> getBlockLogs() throws IOException,
			ParseException {
		List<BlockLog> blockLogs = new ArrayList<BlockLog>();
		RandomAccessFile blockFile = new RandomAccessFile(BLOCKS_LOG_PATH, "r");
		FileChannel inChannel = blockFile.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BLOCK_LOG_LINE_SIZE
				* BLOCK_CHUNK_LINE_COUNT);
		String firstLineOfChunk = "";
		byte[] emptyBuffer = new byte[BLOCK_LOG_LINE_SIZE
				* BLOCK_CHUNK_LINE_COUNT];
		
		while (inChannel.read(buffer) > 0) {
			buffer.flip();
			String chunk = new String(buffer.array());
			String[] lines = chunk.split("\n");

			/**
			 * complete first line of chunk with last chunk data.
			 */
			if (lines.length != 0) {
				lines[0] = firstLineOfChunk + lines[0];
				firstLineOfChunk = "";
			}

			/**
			 * process every lines except last line in chunk (it may be
			 * incomplete).
			 */
			for (int i = 0; i < lines.length - 1; i++) {
				String line = lines[i];
				BlockLog blockLog = getBlockLog(line);
				blockLogs.add(blockLog);
			}
			firstLineOfChunk = lines[lines.length - 1];
			buffer.clear();
			/*
			 * clear don't clean the content of the buffer.
			 */
//			buffer.put(emptyBuffer);
		}

		/**
		 * process last line if it is remained
		 */
		if (!firstLineOfChunk.equals("")) {
			BlockLog blockLog = getBlockLog(firstLineOfChunk);
			blockLogs.add(blockLog);
		}

		inChannel.close();
		blockFile.close();
		return blockLogs;
	}

	/**
	 * pattern of each line:
	 * 000000000000000007B858CEED9681A0487D15CC60E90A7D2AEC96BED94D5795
	 * (2016/01/23, 13:11:00) (2016/01/23, 13:09:41)
	 * 
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	private static BlockLog getBlockLog(String line) throws ParseException {
		try {
			String hash = line.substring(0, 64);
			String receiveDateStr = line.substring(65, 87);
			String blockDateStr = line.substring(88, 110);
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			Date receiveDate = dateFormat.parse(receiveDateStr);
			Date blockDate = dateFormat.parse(blockDateStr);

			BlockLog blockLog = new BlockLog(hash, receiveDate, blockDate);
			return blockLog;
		} catch (Throwable e) {
			System.out.println("error in processing block line:" + line);
			return null;
		}
	}
}

package ir.ac.aut.ceit.sa;

import java.util.Date;

/**
 * 
 * @author Atefeh Zareh Chahoki
 * 
 *         (2016/01/12, 09:08:24) 000007FB6E23028F: R 000007FB6E33CB00 8
 *         0x7fb6ca21828
 */
public class DebugLog implements Comparable<DebugLog> {
	private Date executeDate;
	private String instruction;
	private String readAddress;
	private String readCount;
	private String readContent;

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getReadAddress() {
		return readAddress;
	}

	public void setReadAddress(String readAddress) {
		this.readAddress = readAddress;
	}

	public String getReadContent() {
		return readContent;
	}

	public void setReadContent(String readContent) {
		this.readContent = readContent;
	}

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public DebugLog(Date executeDate, String instruction, String readAddress,
			String readCount, String readContent) {
		super();
		this.executeDate = executeDate;
		this.instruction = instruction;
		this.readAddress = readAddress;
		this.readCount = readCount;
		this.readContent = readContent;
	}

	@Override
	public String toString() {
		return "DebugLog [executeDate=" + executeDate + ", instruction="
				+ instruction + ", readAddress=" + readAddress
				+ ", readContent=" + readContent + ", readCount=" + readCount
				+ "]";
	}

	/**
	 * we assume that data is read in ordered manner and there is no need to
	 * sort data
	 */
	public int compareTo(DebugLog other) {
		return this.getExecuteDate().compareTo(other.getExecuteDate());
	}
}

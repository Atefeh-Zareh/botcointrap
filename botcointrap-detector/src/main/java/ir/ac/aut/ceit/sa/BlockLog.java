package ir.ac.aut.ceit.sa;

import java.util.Date;

/**
 * 
 * @author Atefeh Zareh Chahoki
 * 
 */
public class BlockLog implements Comparable<BlockLog> {
	private String hash;
	private Date receiveDate;
	private Date blockDate;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}

	public BlockLog(String hash, Date receiveDate, Date blockDate) {
		super();
		this.hash = hash;
		this.receiveDate = receiveDate;
		this.blockDate = blockDate;
	}

	public String toString() {
		return "BlockLog [blockDate=" + blockDate + ", hash=" + hash
				+ ", receiveDate=" + receiveDate + "]";
	}

	/**
	 * we assume that data is read in ordered manner and there is no need to
	 * sort data
	 */

	public int compareTo(BlockLog other) {
		return this.getBlockDate().compareTo(other.getBlockDate());
	}
}

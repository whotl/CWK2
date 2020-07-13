/**
 * Host class stores a host as an object
 * 
 */
public class Host {
	/**
	 * id of the host
	 */
	private String hostID;
	/**
	 * name of the host
	 */
	private String hostName;
	/**
	 * type of the host
	 */
	private String type;
	/**
	 * price of the host
	 */
	private int price;
	/**
	 * minimum nights of the host
	 */
	private int minimumNights;
	
	public Host(String hostID, String hostName, String type, int price, int minimumNights) {
		this.hostID = hostID;
		this.hostName = hostName;
		this.type = type;
		this.price = price;
		this.minimumNights = minimumNights;
	}
	
	/**
	 * Getter for hostID
	 * @return id of the host
	 */
	public String getHostID() {
		return hostID;
	}
	
	/**
	 * Getter for hostName
	 * @return name of the host
	 */
	public String getHostName() {
		return hostName;
	}
	
	/**
	 * Getter for type
	 * @return type of the host
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter for price
	 * @return price of the host
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Getter for minimumNights
	 * @return minimum nights of the host
	 */
	public int getMinimumNights() {
		return minimumNights;
	}
}

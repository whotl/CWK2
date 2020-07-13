
public class Host {
	private String hostID;
	private String hostName;
	private String type;
	private int price;
	private int minimumNights;
	
	public Host(String hostID, String hostName, String type, int price, int minimumNights) {
		this.hostID = hostID;
		this.hostName = hostName;
		this.type = type;
		this.price = price;
		this.minimumNights = minimumNights;
	}
	
	public String getHostID() {
		return hostID;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public String getType() {
		return type;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getMinimumNights() {
		return minimumNights;
	}
}

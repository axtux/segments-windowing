package graphics;

public interface StatusListener {
	public default boolean updateStatus(boolean noError, String status) {
		System.out.println((noError ? "Status : " : "Error : ")+status);
		return noError;
	}
}

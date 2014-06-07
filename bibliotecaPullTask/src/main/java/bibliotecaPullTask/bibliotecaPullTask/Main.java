package bibliotecaPullTask.bibliotecaPullTask;

public class Main {

	public static void main(String[] args) {

		try {
			PullQueue.send("oxi1");

			PullQueue.send("oxi2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String a = PullQueue.receive("oxi1");

			String b = PullQueue.receive("oxi2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

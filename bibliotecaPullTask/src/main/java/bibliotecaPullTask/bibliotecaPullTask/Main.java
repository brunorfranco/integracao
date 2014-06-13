package bibliotecaPullTask.bibliotecaPullTask;

public class Main {

	public static void main(String[] args) {

		try {
			PullQueue.send("aaa");

			PullQueue.send("bbbb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String a = PullQueue.receive("aaa");

			String b = PullQueue.receive("bbbb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

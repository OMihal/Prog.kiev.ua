package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			System.out.println("Enter your password: ");
			String password = scanner.nextLine();

			// authentication
			Message m = new AuthMessage(login, password);
			int res = m.send(Utils.getURL() + "/login");
			if (res == 200) { // 200 OK
				System.out.println("Login - OK!");
			} else {
				if (res == 401){
					System.err.println("Invalid login or password!");
				} else {
					System.err.println("HTTP error occured: " + res);
				}
				return;
			}

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message (or enter '/help'): ");
			while (true) {
				String text = scanner.nextLine();

				if (text.isEmpty()) {
					m = new LogoutMessage(login);
					res = m.send(Utils.getURL() + "/logout");
					if (res == 200) { // 200 OK
						System.out.println("Logout");
					} else{
						System.err.println("HTTP error occured: " + res);
					}
					break;
				}
				if (text.startsWith("/")) {// service
					res = serviceProcessing(login, text);
				} else {
					m = new Message(login, text);
					res = m.send(Utils.getURL() + "/add");
				}
				if (res != 200) { // 200 OK
					System.err.println("HTTP error occured: " + res);
					//return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	private static int serviceProcessing(String from, String text) throws IOException {
        Message m = new ServiceMessage(from, text);
        return m.send(Utils.getURL() + "/service");
    }
}

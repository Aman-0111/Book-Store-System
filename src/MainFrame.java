import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class MainFrame extends JFrame {
	//Attributes
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() throws FileNotFoundException{
		//Creates Panel and sets close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Creates user ComboBox
		JComboBox<String> userSelect = new JComboBox<String>();
		userSelect.setBounds(257, 45, 123, 22);
		
		//Opens user file
		File userFile = new File("UserAccounts.txt");
		Scanner fileScanner = new Scanner(userFile);
		
		//Adds userIDs to BomboBox
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] array = line.split(", ");
			userSelect.addItem(array[1]);
		}
		fileScanner.close();
		contentPane.add(userSelect);	
		
		//Creates confirm Button
		JButton confirmUser = new JButton("Confirm");
		confirmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Obtains selected userID
				String UserId = String.valueOf(userSelect.getSelectedItem());
				
				//Opens User file
				File userFile = new File("UserAccounts.txt");
				Scanner fileScanner;
				//Array stores user details
				String[] details = new String[7];
				//Handles FileNotFoundException
				try {
					//Obtains details of selected user
					fileScanner = new Scanner(userFile);
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						String[] array = line.split(", ");
						if (array[1].equals(UserId)) {
							details = array;
							break;
						}
					}
					fileScanner.close();
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				
				//Obtains all books in stock file
				ArrayList<Book> Books = new ArrayList<Book>();
				//Handles FileNotFoundException
				try {
					Books = Book.getBooks();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				//Checks if selected user is an admin
				if (details[6].equals("admin")) {
					//Instantiates an admin class and AdminFrame
					Admin user = new Admin (Integer.parseInt(details[0]),details[1],details[2],Integer.parseInt(details[3]),details[4],details[5]);
					AdminFrame adf = new AdminFrame(Books,user);
					adf.setVisible(true);
				} else {
					//Instantiates a customer class and CustFrame
					Customer user = new Customer (Integer.parseInt(details[0]),details[1],details[2],Integer.parseInt(details[3]),details[4],details[5]);
					CustFrame cdf = new CustFrame(Books,user);
					cdf.setVisible(true);
				}
				
			}
		});
		confirmUser.setBounds(272, 11, 89, 23);
		contentPane.add(confirmUser);

	}
}

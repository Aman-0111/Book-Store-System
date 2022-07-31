import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {
	//Attributes
	private JPanel contentPane;
	private JTable BookTable;
	private DefaultTableModel dtmbooks;
	private ArrayList<Book> Books;
	private JTextField ISBNtxt;
	private JTextField Titletxt;
	private JTextField Releasetxt;
	private JTextField Pricetxt;
	private JTextField Quantitytxt;
	private JTextField info1txt;


	/**
	 * Create the frame.
	 */
	public AdminFrame(ArrayList<Book> Books,Admin user) {
		this.Books = Books;
		//Creates Panel and sets close operation
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 756, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Created tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 732, 409);
		contentPane.add(tabbedPane);
		
		//Creates a panel
		JPanel panel = new JPanel();
		tabbedPane.addTab("View Table", null, panel, null);
		panel.setLayout(null);
		
		//Adds a scroll pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 707, 340);
		panel.add(scrollPane);
		
		//Creates a JTable
		BookTable = new JTable();
		scrollPane.setViewportView(BookTable);
		
		//Uses DefaultTableModel to setup JTable
		dtmbooks = new DefaultTableModel();
		dtmbooks.setColumnIdentifiers(new Object[] {"ISBN","Book Type","Title","Language","Genre","Release Date","Price","Quantity","Info 1","Info 2"});
		this.BookTable.setModel(dtmbooks);
		
		//Loops through Books ArrayList
		for (Book book : this.Books) {
			//Casts books into correct book type and adds their data to the books DefaultTableModel
			if (book.getType() == "paperback") {
				Paperback p_book = (Paperback)book;
				Object[] rowdata = new Object[] {p_book.getIsbn(),p_book.getType(),p_book.getTitle(),p_book.getLanguage(),p_book.getGenre(),p_book.getRelease(),p_book.getPrice(),p_book.getQuantity(),p_book.getPages(),p_book.getCondition()};
				dtmbooks.addRow(rowdata);
			} else if (book.getType() == "ebook") {
				Ebook e_book = (Ebook)book;
				Object[] rowdata = new Object[] {e_book.getIsbn(),e_book.getType(),e_book.getTitle(),e_book.getLanguage(),e_book.getGenre(),e_book.getRelease(),e_book.getPrice(),e_book.getQuantity(),e_book.getPages(),e_book.getFormat()};
				dtmbooks.addRow(rowdata);
			} else {
				Audio a_book = (Audio)book;
				Object[] rowdata = new Object[] {a_book.getIsbn(),a_book.getType(),a_book.getTitle(),a_book.getLanguage(),a_book.getGenre(),a_book.getRelease(),a_book.getPrice(),a_book.getQuantity(),a_book.getLength(),a_book.getFormat()};
				dtmbooks.addRow(rowdata);
			}
		}
		
		//Creates Refresh button
		JButton RefreshButton = new JButton("Refresh");
		RefreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Clears DefaultTableModel
				dtmbooks.setRowCount(0);
				//Creates New Book ArrayList
				ArrayList<Book> Books = new ArrayList<Book>();
				//Handles FileNotFoundException
				try {
					Books = Book.getBooks();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				//Loops through ArrayList to add books like above
				for (Book book : Books) {
					if (book.getType() == "paperback") {
						Paperback p_book = (Paperback)book;
						Object[] rowdata = new Object[] {p_book.getIsbn(),p_book.getType(),p_book.getTitle(),p_book.getLanguage(),p_book.getGenre(),p_book.getRelease(),p_book.getPrice(),p_book.getQuantity(),p_book.getPages(),p_book.getCondition()};
						dtmbooks.addRow(rowdata);
					} else if (book.getType() == "ebook") {
						Ebook e_book = (Ebook)book;
						Object[] rowdata = new Object[] {e_book.getIsbn(),e_book.getType(),e_book.getTitle(),e_book.getLanguage(),e_book.getGenre(),e_book.getRelease(),e_book.getPrice(),e_book.getQuantity(),e_book.getPages(),e_book.getFormat()};
						dtmbooks.addRow(rowdata);
					} else {
						Audio a_book = (Audio)book;
						Object[] rowdata = new Object[] {a_book.getIsbn(),a_book.getType(),a_book.getTitle(),a_book.getLanguage(),a_book.getGenre(),a_book.getRelease(),a_book.getPrice(),a_book.getQuantity(),a_book.getLength(),a_book.getFormat()};
						dtmbooks.addRow(rowdata);
					}
				}
			}
		});
		RefreshButton.setBounds(324, 358, 89, 23);
		panel.add(RefreshButton);
		
		//Creates another Panel
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Add book", null, panel_1, null);
		panel_1.setLayout(null);
		
		//Creates TextField
		ISBNtxt = new JTextField();
		ISBNtxt.setBounds(39, 39, 96, 20);
		panel_1.add(ISBNtxt);
		ISBNtxt.setColumns(10);
		
		//Creates Label
		JLabel lblNewLabel = new JLabel("ISBN");
		lblNewLabel.setBounds(73, 14, 48, 14);
		panel_1.add(lblNewLabel);
		
		//Creates TextField
		Titletxt = new JTextField();
		Titletxt.setBounds(39, 98, 96, 20);
		panel_1.add(Titletxt);
		Titletxt.setColumns(10);
		
		//Creates Label
		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setBounds(73, 73, 48, 14);
		panel_1.add(lblNewLabel_1);
		
		//Creates Label
		JLabel lblNewLabel_2 = new JLabel("Language");
		lblNewLabel_2.setBounds(63, 137, 72, 14);
		panel_1.add(lblNewLabel_2);
		
		//Creates ComboBox storing languages
		JComboBox LangBox = new JComboBox();
		LangBox.setBounds(39, 162, 96, 22);
		LangBox.addItem("English");
		LangBox.addItem("French");
		panel_1.add(LangBox);
		
		//Creates ComboBox storing Genres
		JComboBox Genrebox = new JComboBox();
		Genrebox.setBounds(26, 225, 136, 22);
		Genrebox.addItem("Politics");
		Genrebox.addItem("Medicine");
		Genrebox.addItem("Business");
		Genrebox.addItem("Computer Science");
		Genrebox.addItem("Biography");
		panel_1.add(Genrebox);
		
		//Creates Text Field
		Releasetxt = new JTextField();
		Releasetxt.setBounds(214, 39, 96, 20);
		panel_1.add(Releasetxt);
		Releasetxt.setColumns(10);
		
		//Creates Label
		JLabel lblNewLabel_3 = new JLabel("Release Date");
		lblNewLabel_3.setBounds(225, 14, 96, 14);
		panel_1.add(lblNewLabel_3);
		
		//Creates TextField
		Pricetxt = new JTextField();
		Pricetxt.setBounds(214, 98, 96, 20);
		panel_1.add(Pricetxt);
		Pricetxt.setColumns(10);
		
		//Creates Label
		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setBounds(249, 73, 48, 14);
		panel_1.add(lblNewLabel_4);
		
		//Creates Label
		JLabel lblNewLabel_5 = new JLabel("Genre");
		lblNewLabel_5.setBounds(73, 200, 48, 14);
		panel_1.add(lblNewLabel_5);
		
		//Creates Text Field
		Quantitytxt = new JTextField();
		Quantitytxt.setBounds(214, 162, 96, 20);
		panel_1.add(Quantitytxt);
		Quantitytxt.setColumns(10);
		
		//Creates ComboBox storing Extra info 2
		JComboBox info2box = new JComboBox();
		info2box.setBounds(370, 97, 96, 22);
		panel_1.add(info2box);
		info2box.addItem("new");
		info2box.addItem("used");
		
		//Creates Label
		JLabel lblNewLabel_6 = new JLabel("Quantity");
		lblNewLabel_6.setBounds(238, 137, 72, 14);
		panel_1.add(lblNewLabel_6);
		
		//Creates ComboBox storing book type
		JComboBox Typebox = new JComboBox();
		Typebox.setBounds(214, 225, 96, 22);
		Typebox.addItem("Paperback");
		Typebox.addItem("Ebook");
		Typebox.addItem("Audiobook");
		panel_1.add(Typebox);
		Typebox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent type) {
				//Based on selected book type secondary info ComboBox will have different values
				if (type.getItem() == "Paperback") {
					info2box.removeAllItems();
					info2box.addItem("new");
					info2box.addItem("used");
				} else if (type.getItem() == "Ebook") {
					info2box.removeAllItems();
					info2box.addItem(Format_E.AZW3);
					info2box.addItem(Format_E.EPUB);
					info2box.addItem(Format_E.MOBI);
					info2box.addItem(Format_E.PDF);
				} else {
					info2box.removeAllItems();
					info2box.addItem(Format_A.AAC);
					info2box.addItem(Format_A.MP3);
					info2box.addItem(Format_A.WMA);
				}
			}
		});
		
		//Create Label
		JLabel lblNewLabel_7 = new JLabel("Type");
		lblNewLabel_7.setBounds(249, 200, 48, 14);
		panel_1.add(lblNewLabel_7);
		
		//Creates Text Field
		info1txt = new JTextField();
		info1txt.setBounds(370, 39, 96, 20);
		panel_1.add(info1txt);
		info1txt.setColumns(10);
		
		//Creates Label
		JLabel lblNewLabel_8 = new JLabel("Pages/Length");
		lblNewLabel_8.setBounds(370, 14, 96, 14);
		panel_1.add(lblNewLabel_8);
		
		//Creates Label
		JLabel lblNewLabel_9 = new JLabel("Condition/Format");
		lblNewLabel_9.setBounds(370, 73, 109, 14);
		panel_1.add(lblNewLabel_9);
		
		//Add book button
		JButton AddBook = new JButton("Add Book");
		AddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = false;
				//Checks each text field for invalid entries
				//Displays error pop messages for each error
				String isbn = ISBNtxt.getText();
				if (!(user.checkIsbn(isbn))) {
					JOptionPane.showMessageDialog(null,"ISBN ERROR","Error",JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				String title = Titletxt.getText();
				if (title.equals("")) {
					JOptionPane.showMessageDialog(null,"TITLE MISSING","Error",JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				String date = Releasetxt.getText();
				if (!(user.checkDate(date.trim()))) {
					JOptionPane.showMessageDialog(null,"INCORRECT DATE FORMAT","Error",JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				String price = Pricetxt.getText();
				if (!(user.checkPrice(price))) {
					JOptionPane.showMessageDialog(null,"INVALID PRICE","Error",JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				String quantity = Quantitytxt.getText();
				if (!(user.checkInt(quantity))) {
					JOptionPane.showMessageDialog(null,"INVALID QUANTITY","Error",JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				String info1 = info1txt.getText();
				if ((String.valueOf(Typebox.getSelectedItem()) == "Paperback") || (String.valueOf(Typebox.getSelectedItem()) == "Ebook")){
					if (!(user.checkInt(info1))) {
						JOptionPane.showMessageDialog(null,"INVALID PAGES","Error",JOptionPane.ERROR_MESSAGE);
						error = true;
					}
				} else {
					if (!(user.checkLength(info1))) {
						JOptionPane.showMessageDialog(null,"INVALID LENGTH","Error",JOptionPane.ERROR_MESSAGE);
						error = true;
					}
				}
				
				//Book will be added if no errors occurred
				if (!error) {
					try {
						user.addBook(isbn, Typebox.getSelectedItem().toString(), title, LangBox.getSelectedItem().toString(), Genrebox.getSelectedItem().toString(), date, price, quantity, info1, info2box.getSelectedItem().toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		AddBook.setBounds(370, 177, 96, 37);
		panel_1.add(AddBook);
		
		//Creates logout button
		JButton LogoutButton = new JButton("Logout");
		LogoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Disposes the frame
				dispose();
			}
		});
		LogoutButton.setBounds(527, 14, 174, 309);
		panel_1.add(LogoutButton);


	}
}

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class CustFrame extends JFrame {
	//Attributes
	private JPanel contentPane;
	private JTable BookTable;
	private DefaultTableModel dtmbooks;
	private DefaultTableModel dtmcheck;
	private ArrayList<Book> Books;
	private JTable CheckoutTable;
	private JTextField PayPaltxt;
	private JTextField digit6txt;
	private JTextField digit3txt;
	private JLabel PayPalLabel;
	private JButton PayButton;
	private JLabel CreditLabel;
	private JLabel Digit3Label;
	private JLabel Digit6Label;
	private double price = 0;



	/**
	 * Create the frame.
	 */
	public CustFrame(ArrayList<Book> Books,Customer user) {
		this.Books = Books;
		//Creates Panel and sets close operation
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Created tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 611, 351);
		contentPane.add(tabbedPane);
		
		//Creates panel
		JPanel BookPanel = new JPanel();
		tabbedPane.addTab("Books", null, BookPanel, null);
		BookPanel.setLayout(null);
		
		//Adds a scroll pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 436, 301);
		BookPanel.add(scrollPane);
		
		//Creates a JTable
		BookTable = new JTable();
		scrollPane.setViewportView(BookTable);
		
		//Uses DefaultTableModel to setup JTable
		dtmbooks = new DefaultTableModel();
		dtmbooks.setColumnIdentifiers(new Object[] {"ISBN","Book Type","Title","Language","Genre","Release Date","Price","Quantity","Info 1","Info 2"});
		BookTable.setModel(dtmbooks);
		
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
		
		//Creates a ComboBox filled with filter options for the table
		String[] filters = {"No Filter","Politics","Medicine","Business","Computer Science","Biography","Audiobook 5hr+"};
		JComboBox FilterBox = new JComboBox(filters);
		FilterBox.setBounds(462, 38, 134, 22);
		BookPanel.add(FilterBox);
		
		//Creates Filter Button
		JButton FilterButton = new JButton("Apply");
		FilterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Obtains selected filter
				String filter = String.valueOf(FilterBox.getSelectedItem());
				
				//Creates a TableRowSorter for the JTable
				TableRowSorter<DefaultTableModel> tablerow = new TableRowSorter<DefaultTableModel>(dtmbooks);
				BookTable.setRowSorter(tablerow);
				
				if (filter.equals("No Filter")){
					//Gets rid of any row filters
					tablerow.setRowFilter(null);
				}else if (filter.equals("Audiobook 5hr+")){
					//List of row filters used to have two row filter a once
					List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
					//Filters out book types
					filters.add(RowFilter.regexFilter("audiobook",1));
					//Filters out books with length less than 5
				    filters.add(RowFilter.numberFilter(ComparisonType.AFTER,5,8));
					RowFilter<Object,Object> audioFilter = RowFilter.andFilter(filters);
					tablerow.setRowFilter(audioFilter);
				}else {
					//filters based on the genre column
					tablerow.setRowFilter(RowFilter.regexFilter(filter,4));
				}

			}
		});
		FilterButton.setBounds(484, 71, 89, 23);
		BookPanel.add(FilterButton);
		
		//Adds Refresh Button
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
				
				//Loops through Books ArrayList
				for (Book book : Books) {
					if (book.getType() == "paperback") {
						//Casts books into correct book type and adds their data to the books DefaultTableModel
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
		RefreshButton.setBounds(484, 127, 89, 23);
		BookPanel.add(RefreshButton);
		
		//Creates Panel
		JPanel CheckPanel = new JPanel();
		tabbedPane.addTab("Checkout", null, CheckPanel, null);
		CheckPanel.setLayout(null);
		
		//Adds Scroll Pane
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 358, 276);
		CheckPanel.add(scrollPane_1);
		
		//Creates Label
		JLabel PriceLabel = new JLabel("Price:");
		PriceLabel.setBounds(123, 298, 129, 14);
		CheckPanel.add(PriceLabel);
		
		//Creates JTable
		CheckoutTable = new JTable();
		scrollPane_1.setViewportView(CheckoutTable);
		
		//Uses DefaultTableModel to setup JTable
		dtmcheck = new DefaultTableModel();
		dtmcheck.setColumnIdentifiers(new Object[] {"ISBN","Book Type","Title","Language","Genre","Release Date","Price","Quantity","Info 1","Info 2"});
		CheckoutTable.setModel(dtmcheck);
		
		//Creates Add Button
		JButton AddButton = new JButton("Add");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checks if user selected a row
				if(BookTable.getSelectedRow() == -1) {
					//Displays error
					JOptionPane.showMessageDialog(null,"BOOK NOT SELECTED","Error",JOptionPane.ERROR_MESSAGE);
				} else {
					//Obtains row data of selected row
					Object[] rowdata = dtmbooks.getDataVector().elementAt(BookTable.getSelectedRow()).toArray();
					
					//Checks the quantity of the book
					if (rowdata[7].equals(0)) {
						JOptionPane.showMessageDialog(null,"BOOK OUT OF STOCK","Error",JOptionPane.ERROR_MESSAGE);
					} else {
						//Sets quantity in row data to 1 and stores the isbn of the selected book
						rowdata[7] = 1;
						int isbn = Integer.parseInt(rowdata[0].toString());
						boolean exists = false;
						int i;
						//Checks if the book has been selected before
						for (i = 0; i<CheckoutTable.getRowCount();i++) {
							String s = CheckoutTable.getValueAt(i, 0).toString().trim();
							if (isbn == Integer.parseInt(s)) {
								exists = true;
								break;
							}
						}
						
						//If Book has not been selected before it is added to the table
						if (!(exists)) {
							dtmcheck.addRow(rowdata);
						} else {
							//Gets quantity of book already selected
							int quantity = (Integer.parseInt(dtmcheck.getValueAt(i, 7).toString()));
							int c;
							
							for (c = 0; c<BookTable.getRowCount();c++) {
								String s = BookTable.getValueAt(c, 0).toString().trim();
								if (isbn == Integer.parseInt(s)) {
									break;
								}
							}
							//Checks quantity of book selected to max quantity stored in the book table
							if (quantity == Integer.parseInt(dtmbooks.getValueAt(c, 7).toString())) {
								//Displays Error if max quantity is reached
								JOptionPane.showMessageDialog(null,"MAX QUANTITY REACHED","Error",JOptionPane.ERROR_MESSAGE);
							}else {
								//Adds to quantity and sets value in checkout table 
								quantity++;
								dtmcheck.setValueAt((Object)quantity, i, 7);
							}
						}
						//Calculates price of books in checkout table
						price = 0;
						for (i = 0; i<CheckoutTable.getRowCount();i++) {
							price += (Double.parseDouble(CheckoutTable.getValueAt(i, 6).toString().trim()) * Double.parseDouble(CheckoutTable.getValueAt(i, 7).toString().trim()));
						}
						//Sets price label
						PriceLabel.setText("Price: "+price);
					}
				}
			}
		});
		AddButton.setBounds(484, 210, 89, 23);
		BookPanel.add(AddButton);
		
		//Creates Label
		JLabel lblNewLabel = new JLabel("Filter");
		lblNewLabel.setBounds(514, 12, 48, 14);
		BookPanel.add(lblNewLabel);
		
		//Creates Label
		JLabel lblNewLabel_1 = new JLabel("Click Book");
		lblNewLabel_1.setBounds(494, 161, 78, 22);
		BookPanel.add(lblNewLabel_1);
		
		//Creates Label
		JLabel lblNewLabel_2 = new JLabel("Then Press Button");
		lblNewLabel_2.setBounds(472, 185, 112, 14);
		BookPanel.add(lblNewLabel_2);
		
		//Creates Logout Button
		JButton LogoutButton = new JButton("Logout");
		LogoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Disposes the Frame
				dispose();
			}
		});
		LogoutButton.setBounds(484, 256, 89, 56);
		BookPanel.add(LogoutButton);
		
		//Creates PayPal Button
		JButton PayPalButton = new JButton("PayPal");
		PayPalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Disables Credit Card related text fields and labels
				digit6txt.setVisible(false);
				digit3txt.setVisible(false);
				CreditLabel.setVisible(false);
				Digit6Label.setVisible(false);
				Digit3Label.setVisible(false);
				//Sets PayPal related text fields and labels to visible
				PayPaltxt.setVisible(true);
				PayPalLabel.setVisible(true);
				PayButton.setVisible(true);
			}
		});
		PayPalButton.setBounds(437, 47, 107, 23);
		CheckPanel.add(PayPalButton);
		
		//Creates Credit Card button
		JButton CreditButton = new JButton("Credit Card");
		CreditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Disables PayPal related text fields and labels
				PayPaltxt.setVisible(false);
				PayPalLabel.setVisible(false);
				//Sets Credit Card related text fields and labels to visible
				digit6txt.setVisible(true);
				digit3txt.setVisible(true);
				CreditLabel.setVisible(true);
				Digit6Label.setVisible(true);
				Digit3Label.setVisible(true);
				PayButton.setVisible(true);
			}
		});
		CreditButton.setBounds(437, 81, 107, 23);
		CheckPanel.add(CreditButton);
		
		//Creates Label
		JLabel lblNewLabel_3 = new JLabel("Select Payment Method");
		lblNewLabel_3.setBounds(420, 23, 153, 14);
		CheckPanel.add(lblNewLabel_3);
		
		//Creates Text Field
		PayPaltxt = new JTextField();
		PayPaltxt.setBounds(402, 138, 181, 20);
		CheckPanel.add(PayPaltxt);
		PayPaltxt.setColumns(10);
		PayPaltxt.setVisible(false);
		
		//Creates Label
		PayPalLabel = new JLabel("Enter PayPal Email Address");
		PayPalLabel.setBounds(412, 113, 171, 14);
		CheckPanel.add(PayPalLabel);
		PayPalLabel.setVisible(false);
		
		//Creates Text Field
		digit6txt = new JTextField();
		digit6txt.setBounds(437, 184, 96, 20);
		CheckPanel.add(digit6txt);
		digit6txt.setColumns(10);
		digit6txt.setVisible(false);
		
		//Creates Text Field
		digit3txt = new JTextField();
		digit3txt.setBounds(437, 215, 96, 20);
		CheckPanel.add(digit3txt);
		digit3txt.setColumns(10);
		digit3txt.setVisible(false);
		
		//Creates Label
		CreditLabel = new JLabel("Enter Credit Card Details");
		CreditLabel.setBounds(420, 159, 153, 14);
		CheckPanel.add(CreditLabel);
		CreditLabel.setVisible(false);
		
		//Creates Label
		Digit6Label = new JLabel("6 Digits");
		Digit6Label.setBounds(379, 187, 48, 14);
		CheckPanel.add(Digit6Label);
		Digit6Label.setVisible(false);
		
		//Creates Label
		Digit3Label = new JLabel("3 Digits");
		Digit3Label.setBounds(378, 218, 48, 14);
		CheckPanel.add(Digit3Label);
		Digit3Label.setVisible(false);
		
		
		//Creates Pay Button
		PayButton = new JButton("Pay");
		PayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checks if the Checkout Table has books in it
				if (dtmcheck.getRowCount() == 0) {
					//Displays error message
					JOptionPane.showMessageDialog(null,"NO BOOKS IN BASKET","Error",JOptionPane.ERROR_MESSAGE);
				} else {
					boolean error = false;
					//Checks for errors in PayPal text field if the text field is visible
					if (PayPaltxt.isVisible()) {
						//Checks if the email address valid
						String email = PayPaltxt.getText();
						if (!(user.checkPayPal(email))) {
							//Displays error message
							JOptionPane.showMessageDialog(null,"EMAIL ERROR","Error",JOptionPane.ERROR_MESSAGE);
							error = true;
						}
						//Checks if an error occurred
						if (!error) {
							//Displays amount payed
							JOptionPane.showMessageDialog(null,price+" paid using PayPal","Payment",JOptionPane.INFORMATION_MESSAGE);							
							//Sets users basket
							user.setBasket(dtmcheck);
							//Handles IOException
							try {
								//Updates stock and activity log file
								user.removeStock();
								user.purchaseActivity("PayPal");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							//Clears Basket and checkout table and price label
							PriceLabel.setText("Price: ");
							dtmcheck.setRowCount(0);
							user.clearBasket();						
						}
					//Checks for errors in Credit Card text fields if the text field is visible
					} else {
						//Checks if 6 digits are valid
						String digit6= digit6txt.getText();
						if (!(user.check6Digit(digit6))) {
							//Displays error message
							JOptionPane.showMessageDialog(null,"6 DIGITS ERROR","Error",JOptionPane.ERROR_MESSAGE);
							error = true;
						}
						//Checks if 3 digits are valid
						String digit3= digit3txt.getText();
						if (!(user.check3Digit(digit3))) {
							//Displays error message
							JOptionPane.showMessageDialog(null,"3 DIGITS ERROR","Error",JOptionPane.ERROR_MESSAGE);
							error = true;
						}
						if (!error) {
							//Displays amount payed
							JOptionPane.showMessageDialog(null,price+" paid using Credit Card","Payment",JOptionPane.INFORMATION_MESSAGE);
							//Sets users basket
							user.setBasket(dtmcheck);
							//Handles IOException
							try {
								//Updates stock and activity log file
								user.removeStock();
								user.purchaseActivity("Credit Card");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							//Clears Basket and checkout table and price label
							PriceLabel.setText("Price: ");
							dtmcheck.setRowCount(0);
							user.clearBasket();
						}
					}
				}
			}
		});
		PayButton.setBounds(437, 246, 96, 23);
		CheckPanel.add(PayButton);
		PayButton.setVisible(false);
		
		//Creates cancel button
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Sets users basket
				user.setBasket(dtmcheck);
				//Handles IOException
				try {
					//Updates activity logs
					user.cancelActivity();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Clears Basket and checkout table and price label
				PriceLabel.setText("Price: ");
				dtmcheck.setRowCount(0);
				user.clearBasket();
			}
		});
		CancelButton.setBounds(437, 280, 96, 23);
		CheckPanel.add(CancelButton);		
	}
}

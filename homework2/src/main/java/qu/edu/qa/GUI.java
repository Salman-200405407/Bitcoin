package qu.edu.qa;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import qa.edu.qu.bean.Input;
import qa.edu.qu.bean.MyKeyPair;
import qa.edu.qu.bean.Output;
import qa.edu.qu.bean.Transaction;
import qa.edu.qu.util.CryptographyUtils;

public class GUI extends JFrame {

	private static final long serialVersionUID = 7621801269360253372L;
	private JPanel contentPane;
	private MyKeyPair keyPair;
	private Transaction transaction;
	private JTextField textFieldPrevTxHash;
	private JTextField textFieldIndex;
	private JTextField textFieldSignature;
	private JTextField textFieldValue;
	private JTextArea textAreaPublicKey;
	private JTextArea textAreaPrivateKey;
	protected byte[] signature;
	private JTextArea textAreaOutPubKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 800, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPublicKey = new JLabel("Public key");
		lblPublicKey.setBounds(25, 11, 112, 14);
		contentPane.add(lblPublicKey);

		JLabel lblPrivateKey = new JLabel("Private Key");
		lblPrivateKey.setBounds(25, 114, 68, 14);
		contentPane.add(lblPrivateKey);

		JLabel lblTransaction = new JLabel("Transaction");
		lblTransaction.setBounds(25, 258, 83, 14);
		contentPane.add(lblTransaction);

		JLabel lblListOfInput = new JLabel("List Of Inputs:");
		lblListOfInput.setBounds(25, 283, 115, 14);
		contentPane.add(lblListOfInput);

		JLabel lblListOfOutput = new JLabel("List Of Output:");
		lblListOfOutput.setBounds(367, 283, 112, 14);
		contentPane.add(lblListOfOutput);

		JLabel lblPreviousTxHash = new JLabel("Previous Tx Hash:");
		lblPreviousTxHash.setBounds(25, 308, 112, 14);
		contentPane.add(lblPreviousTxHash);

		JLabel lblIndex = new JLabel("Index:");
		lblIndex.setBounds(25, 364, 46, 14);
		contentPane.add(lblIndex);

		JLabel lblSignature = new JLabel("Signature:");
		lblSignature.setBounds(25, 407, 83, 14);
		contentPane.add(lblSignature);

		JLabel lblOutputValue = new JLabel("Value:");
		lblOutputValue.setBounds(369, 308, 46, 14);
		contentPane.add(lblOutputValue);

		JLabel lblOutputPublicKey = new JLabel("Public Key:");
		lblOutputPublicKey.setBounds(367, 364, 74, 14);
		contentPane.add(lblOutputPublicKey);
	
		textFieldPrevTxHash = new JTextField();
		textFieldPrevTxHash.setBounds(22, 333, 115, 20);
		contentPane.add(textFieldPrevTxHash);
		textFieldPrevTxHash.setColumns(10);


		textFieldIndex = new JFormattedTextField(NumberFormat.getNumberInstance());
		textFieldIndex.setBounds(25, 376, 46, 20);
		contentPane.add(textFieldIndex);
		textFieldIndex.setColumns(10);


		textFieldSignature = new JTextField();
		textFieldSignature.setBounds(25, 420, 115, 20);
		contentPane.add(textFieldSignature);
		textFieldSignature.setColumns(10);


		textFieldValue = new JTextField();

		textFieldValue.setBounds(367, 333, 124, 20);
		contentPane.add(textFieldValue);
		textFieldValue.setColumns(10);

		textAreaOutPubKey = new JTextArea();
		textAreaOutPubKey.setLineWrap(true);
		textAreaOutPubKey.setBounds(367, 389, 313, 97);
		contentPane.add(textAreaOutPubKey);
		
		textAreaPublicKey = new JTextArea();
		textAreaPublicKey.setLineWrap(true);
		textAreaPublicKey.setBounds(22, 25, 371, 85);
		contentPane.add(textAreaPublicKey);

		textAreaPrivateKey = new JTextArea();
		textAreaPrivateKey.setLineWrap(true);
		textAreaPrivateKey.setBounds(25, 139, 368, 97);
		contentPane.add(textAreaPrivateKey);

		initData();
		
		prepareGenerateKeyButton();
		
		prepareSignInputButton();

		prepareVerifySignButton();

		setTextFieldsAction();

	}

	private void setTextFieldsAction() {
		textFieldPrevTxHash.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				transaction.getInputs().get(0).setPrevTxHash(textFieldPrevTxHash.getText());
			}
		});		
		
		textFieldIndex.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				transaction.getInputs().get(0).setOutputIndex(new Integer(textFieldIndex.getText()));
			}
		});
		
		textFieldSignature.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				transaction.getInputs().get(0).setSignature(textFieldSignature.getText());
			}
		});
		
		textFieldValue.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				transaction.getOutputs().get(0).setValue(new Double(textFieldValue.getText()));
			}
		});
		
	}

	private void prepareVerifySignButton() {
		JButton btnSignature = new JButton("Verify Signature");
		btnSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// decrypt the signature to get the hash of the object and then compare it with
					// the hash of the object that we currently have
					byte[] decryptedObject = CryptographyUtils.getInstance().decryptObject(signature,
							keyPair.getPublicKey());

					byte[] hash = CryptographyUtils.getInstance().getHash(transaction);

					if (Arrays.equals(decryptedObject, hash)) {
						JOptionPane.showMessageDialog(null, "Valid Signature");
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Signature");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSignature.setBounds(146, 531, 140, 23);
		contentPane.add(btnSignature);
	}

	private void prepareSignInputButton() {
		JButton btnSignInput = new JButton("Sign Input");
		btnSignInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaOutPubKey.setText(textAreaPublicKey.getText());
				try {
					//get the signature by encrypting the transaction object using the private key
					signature = CryptographyUtils.getInstance().encryptObject(transaction, keyPair.getPrivateKey());
					textFieldSignature.setText(new String(signature));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSignInput.setBounds(25, 531, 112, 23);
		contentPane.add(btnSignInput);
	}

	private void prepareGenerateKeyButton() {
		JButton btnGenerateRandom = new JButton("Generate Random");
		btnGenerateRandom.setBounds(403, 37, 156, 44);
		btnGenerateRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// generate the key pairs and set them in the ui text
				keyPair = CryptographyUtils.getInstance().getKeyPair();
				textAreaPublicKey.setText(Base64.getEncoder().encodeToString(keyPair.getPublicKey().getEncoded()));
				textAreaPrivateKey.setText(Base64.getEncoder().encodeToString(keyPair.getPrivateKey().getEncoded()));
			}
		});
		contentPane.add(btnGenerateRandom);
	}

	private void initData() {
		transaction = new Transaction();
		List<Input> inputs = new ArrayList<Input>();
		Input input = new Input();
		inputs.add(input);
		transaction.setInputs(inputs);

		List<Output> outputs = new ArrayList<Output>();
		Output output = new Output();

		outputs.add(output);
		transaction.setOutputs(outputs);

		textFieldIndex.setText("0");
		textFieldValue.setText("0");
	}
}

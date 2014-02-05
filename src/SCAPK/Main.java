package SCAPK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Main {
	
	static JFrame mainFrame;
	static JTextPane codeTP, outputTP;
	static JSplitPane splitPane;
	static JMenuBar mainMenu;
	static JMenu file, run;
	static JFileChooser fc;
	static JButton openFielBtn, saveBtn, runBtn;
	public static String[] inputs, outputs;
	public static boolean badFormatError;
	public static ArrayList<String> errors, quickFix;
	public static String blueWords;
	
	Main() {
		
		mainFrame = new JFrame();
		mainFrame.setTitle("Sequential Circuit Analysis");
		mainFrame.setSize(800, 600);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
		
		fc = new JFileChooser();
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("Text Documents(*.txt)", "txt", "text");
		fc.setFileFilter(filter);
				
		codeTP = new JTextPane(setStyle());
		codeTP.setDragEnabled(true);
		
		outputTP = new JTextPane();
		
		outputTP.setEditable(false);
		outputTP.setBackground(new Color(220, 220, 200));
		
		JScrollPane codeTPSP = new JScrollPane(codeTP);
		JScrollPane outputTPSP = new JScrollPane(outputTP);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, codeTPSP, outputTPSP);
		splitPane.setDividerLocation(380);
		
		mainMenu = new JMenuBar();
		
		file = new JMenu("File");
		run = new JMenu("Run");
		
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveAction();
			}
			
		});
		
		JMenuItem open = new JMenuItem("Open");
		open.setMnemonic(KeyEvent.VK_O);
		open.setAccelerator(
	                KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(clean(codeTP.getText()).isEmpty())
					openAction();
				else {
					File file = null;
					int returnVal = fc.showOpenDialog(mainFrame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							file = fc.getSelectedFile();
							//codeTP.setText(getFileData(file.getPath()));
						}
						
						int rslt =
								JOptionPane.showConfirmDialog
								(mainFrame, "Do you want to save changes?", "SCA", JOptionPane.YES_NO_CANCEL_OPTION);
						switch(rslt) {
						case JOptionPane.YES_OPTION :
							saveAction();
							codeTP.setText(getFileData(file.getPath()));
							outputTP.setText("");
							mainFrame.setTitle("Sequential Circut Analysis (" + file.getName() +")");
							break;
						case JOptionPane.NO_OPTION:
							codeTP.setText(getFileData(file.getPath()));
							outputTP.setText("");
							mainFrame.setTitle("Sequential Circut Analysis (" + file.getName() +")");
							break;
						case JOptionPane.CANCEL_OPTION:
							break;
						}
					}
				}
			}
		});
		
		JMenuItem setNew = new JMenuItem("New");
		setNew.setMnemonic(KeyEvent.VK_N);
		setNew.setAccelerator(
	                KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		setNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(clean(codeTP.getText()).isEmpty()) {
					codeTP.setText("");
					outputTP.setText("");
					mainFrame.setTitle("Sequential Circut Analysis");
				} else {
					int rslt = 
							JOptionPane.showConfirmDialog
							(mainFrame, "Do you want to save changes?", "SCA", JOptionPane.YES_NO_CANCEL_OPTION);
					switch(rslt) {
					case JOptionPane.YES_OPTION :
						saveAction();
						codeTP.setText("");
						outputTP.setText("");
						mainFrame.setTitle("Sequential Circut Analysis");
						break;
					case JOptionPane.NO_OPTION:
						codeTP.setText("");
						outputTP.setText("");
						mainFrame.setTitle("Sequential Circut Analysis");
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
						
					}
				}
			}
		});

		JMenuItem exit = new JMenuItem("Exit");

		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				if(clean(codeTP.getText()).isEmpty())
					mainFrame.dispose();
				else {
					int rslt = 
							JOptionPane.showConfirmDialog
							(mainFrame, "Do you want to save changes?", "SCA", JOptionPane.YES_NO_CANCEL_OPTION);
					switch(rslt) {
					case JOptionPane.YES_OPTION :
						saveAction();
						mainFrame.dispose();
						break;
					case JOptionPane.NO_OPTION:
						mainFrame.dispose();
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
						
					}
				}
			}
		});
				
		file.add(setNew);
		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		
		JMenuItem runItem = new JMenuItem("Run");
		runItem.setMnemonic(KeyEvent.VK_R);
		runItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		runItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!clean(codeTP.getText()).isEmpty())
					run();
				else
					outputTP.setText("Codepad is empty!");
			}
			
		});
		run.add(runItem);
		
		
		mainMenu.add(file);
		mainMenu.add(run);
		
		runBtn = new JButton("Run", createImageIcon("images/run.gif"));
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!clean(codeTP.getText()).isEmpty())
					run();
				else
					outputTP.setText("Codepad is empty!");
			}
		});
		
		openFielBtn = new JButton("Open", createImageIcon("images/openFile.gif"));
		openFielBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(clean(codeTP.getText()).isEmpty())
					openAction();
				else {
					File file = null;
					int returnVal = fc.showOpenDialog(mainFrame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						if(returnVal == JFileChooser.APPROVE_OPTION)
							file = fc.getSelectedFile();
						int rslt =
								JOptionPane.showConfirmDialog
								(mainFrame, "Do you want to save changes?", "SCA", JOptionPane.YES_NO_CANCEL_OPTION);
						switch(rslt) {
						case JOptionPane.YES_OPTION :
							saveAction();
							codeTP.setText(getFileData(file.getPath()));
							outputTP.setText("");
							mainFrame.setTitle("Sequential Circut Analysis (" + file.getName() +")");
							break;
						case JOptionPane.NO_OPTION:
							codeTP.setText(getFileData(file.getPath()));
							outputTP.setText("");
							mainFrame.setTitle("Sequential Circut Analysis (" + file.getName() +")");
							break;
						case JOptionPane.CANCEL_OPTION:
							break;
						}
					}
				}
			}
		});
		
		saveBtn = new JButton("Save", createImageIcon("images/save.gif"));
		saveBtn.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveAction();
			}
		});
		
		runBtn.setPreferredSize(new Dimension(90,35));
		openFielBtn.setPreferredSize(new Dimension(90,35));
		saveBtn.setPreferredSize(new Dimension(90,35));
		
		JPanel mainBtnPanel = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel();
		btnPanel.add(openFielBtn);
		btnPanel.add(saveBtn);
		btnPanel.add(runBtn);
		
		mainBtnPanel.add(btnPanel, BorderLayout.WEST);
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(mainMenu, BorderLayout.NORTH);
		topPanel.add(mainBtnPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(splitPane, BorderLayout.CENTER);

		
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}
	
	public static DefaultStyledDocument setStyle() {
		
		  int fontSize = 12;
		  String fontType = "toham";
	        
		  final SimpleAttributeSet red = new SimpleAttributeSet();
		  red.addAttribute(StyleConstants.FontFamily, fontType);
		  red.addAttribute(StyleConstants.Size, new Integer(fontSize));
		  red.addAttribute(StyleConstants.Bold, new Boolean(true));
		  red.addAttribute(StyleConstants.Foreground, new Color(149, 0, 85));
		  
		  final SimpleAttributeSet black = new SimpleAttributeSet();
		  black.addAttribute(StyleConstants.FontFamily, fontType);
		  black.addAttribute(StyleConstants.Size, new Integer(fontSize));
		  black.addAttribute(StyleConstants.Bold, new Boolean(false));
		  black.addAttribute(StyleConstants.Foreground, Color.BLACK);
	        
		  final SimpleAttributeSet blue = new SimpleAttributeSet();
		  blue.addAttribute(StyleConstants.FontFamily, fontType);
		  blue.addAttribute(StyleConstants.Size, new Integer(fontSize));
		  blue.addAttribute(StyleConstants.Bold, new Boolean(false));
		  blue.addAttribute(StyleConstants.Foreground, Color.BLUE);
		  
		  final SimpleAttributeSet blueBold = new SimpleAttributeSet();
		  blueBold.addAttribute(StyleConstants.FontFamily, fontType);
		  blueBold.addAttribute(StyleConstants.Size, new Integer(fontSize));
		  blueBold.addAttribute(StyleConstants.Bold, new Boolean(true));
		  blueBold.addAttribute(StyleConstants.Foreground, Color.BLUE);
		  
		  final String redWords = 
				  "(?i)(\\W)*(FF_TYPE|FF_NO|INPUT_NO|OUTPUT_NO|"
				  + "INTERNAL_NO|INPUTS|OUTPUTS|INTERNALS|GATE|FF)";
		  final String blueBoldWords = "(?i)(\\W)*(DESIGN)";
		  blueWords = "(?i)(\\W)*(AND|NAND|OR|NOR|XOR|XNOR"
		  		+ "|INVERTER|BUFFER|JK|D|SR|T)";
		  
		  DefaultStyledDocument doc = new DefaultStyledDocument() {
			  public void insertString 
			  (int offset, String str, AttributeSet a) throws BadLocationException {
				  super.insertString(offset, str, a);
				  
				  String text = getText(0, getLength());
				  int before = findLastNonWordChar(text, offset);
				  if (before < 0) before = 0;
				  int after = findFirstNonWordChar(text, offset + str.length());
				  int wordL = before;
				  int wordR = before;
				  
				  while (wordR <= after) {
					  if (wordR == after ||
							  String.valueOf(text.charAt(wordR)).matches("\\W")) {
						  if (text.substring(wordL, wordR).matches(redWords))
							  setCharacterAttributes(wordL, wordR - wordL, red, false);
						  else if (text.substring(wordL, wordR).matches(blueBoldWords))
							  setCharacterAttributes(wordL, wordR - wordL, blueBold, false);
						  else if (text.substring(wordL, wordR).matches(blueWords))
							  setCharacterAttributes(wordL, wordR - wordL, blue, false);
						  else
							  setCharacterAttributes(wordL, wordR - wordL, black, false);
						  wordL = wordR;
					  }
					  wordR++;
				  }
			  }

			  public void remove (int offs, int len) throws BadLocationException {
				  super.remove(offs, len);

				  String text = getText(0, getLength());
				  int before = findLastNonWordChar(text, offs);
				  if (before < 0) before = 0;
				  int after = findFirstNonWordChar(text, offs);

				  if (text.substring(before, after).matches(redWords))
					  setCharacterAttributes(before, after - before, red, false);
				  else if (text.substring(before, after).matches(blueBoldWords))
					  setCharacterAttributes(before, after - before, blueBold, false);
				  else if (text.substring(before, after).matches(blueWords))
					  setCharacterAttributes(before, after - before, blue, false);
				  else 
					  setCharacterAttributes(before, after - before, black, false);
			  }
		  };
		  
		  return doc;	
	}
	
	private static int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private static int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    private static String getFileData(String s) {
    	try {
			Scanner inFile = new Scanner(new FileReader(s));
			s = "";
			
			while(inFile.hasNext()) 
				s += inFile.nextLine() + "\n";
			return s;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    private static void saveAction() {
    	int returnVal = fc.showSaveDialog(mainFrame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
		
			PrintWriter writer;
			try {
				writer = new PrintWriter(file.getPath(), "UTF-8");
				writer.println(codeTP.getText());
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    }
    
    private static void openAction() {
    	
		int returnVal = fc.showOpenDialog(mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			codeTP.setText(getFileData(file.getPath()));
			outputTP.setText("");
			mainFrame.setTitle("Sequential Circut Analysis (" + file.getName() +")");
		}
		
    }
    
    public static String clean (String s) {
		Pattern replace = Pattern.compile("[\\s|-]+");
		Matcher regexMatcher = replace.matcher(s.trim());
		return regexMatcher.replaceAll("");
	}
    
	public static void run() {
		errors = new ArrayList<String>();
		quickFix = new ArrayList<String>();
		
		badFormatError = false;
		
		int FF_NO = 0,input_NO = 0, outPut_NO = 0;
		String FF_Type;
		TreeMap<String, String> values = new TreeMap<String, String>();
		ArrayList<String> design = new ArrayList<String>();
		boolean foundDesign = false;
		
		String[] s = codeTP.getText().split("\n");

		String str;
		
		for(String i : s) {
			str = clean(i);
			if(!foundDesign) {
				if(str.toUpperCase().equals("DESIGN:")) 
					foundDesign = true;
				else {	
					if(str.length() == 0)
						continue;
					String[] arr = str.split(":");
					try {
					values.put(arr[0].toUpperCase(), arr[1]);
					} catch(ArrayIndexOutOfBoundsException e) {
						if(arr[0].toUpperCase().equals("INPUTS"))
							continue;
						badFormatError = true;
						break;
					}
				}
			} else {
				design.add(str);
			}
		}
		
		if(!badFormatError) {
		try {
			inputs = values.get("INPUTS").split(",");
		} catch(NullPointerException e){
			//no i/o
			inputs = new String[1];
			inputs[0] = "X";
		}
		try {
			outputs = values.get("OUTPUTS").split(",");
		} catch(NullPointerException e){
			quickFix.add("   add OUTPUTS");
		}
		
		String[] internals	 = values.get("INTERNALS").split(",");
		try {
			FF_NO = Integer.parseInt(values.get("FF_NO"));
		} catch(NumberFormatException e) {
			quickFix.add("   add FF_NO");
		}
		try {
			input_NO = Integer.parseInt(values.get("INPUT_NO"));
		} catch(NumberFormatException e) {
			quickFix.add("   add INPUT_NO");
		}
		try {
			outPut_NO = Integer.parseInt(values.get("OUTPUT_NO"));
		} catch(NumberFormatException e) {
			quickFix.add("   add OUTPUT_NO");
		}
		if(quickFix.size() == 0) {
		SCA test = new SCA(input_NO, outPut_NO, FF_NO);
			
		test.setInputs(inputs);
		test.setOutpuuts(outputs);
		test.setInternals(internals);
		try{
			test.FF_TYPE = (values.get("FF_TYPE").toUpperCase());
		} catch(NullPointerException e) {
			quickFix.add("   add FF_TYPE");
		}
		
		for(String i : design) 
			if(!i.equals("")) 
				test.eval(clean(i));
		for(String i : design)
			if(!i.equals(""))	
				test.eval(clean(i));
		for(String i : design)
			if(!i.equals(""))	
				test.eval(clean(i));
		test.appendToTextPane(outputTP);		
	} else {
		String appand = "Can't find a reslut\n\n";
		int size = quickFix.size();
		if(size == 1) 
			appand += size + " Quick Fix found: \n";
		else
			appand += size + " Quick Fixs found: \n";
		for(String i : quickFix) 
			appand += i + "\n";
			
		outputTP.setText(appand);
	}
		} 

		else 
			outputTP.setText("Bad Format.");
	}
	
	   protected static ImageIcon createImageIcon(String path) {
	        ImageIcon returnIcon = new ImageIcon(path);
	        return returnIcon;
	    }
	
	public static void main(String[] args) {
		new Main();
	}
}

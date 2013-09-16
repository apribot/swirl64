import java.io.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import java.lang.String;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.text.BadLocationException;


/**
 * -----------------------------------------------------------------------------
 * SWIRL64
 * 
 * @version 0.1b
 * -----------------------------------------------------------------------------
 */

public class swirl extends Frame 
{
	private Button copyButton;
    private Button cutButton;
    private Button pasteButton;
    private Button  exitButton;	
	private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;
	private JTextField textField;
	private JButton b1;
	private JLabel labelpw;
	private JTextArea textArea;
	private JFileChooser fc;
	
    private swirl() {

        super("SWIRL64");
        setSize(450, 255);

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        ActionListener buttonListener = new ActionListener() 
		{
            public void actionPerformed(ActionEvent ae) 
			{
                String action = ae.getActionCommand();
                
                if ("encdec".equals(action))
				{checkForState();}
				if ("NewFile".equals(action))
				{doNewFile();}
				if ("SaveFile".equals(action))
				{doSave();}
				if ("SaveFileAs".equals(action))
				{return;}		//needs to be implimented+++++++++++++++++++++++++++++++++
				if ("OpenFile".equals(action))
				{doOpen();}	
				if ("Exit".equals(action))
				{doExit();}	
				if ("About".equals(action))
				{doAbout();	}
            }
            
        };

		//menu bar
  		menuBar = new JMenuBar();
		menu = new JMenu("File");
        menuBar.add(menu);
        
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.setActionCommand("NewFile");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
		
        menuItem = new JMenuItem("Open", KeyEvent.VK_O);
		menuItem.setActionCommand("OpenFile");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
		
        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
		menuItem.setActionCommand("SaveFile");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
			
		menuItem = new JMenuItem("Save As");
		menuItem.setActionCommand("SaveFileAs");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
		
        menu.addSeparator();
		
        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setActionCommand("Exit");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
        
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
	           menuItem = new JMenuItem("About",
                                 KeyEvent.VK_N);
		menuItem.setActionCommand("About");
		menuItem.addActionListener(buttonListener);
		menu.add(menuItem);
	    add(menuBar, BorderLayout.NORTH);

        // mid Panel
        Panel midPanel = new Panel();

		labelpw = new JLabel("Password:");
        midPanel.add(labelpw);



        textField = new JTextField(20);
        textField.addActionListener(buttonListener);
        midPanel.add(textField);
        
  
		
		
		b1 = new JButton("Go");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("encdec");
		b1.addActionListener(buttonListener);
		b1.setToolTipText("do things, and also: stuff.");
		midPanel.add(b1);

		add(midPanel, BorderLayout.BEFORE_LINE_BEGINS );


		Panel bottomPanel = new Panel();
		
		textArea = new JTextArea(9, 38);
        textArea.setEditable(true);
		bottomPanel.add(textArea);	
        JScrollPane scrollPane = new JScrollPane(textArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bottomPanel.add(scrollPane);


		add(bottomPanel, BorderLayout.PAGE_END   );


    }

	private void doAbout()
	{
		JOptionPane.showMessageDialog(null, "SWIRL64 v0.1b\n64-bit strong encryption\n\ncoded by:j mikula");
	
	
	}

	private void doExit()
	{
		System.exit(0);
	
	}
	private void doSave()
	{
		fc = new JFileChooser();
		
		int returnVal = fc.showSaveDialog(swirl.this);
        
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
            File file = fc.getSelectedFile();
			
			
			String temp="";
			String dat = textArea.getText();
			int f;	
			
			FileOutputStream out = null;
			try 
			{
				out = new FileOutputStream(file);
				
				for(f=0;f<dat.length();f++)
				{
					out.write((int)dat.charAt(f));
				}
			}
			catch(IOException e)
			{
				System.out.println("ugh, I feel sick");
			} 
				
			try {
				if (out!= null) {
					out.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("ugh, I feel sick");
			}
			
			
			
	
		}	
		return;
	}				
	
	private void doOpen()
	{
		fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(swirl.this);
        
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
            File file = fc.getSelectedFile();
			String temp="";
			
			
			
			FileInputStream in = null;
		
			try {
				in = new FileInputStream(file);
		
				int c;
				while ((c = in.read()) != -1) {
					temp += (char)c;
				}
				}
			catch(IOException e)
			{
				System.out.println("ugh, I feel sick");
			} 
				
			try {
				if (in != null) {
					in.close();
				}
		
			}
			catch(IOException e)
			{
				System.out.println("ugh, I feel sick");
			}
			textArea.setText(temp);
		
		}	
		return;
	}
	

		
	
	private void doNewFile()
	{
		if(textArea.getText().length()!=0)
		{
			Object[] options = {"Yes", "No", "Cancel"};
			int n = JOptionPane.showOptionDialog(null,
			"Would you like to save changes?",
			"Question",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,     //don't use a custom Icon
			options,  //the titles of buttons
			options[0]); //default button title
			
			if(n==0)
			{
				doSave();
				textArea.setText("");
				textField.setText("");
				return;
			}
			if(n==1)
			{
				textArea.setText("");
				textField.setText("");
				return;
			}
			if(n==2)
			{
				return;
			}
			
			return;
		}
		
		else
		{
			return;
		}
		

	}


	private void checkForState()
	{
		if(textArea.getText().length() <= 11 || textArea.getText().substring(0,11).equals("//SWIRL64//")==false)
		{
			
			String inp = textArea.getText();
			if(inp.length()==0)
			{
				JOptionPane.showMessageDialog(null, "enter text");
				return;
			}
			String pw = textField.getText();
			if(pw.length()==0)
			{
				JOptionPane.showMessageDialog(null, "enter password");
				return;
			}
			textArea.setText(swirl(inp, pw));
			//textArea.setText(pwMask(inp, pw));  //for xor only	
			return;
			
		}
			
		if(textArea.getText().substring(0,11).equals("//SWIRL64//")==true)
		{
			
			String inp = textArea.getText();
			
			if(inp.length()==0)
			{
				JOptionPane.showMessageDialog(null, "enter text");
				return;
			}
			
			String pw = textField.getText();
			if(pw.length()==0)
			{
				JOptionPane.showMessageDialog(null, "enter password");
				return;
			}
			textArea.setText(deswirl(inp, pw));
			//textArea.setText(pwMask(inp, pw));  //for xor only
			return;
		}
		

	}
	
	
	
	private String swirl(String inp, String pw)
	{
		int inplen = inp.length();
		String temp;
		String outp = "";
		String binCollect = "";
		int q;
		int counter;
		String afterSwirlBinCollect="";
		char[][] aryBin = new char[8][8];
		char[][] aryBin2 = new char[8][8];
		
		int i=0;
		//string length must be multiple of 8
		//if it's not, add some filler spaces.
		while(inplen % 8 != 0)
		{
			inp += " ";
			inplen = inp.length();
		}
		
		for(i=0;i<inplen;i++)
		{
			//concat binary strings
			binCollect += intToBin((int)inp.charAt(i));
			
			//if there are 8 bin strings, it's time to swirl.
			if(binCollect.length()/8==8)
			{	//load everything into a 8x8 array
				int y, x;
				for(y=0;y<8;y++)
				{
					for(x=0;x<8;x++)
					{
						aryBin[x][y] = binCollect.charAt((y*8)+x);
					}
				}
				
				//apply some swirling (transfer to aryBin2)
				//++++++++++++++++++outer clockwise
				//top
				counter = 0;
				while(counter <7)
				{
					aryBin2[counter+1][0] = aryBin[counter][0];
					counter++;
				}
				//right
				counter = 0;
				while(counter <7)
				{
					aryBin2[7][counter+1] = aryBin[7][counter];
					counter++;
				}	
				//bottom
				counter = 7;
				while(counter >0)
				{
					aryBin2[counter-1][7] = aryBin[counter][7];
					counter--;
				}	
				//left
				counter = 7;
				while(counter >0)
				{
					aryBin2[0][counter-1] = aryBin[0][counter];
					counter--;
				}	
				
				//+++++++++++ second inner counter clockwise
				
		
				//top
				counter = 6;
				while(counter >1)
				{
					aryBin2[counter-1][1] = aryBin[counter][1];
					counter--;
				}
				//right
				counter = 6;
				while(counter >1)
				{
					aryBin2[6][counter-1] = aryBin[6][counter];
					counter--;
				}	
				//bottom
				counter = 1;
				while(counter <6)
				{
					aryBin2[counter+1][6] = aryBin[counter][6];
					counter++;
				}	
				//left
				counter = 1;
				while(counter <6)
				{
					aryBin2[1][counter+1] = aryBin[1][counter];
					counter++;
				}	
				
				
				
							//++++++++++++++++++3rd inner clockwise
				//top
				counter = 2;
				while(counter <5)
				{
					aryBin2[counter+1][2] = aryBin[counter][2];
					counter++;
				}
				//right
				counter = 2;
				while(counter <5)
				{
					aryBin2[5][counter+1] = aryBin[5][counter];
					counter++;
				}	
				//bottom
				counter = 5;
				while(counter >2)
				{
					aryBin2[counter-1][5] = aryBin[counter][5];
					counter--;
				}	
				//left
				counter = 5;
				while(counter >2)
				{
					aryBin2[2][counter-1] = aryBin[2][counter];
					counter--;
				}	
				
				//+++++++++++ 4th inner counter clockwise
				
		
				//top
				counter = 4;
				while(counter >3)
				{
					aryBin2[counter-1][3] = aryBin[counter][3];
					counter--;
				}
				//right
				counter = 4;
				while(counter >3)
				{
					aryBin2[4][counter-1] = aryBin[4][counter];
					counter--;
				}	
				//bottom
				counter = 3;
				while(counter <4)
				{
					aryBin2[counter+1][4] = aryBin[counter][4];
					counter++;
				}	
				//left
				counter = 3;
				while(counter <4)
				{
					aryBin2[3][counter+1] = aryBin[3][counter];
					counter++;
				}	
				
				
				
			
				//condense each line into a bin string and send it to binToDec
				for(y=0;y<8;y++)
				{
					afterSwirlBinCollect = "";
					for(x=0;x<8;x++)
					{
						afterSwirlBinCollect += aryBin2[x][y];
					}
					outp += (char) binToInt(afterSwirlBinCollect); //^ parseInt( (pw.charCodeAt(pwpoint) % 29) +2));
				}
				binCollect = "";
			} 
		}
		
		outp=pwMask(outp, pw);
		//put on marker
		outp = "//SWIRL64//" + outp;
		return outp;
	}

	private String deswirl(String inp, String pw)
	{
		String temp;
		int inplen = inp.length();
		//remove "//SWIRL64//"  marker and update data
		inp = inp.substring(11, inplen);
		inplen = inp.length();
		String outp = "";
		String binCollect = "";
		int q;
		int counter;
		String afterSwirlBinCollect="";
		char[][] aryBin = new char[8][8];
		char[][] aryBin2 = new char[8][8];

		
		int i=0;
		
		//string length must be multiple of 8
		//if it's not, add some filler spaces.
		if(inplen % 8 != 0)
		{JOptionPane.showMessageDialog(null, "this doesn't seem to be a valid cypher \nI'll try it anyway, though.\njust don't get all mad when it comes out wacky.");}
		
		while(inplen % 8 != 0)
		{
			inp += " ";
			inplen = inp.length();
		}
		
		temp = pwMask(inp, pw); //apply password xor masking
		
		for(i=0;i<inplen;i++)
		{
			//load up binary strings
			binCollect += intToBin((int)temp.charAt(i));
			
			//if there are 8 bin strings, it's time to deswirl.
		if(binCollect.length()/8==8)
			{	//load everything into a 8x8 array
				int y, x;
				for(y=0;y<8;y++)
				{
					for(x=0;x<8;x++)
					{
						aryBin[x][y] = binCollect.charAt((y*8)+x);
					}
				}
				
				
				
				counter = 7;
				while(counter >0)
				{
					aryBin2[counter-1][0] = aryBin[counter][0];
					counter--;
				}
				//right
				counter = 7;
				while(counter >0)
				{
					aryBin2[7][counter-1] = aryBin[7][counter];
					counter--;
				}	
				//bottom
				counter = 0;
				while(counter <7)
				{
					aryBin2[counter+1][7] = aryBin[counter][7];
					counter++;
				}	
				//left
				counter = 0;
				while(counter <7)
				{
					aryBin2[0][counter+1] = aryBin[0][counter];
					counter++;
				}	
				
				//+++++++++++ second inner clockwise
				
				//top
				counter = 1;
				while(counter <6)
				{
					aryBin2[counter+1][1] = aryBin[counter][1];
					counter++;
				}
				//right
				counter = 1;
				while(counter <6)
				{
					aryBin2[6][counter+1] = aryBin[6][counter];
					counter++;
				}	
				//bottom
				counter = 6;
				while(counter >1)
				{
					aryBin2[counter-1][6] = aryBin[counter][6];
					counter--;
				}	
				//left
				counter = 6;
				while(counter >1)
				{
					aryBin2[1][counter-1] = aryBin[1][counter];
					counter--;
				}	
				
				
				
							//++++++++++++++++++3rd inner counterclockwise
				//top
				counter = 5;
				while(counter >2)
				{
					aryBin2[counter-1][2] = aryBin[counter][2];
					counter--;
				}
				//right
				counter = 5;
				while(counter >2)
				{
					aryBin2[5][counter-1] = aryBin[5][counter];
					counter--;
				}	
				//bottom
				counter = 2;
				while(counter <5)
				{
					aryBin2[counter+1][5] = aryBin[counter][5];
					counter++;
				}	
				//left
				counter = 2;
				while(counter <5)
				{
					aryBin2[2][counter+1] = aryBin[2][counter];
					counter++;
				}	
				
				//+++++++++++ 4th inner clockwise
				
		
				//top
				counter = 3;
				while(counter <4)
				{
					aryBin2[counter+1][3] = aryBin[counter][3];
					counter++;
				}
				//right
				counter = 3;
				while(counter <4)
				{
					aryBin2[4][counter+1] = aryBin[4][counter];
					counter++;
				}	
				//bottom
				counter = 4;
				while(counter >3)
				{
					aryBin2[counter-1][4] = aryBin[counter][4];
					counter--;
				}	
				//left
				counter = 4;
				while(counter >3)
				{
					aryBin2[3][counter-1] = aryBin[3][counter];
					counter--;
				}	
				
				//condense each line into a bin string and send it to binToInt
				for(y=0;y<8;y++)
				{
					afterSwirlBinCollect = "";
					for(x=0;x<8;x++)
					{
						afterSwirlBinCollect += aryBin2[x][y];
					}
					outp += (char)(binToInt(afterSwirlBinCollect));
				}
				binCollect = "";
			}
		}
	
		return outp.trim();
	}


	private String pwMask(String dat, String pw)
	{
		int pwpoint = 0;
		int pwlen = pw.length();
		String temp = "";
		int datlen = dat.length();
		int i;
		for(i=0;i<datlen;i++)
		{
			temp += (char)( (int)dat.charAt(i) ^ (((int)pw.charAt(pwpoint) % 29) + 2));
			pwpoint++;
			if(pwpoint >= pwlen)
			{pwpoint=0;}
		}				
		return temp;
	}
	
	private String intToBin(int cup)
	{
		String temp="";
		int i;
		for(i=7;i>0;i--)
		{
			if(cup>=Math.pow(2,i))
			{	
				temp+="1";
				cup-=Math.pow(2,i);
			}
			else
			{temp+="0";}
		}
	
		if(cup>=1)
		{
			temp+="1";
			cup-=128;
		}
		else
		{temp+="0";}
	
		return temp;
	}

	private int binToInt(String cup)
	{
		int temp=0;
		int i=0;
		for(i=0;i<=6;i++)
		{
			if(cup.charAt(i)=='1')
			{
				temp+=Math.pow(2,(7-i));
			}
		}
		if(cup.charAt(7)=='1')
		{
			temp+=1;
		}
		
		return temp;
	}

    public static void main(String[] args) throws IOException {

        swirl mainFrame = new swirl();
        mainFrame.setVisible(true);

    }

}

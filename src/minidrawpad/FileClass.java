package minidrawpad;

import java.awt.Color;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;


public class FileClass {
   private DrawPad drawpad;
   DrawArea drawarea = null;
    FileClass(DrawPad dp,DrawArea da) {
		drawpad = dp;
		drawarea = da;
	}
    
	public void newFile() {

		drawarea.setIndex(0);
		drawarea.setCurrentChoice(3);
		drawarea.setColor(Color.black);
		drawarea.setStroke(1.0f);
		drawarea.createNewitem();
		drawarea.repaint();
	}

	public void openFile() {

		 JFileChooser filechooser = new JFileChooser();
		 filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		    int returnVal = filechooser.showOpenDialog(drawpad);
		    
		    if(returnVal == JFileChooser.CANCEL_OPTION) {
		       return;
		    }
		    File fileName = filechooser.getSelectedFile();
		    fileName.canRead();
		    if(fileName == null || fileName.getName().equals(""))
		    {
		    	JOptionPane.showMessageDialog(filechooser,JOptionPane.ERROR_MESSAGE);
		    }
		    
		    else {
		    	
					try {
						FileInputStream ifs = new FileInputStream(fileName);
						ObjectInputStream input = new ObjectInputStream(ifs);
						
						int countNumber = 0;
						Drawing inputRecord;
						countNumber = input.readInt();
						for(int i =0;i<countNumber;i++)
						{
							drawarea.setIndex(i);
							inputRecord = (Drawing)input.readObject();
							drawarea.itemList[i] = inputRecord;
						}
						drawarea.createNewitem();
						input.close();
						drawarea.repaint();
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(drawpad,JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(drawpad,JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(drawpad,JOptionPane.ERROR_MESSAGE);
					}
				
		    }
	}

	public void saveFile() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = filechooser.showSaveDialog(drawpad);
		if(result == JFileChooser.CANCEL_OPTION){
        	return ;
        }
        
        File fileName = filechooser.getSelectedFile();
	    fileName.canWrite();
	    if(fileName == null || fileName.getName().equals(""))
	    {
	    	JOptionPane.showMessageDialog(filechooser,JOptionPane.ERROR_MESSAGE);
	    }
	    else {
	    	try {
				fileName.delete();
				FileOutputStream fos = new FileOutputStream(fileName+".jpg");
				ObjectOutputStream output = new ObjectOutputStream(fos);
				
				output.writeInt(drawarea.getIndex());
				
				for(int i = 0;i<drawarea.getIndex() ;i++)
				{
					Drawing p = drawarea.itemList[i];
					output.writeObject(p);
					output.flush();
				}
				output.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
}

//Isabel Costa Lavorenti

package view;

import java.io.IOException;
import controller.BuscaController;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MenuPrincipal
{
	
	public static void main(String[] args) throws IOException 
	{
		
		boolean idExiste = false;
		int market = 0;
        	BuscaController controller = new BuscaController();
       	 	String ids = controller.retornaID();
           
        	while(idExiste == false)
        	{
        		JTextArea textArea = new JTextArea("                                                  Localizador de ID\n\nIDs cadastrados:\n" + ids + "\n\nInsira um ID:");
        		textArea.setWrapStyleWord(true);
        		textArea.setLineWrap(true);
        		textArea.setEditable(false); 
        		JScrollPane scrollPane = new JScrollPane(textArea);
        		scrollPane.setPreferredSize(new java.awt.Dimension(400,250));
        		market = Integer.parseInt(JOptionPane.showInputDialog(null, scrollPane, "Market", JOptionPane.PLAIN_MESSAGE));
        		idExiste = controller.exists(ids, market);
        	   
        		if(!idExiste) 
        		{
        			JOptionPane.showMessageDialog(null, market +" não é um ID válido!\nInsira novamente.");
        		}
        	   
         	}
          
        	String dados = controller.buscaDados(market);
         	JOptionPane.showMessageDialog(null, dados);
           
	}
}

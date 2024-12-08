package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BuscaController 
{
	
	public File validaSistema() 
	{
		
		String system = System.getProperty("os.name").toLowerCase(), path = null;
               
        	if (system.contains("win")) 
        	{
        		path = "C:"+ File.separator + "TEMP";
     	 	} 
    	    	else if (system.contains("nix") || system.contains("nux"))
        	{
           		path = File.separator + "tmp";
        	}
        
        	File file = new File(path, "trades.json");
		return file;
	}
	
	public boolean exists(String ids, int market) 
	{
		
		String marketStr = String.valueOf(market);
		String[] vetIds = ids.split("[-\\n]+");
		int tamanhoVet = vetIds.length;
		
		for(int i = 0; i < tamanhoVet; i++) 
		{
			vetIds[i] = vetIds[i].trim();
			
			if(vetIds[i].equals(marketStr)) 
			{
				return true;
			}
		}

		return false;
	
	}
	
	public String retornaID() throws IOException
	{
		
		StringBuilder stringBuilder = new StringBuilder();
        	File file = validaSistema();

        	if (file.exists() && file.isFile())
        	{
        	 
        		FileInputStream r = new FileInputStream(file);
        		InputStreamReader reader = new InputStreamReader(r); 
        		BufferedReader buffer = new BufferedReader(reader);
        		int divisor = 0;
        		String linha;
			
        		while ((linha = buffer.readLine()) != null)
        		{
        		
        			String[] vetLinha = linha.split("}");
        			int size = vetLinha.length;
        		
        			for(int i = 0; i < size; i++) 
        			{
        			
        				String[]vetSec = vetLinha[i].split(",");
					
        				int tamanho = vetSec.length;
				
        				for(int j = 0; j < tamanho - 1; j++) 
        				{
        					if (vetSec[j].contains("id")) 
        					{
        						String id= vetSec[j].substring(vetSec[j].indexOf(":") + 1).replace("\"", "").trim();
					
        						if(divisor < 4) 
        						{
        							stringBuilder.append(id).append(" - ");
        							divisor += 1;
        						} 
        						else
        						{
        							stringBuilder.append(id).append("\n");
        							divisor = 0;
        						}
        					}
        				}
        			}
				
        		}
			
			buffer.close();
			r.close();
			reader.close();
			stringBuilder.setLength(stringBuilder.length() - 1);

         	}
		
         	return stringBuilder.toString();	
	
	}

	public String buscaDados(int market) throws IOException 
	{
		
		String marketStr = String.valueOf(market);
		String response = null;
        	File file = validaSistema();
		
		if (file.exists() && file.isFile()) 
		{
			
			FileInputStream r = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader (r); 
			BufferedReader buffer = new BufferedReader (reader);

			String linha = buffer.readLine();
			String funds = null;
			String created = null;
			
			String[] vetLinha = linha.split("}");
			int size = vetLinha.length;
			
			for(int i = 0; i < size; i++) 
			{
					
				if(vetLinha[i].contains(marketStr)) 
				{
					
					String[] vetSec = vetLinha[i].split(",");		
					int size2 = vetSec.length;
	
					for(int j = 0; j < size2 ; j++)
					{
						if (vetSec[j].contains("funds")) 
						{
							funds = vetSec[j].substring(vetSec[j].indexOf(":") + 1).replace("\"", "").trim();
						}
						if (vetSec[j].contains("created")) 
						{
							created = vetSec[j].substring(vetSec[j].indexOf(":") + 1).replace("\"", "").replace("]", "").trim();	  
							created = created.replace("-", "/").replace("T", " às ").replace("Z", "").trim();
						}
							
						response = ( "ID "+ marketStr + " \nFunds = " + funds + "\nCriado em " + created);
						
					}
				}
			}
                
			buffer.close();
			r.close();
			reader.close();
				
		}
		
		return response;
		
	}
}

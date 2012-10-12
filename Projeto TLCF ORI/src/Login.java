/*
  +---------------------------------------------------------------+
  | Pontifícia Universidade Católica de Minas Gerais - PUC Minas  |
  | Campus de Poços de Caldas									  |
  | Curso de Bacharelado em Ciência da Computação                 |
  | Curso de Programação em Linguagem Java - Público Externo      |
  | Prof. João Benedito dos Santos Junior, Ph.D.                  |
  | Alunos: Guilherme Ricarte de Souza | Mat: 442891              |
  |         Douglas Ravaneli Andriani  | Mat: 442888              |
  +---------------------------------------------------------------+
  | Login.java	                                                  |
  | Objetivo: Desenvolver uma tela com o usuario e senha com obje-| 
  | do usuario entrar com os dados e o sistema efetuar a validação|
  | dos campos.                                                   |
  +---------------------------------------------------------------+
  */
	
import java.awt.event.*;
import java.io.*;
import java.security.*;
import javax.swing.*;
  	
  
public class Login extends JFrame implements ActionListener
{

  	JTextField txt_user;
  	JPasswordField txt_pass;
  	JButton btn_ok, btn_cancel;
        JLabel lbl_user, lbl_pass, lbl_introducao;
    
        String senha = "pass", nome = "user";
    
	public Login()
	  {
	  	setBounds (550,250,220,175);
	  	setTitle("Projeto de ORI + TCLF");
	  	setResizable(false);
	  	getContentPane().setLayout(null);
	  		
	  	txt_user = new JTextField("");
	  	txt_user.setBounds(70,40,135,25);
	  	
	  	txt_pass = new JPasswordField("");
		txt_pass.setBounds(70,75,135,25);
		  	
		btn_ok = new JButton("Ok");
		btn_ok.setBounds(155,110,50,25);
		btn_ok.addActionListener(this);
				
		btn_cancel = new JButton("Cancel");
		btn_cancel.setBounds(70,110,75,25);
		btn_cancel.addActionListener(this);
				
		lbl_introducao = new JLabel("Digite o Usuário e Senha:");
		lbl_introducao.setBounds(10,05,150,30);
		
		lbl_user = new JLabel("Usuário:");
		lbl_user.setBounds(10,35,50,40);
		
		lbl_pass = new JLabel("Senha:");
		lbl_pass.setBounds(10,70,50,40);
						
		getContentPane().add(txt_user);
		getContentPane().add(txt_pass);
		getContentPane().add(btn_ok);
		getContentPane().add(btn_cancel);
		getContentPane().add(lbl_introducao);
		getContentPane().add(lbl_user);
		getContentPane().add(lbl_pass);
		
		System.out.println(nome);  
		System.out.println(senha);
		
  	}
  	
        public void escreveArquivoDisco(String nomeArquivo)
	{
		byte hashPassword[];
                String passwordHashed;
                FileOutputStream arquivo;
		PrintStream escritor;
		
	    try 
	    {               
	        arquivo = new FileOutputStream(nomeArquivo);
                hashPassword = gerarHash(txt_pass.toString());
	        escritor = new PrintStream(arquivo);
                passwordHashed = stringHexa(hashPassword);
	        escritor.print(txt_user.getText() + "="+ passwordHashed + ";");
	                        
	        arquivo.close();   
	        
	        JOptionPane.showMessageDialog(this, "Arquivo gerado com sucesso...");
	    }
	    
	    catch(IOException erro)
	    {
                System.out.println(erro.getMessage());
	    	System.exit(1);
	    }
	}
        
    @Override
  	public void actionPerformed(ActionEvent evento)
  	{
  		Object objetoEvento = evento.getSource();
  		
  		if (objetoEvento == btn_cancel) {
                System.exit(0);
            }
            if (objetoEvento == btn_ok)
            {
                    escreveArquivoDisco("Files/login.txt");
                    this.dispose();
                    loginValidado validacao = new loginValidado();
                    if (txt_user.getText().equals(nome) && txt_pass.toString().equals(senha))
                    {
                            JOptionPane.showMessageDialog(this, "Acesso Liberado", "Aviso", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                    JOptionPane.showMessageDialog(this, "Senha Incorreta", "Aviso", JOptionPane.ERROR_MESSAGE);
                }

            }
  	}
        public static byte[] gerarHash(String frase) {
            try
            {
                MessageDigest md;
                md = MessageDigest.getInstance("MD5");
                md.update(frase.getBytes());
                return md.digest();
            }
            catch (NoSuchAlgorithmException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            return null;
      }	
        private static String stringHexa(byte[] bytes) 
        {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
                int parteBaixa = bytes[i] & 0xf;
                if (parteAlta == 0) {
                    s.append('0');
                }
                s.append(Integer.toHexString(parteAlta | parteBaixa));
            }
            return s.toString();
         }
    
    
	public static void main(String argumentos[])
  	{
  		JFrame aplicacao = new Login();
  		aplicacao.setVisible(true);
  	}
  }
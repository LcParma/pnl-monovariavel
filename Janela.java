import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.imageio.*;
import java.lang.Math;

public class Janela extends JFrame{

    JTextField txtFx,txtA,txtB,txtD,txtE,txtX;
    JButton btnGo,btnStop,btnClear;
    JRadioButton buni,bdic,fib,bis,newton,secau;
    JTextArea txtIt;
    ButtonGroup selecMet;
    final int MAX_IT = 30000;
    String f = "";
    double a = 0, b = 0, x = 0;
    double delta = 0, epsilon = 0;

    StringBuffer strIt = new StringBuffer();
    
    Janela(String titulo){
        super(titulo);
        JLabel lblFx = new JLabel("f(x) = ");
        JLabel lblA = new JLabel("a = ");
        JLabel lblB = new JLabel("b = ");
        JLabel lblD = new JLabel("Δ = ");
        JLabel lblE = new JLabel("ε = ");
        JLabel lblX = new JLabel("x* = ");
        txtFx = new JTextField(30);
        txtA = new JTextField(10);
        txtB = new JTextField(10);
        txtE = new JTextField(10);
        txtD = new JTextField(10);
        txtX = new JTextField(10);
        txtX.setEditable(false);
        Image imGo = null,imClear = null,imStop = null;
        ImageIcon icon = null;
        try{
            imGo = ImageIO.read(getClass().getResource("Imagens/forward.gif"));
            imClear = ImageIO.read(getClass().getResource("Imagens/delete.gif"));
            imStop = ImageIO.read(getClass().getResource("Imagens/stop2.gif")); 
            icon = new ImageIcon("Imagens/MNC-Raízes.ico");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Imagem de ícone não encontrada!\n" + e.toString());
            System.exit(1);
        }
        btnGo = new JButton(new ImageIcon(imGo));
        btnGo.setContentAreaFilled(false);
        btnClear = new JButton(new ImageIcon(imClear));
        btnClear.setContentAreaFilled(false);
        btnStop = new JButton(new ImageIcon(imStop));
        btnStop.setContentAreaFilled(false);
        
        buni = new JRadioButton("Busca Uniforme",true);
        bdic = new JRadioButton("Busca Dicotômica");
        secau = new JRadioButton("Seção Áurea");
        fib = new JRadioButton("Fibonacci");
        bis = new JRadioButton("Bisseção");
        newton = new JRadioButton("Newton");

        selecMet = new ButtonGroup();
        selecMet.add(buni);
        selecMet.add(bdic);
        selecMet.add(secau);
        selecMet.add(fib);
        selecMet.add(bis);
        selecMet.add(newton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(2,3));
        radioPanel.add(buni);
        radioPanel.add(fib);
        radioPanel.add(bis);
        radioPanel.add(bdic);
        radioPanel.add(secau);
        radioPanel.add(newton);

        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Métodos"));

        lblFx.setBounds(10,20,40,16);
        lblA.setBounds(318, 20, 30, 16);
        lblB.setBounds(427, 20, 30, 16);
        txtFx.setBounds(55, 20, 250, 20);
        txtA.setBounds(350, 20, 64, 20);
        txtB.setBounds(458, 20, 64, 20);
        lblD.setBounds(535, 20, 30, 16);
        lblE.setBounds(535,55,30,16);
        txtD.setBounds(567, 20,64,20);
        btnClear.setBounds(584,97,45,30);
        btnGo.setBounds(534,97,45,30);
        txtE.setBounds(567, 55, 64,20);

        JPanel pnSup = new JPanel(null);
        pnSup.add(lblFx);
        pnSup.add(lblA);
        pnSup.add(lblB);
        pnSup.add(txtFx);
        pnSup.add(txtA);
        pnSup.add(txtB);
        pnSup.add(lblD);
        pnSup.add(txtD);
        pnSup.add(btnGo);
        pnSup.add(btnClear);
        pnSup.add(radioPanel);
        pnSup.add(txtE);
        pnSup.add(lblE);

        pnSup.setBorder(BorderFactory.createTitledBorder("Função, intervalo de busca e seleção do método"));
        UIManager.put("TitledBorder.border", new LineBorder(new Color(0,0,0), 1));
        pnSup.setBounds(10,10,641, 140);
        radioPanel.setBounds(10,50,514,80);

        JPanel pnInf = new JPanel(null);
        pnInf.setBorder(BorderFactory.createTitledBorder("Solução e iterações"));
        pnInf.setBounds(10,160,641,280);

        lblX.setBounds(20,20,35,16);
        txtX.setBounds(60,20,250,20);
        pnInf.add(lblX);
        pnInf.add(txtX);

        txtD.setToolTipText("Valor do Delta");
        txtE.setToolTipText("Valor do Epsilon");

        txtD.setEditable(true);
        txtE.setEditable(false);

        ActionListener radioChangeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == buni){
                    txtD.setEditable(true);
                    txtE.setEditable(false);
                    txtE.setText("");
                }else if(e.getSource() == bdic){
                    txtD.setEditable(true);
                    txtE.setEditable(true);
                }else{
                    txtD.setEditable(false);
                    txtE.setEditable(true);
                    txtD.setText("");
                }
            }
        };

        buni.addActionListener(radioChangeListener);
        bdic.addActionListener(radioChangeListener);
        secau.addActionListener(radioChangeListener);
        fib.addActionListener(radioChangeListener);
        bis.addActionListener(radioChangeListener);
        newton.addActionListener(radioChangeListener);

        btnClear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                txtFx.setText("");
                txtA.setText("");
                txtB.setText("");
                txtD.setText("");
                txtX.setText("");
                txtE.setText("");
                txtIt.setText("");
            }
        });

        txtIt = new JTextArea();
        //txtIt.setEditable(false);
        JScrollPane paneIt = new JScrollPane(txtIt);
        paneIt.setBounds(20,50, 601,210);
        pnInf.add(paneIt);

        btnGo.setToolTipText("Calcular");
        btnClear.setToolTipText("Limpar dados");
        txtFx.setToolTipText("Função para minimização");
        txtA.setToolTipText("Início do intervalo");
        txtB.setToolTipText("Fim do intervalo");
        txtD.setToolTipText("Valor do Delta");
        txtX.setToolTipText("Solução do PNL");
        txtIt.setToolTipText("Descrição das iterações");
        txtIt.setEditable(false);

        btnGo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    f = txtFx.getText();
                    a = Double.parseDouble(txtA.getText());
                    b = Double.parseDouble(txtB.getText());
                    if(buni.isSelected() || bdic.isSelected())
                        delta = Double.parseDouble(txtD.getText());
                    if(!buni.isSelected())
                        epsilon = Double.parseDouble(txtE.getText());
                }catch(NullPointerException npe){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente!\n\n"+npe.toString(),"Erro",JOptionPane.ERROR_MESSAGE);
                    return;
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente!\n\n"+nfe.toString(),"Erro",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try{
                    Interpretador.FxR1(f, a);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Função inválida!\n\n"+ex.toString(),"Erro",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                strIt.delete(0, strIt.length());
                //Thread para  realizar o cálculo fora da Thread do Swing
                Thread calc_thread = new Thread(){
                    public void run(){
                        txtFx.setEditable(false);
                        txtA.setEditable(false);
                        txtB.setEditable(false);
                        txtD.setEditable(false);
                        txtE.setEditable(false);
                        btnClear.setEnabled(false);
                        btnGo.setEnabled(false);
                        buni.setEnabled(false);
                        bdic.setEnabled(false);
                        secau.setEnabled(false);
                        fib.setEnabled(false);
                        bis.setEnabled(false);
                        newton.setEnabled(false);
                        if(buni.isSelected()){
                            buscaUniforme(f, a, b, delta);

                        }else if(bdic.isSelected()){
                            buscaDico(f, a, b, delta, epsilon);
                        }else if(secau.isSelected()){
                            secaoAurea(f, a, b, epsilon);
                        }else if(fib.isSelected()){
                            fibonacci(f, a, b, epsilon);
                        }else if(bis.isSelected()){
                            bissecao(f, a, b, epsilon);
                        }else{
                            newton(f, a, b, epsilon);
                        }
                        txtFx.setEditable(true);
                        txtA.setEditable(true);
                        txtB.setEditable(true);
                        btnClear.setEnabled(true);
                        btnGo.setEnabled(true);
                        if(buni.isSelected() || bdic.isSelected())
                            txtD.setEditable(true);
                        if(!buni.isSelected())
                            txtE.setEditable(true);
                        buni.setEnabled(true);
                        bdic.setEnabled(true);
                        secau.setEnabled(true);
                        fib.setEnabled(true);
                        bis.setEnabled(true);
                        newton.setEnabled(true);
                    }
                };
                calc_thread.start();
            }
        });
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.add(pnSup);
        this.add(pnInf);
        this.setSize(661,480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    //----------------------------------------METODO DA BUSCA UNIFORME----------------------------------------------------------------
    public void buscaUniforme(String func, double val_a, double val_b, double delta){
        //JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
        double p,q,fp,fq;
        boolean refinado = false;
        p = val_a;
        q = p + delta;
        x = val_a;
        int k = 0;
        strIt.append(" k \t p \t q \t f(p) \t f(q) \t f(q) < f(p) \t Δ \n");
        while(q <= val_b && k < MAX_IT){
            k++;
            try {
                fp = Interpretador.FxR1(func, p);
                fq = Interpretador.FxR1(func, q);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!"+e.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \t",k,p,q,fp,fq));
            if(fq > fp){ // função está crescendo
                strIt.append(String.format("        F\t %.6f \n",delta));
                if(k == 1){ // preimeira iteração
                    txtX.setText(""+x);
                    strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fp +"\n");
                    txtIt.setText(strIt.toString());
                    return;
                }else if(!refinado){ // refinar
                    refinado = true;
                    p = p-delta;
                    delta = delta/10;
                    q = p + delta;
                }else{ // fim
                    x = p;
                    txtX.setText(""+x);
                    strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fp +"\n");
                    txtIt.setText(strIt.toString());
                    return;
                }
            }else{
                strIt.append(String.format("        V\t %.6f \n",delta));
                p = q;
                q = p + delta;
            }
        }
        x = val_b;
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        txtX.setText(""+x);
        double fb = 0;
        try{
            fb = Interpretador.FxR1(func, val_b);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+ex.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fb +"\n");
        txtIt.setText(strIt.toString());
        return;
    }
    //----------------------------------------METODO DA BUSCA DICOTOMICA----------------------------------------------------------------
    public void buscaDico(String func, double val_a, double val_b, double val_delta, double val_epsilon){
        //JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
        double val_x = 0, val_z = 0,base = 0;
        double fx = 0,fz = 0;
        int k = 0;
        strIt.append(" k \t a \t b \t x \t z \t f(x) \t f(z) \t b-a \n");
        while(val_b-val_a >= val_epsilon && k < MAX_IT){
            k++;
            base = (val_b+val_a)/2;
            val_x = base - val_delta;
            val_z = base + val_delta;
            try{
                fx = Interpretador.FxR1(func, val_x);
                fz = Interpretador.FxR1(func, val_z);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \t %.6f \t %.6f \t %.6f\n",k,val_a,val_b,val_x,val_z,fx,fz,val_b-val_a));
            if(fx > fz)
                val_a = val_x;
            else
                val_b = val_z;
        }
        x = (val_a+val_b)/2;
        try{
            fx = Interpretador.FxR1(func, x);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+ex.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        txtX.setText(""+x);
        strIt.append(String.format(" %02d\t%.6f \t %.6f \t -------- \t -------- \t -------- \t -------- \t %.6f\n",k+1,val_a,val_b,val_b-val_a));
        strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fx +"\n");
        txtIt.setText(strIt.toString());
        return;
    }
    //----------------------------------------METODO DA SECAO AUREA----------------------------------------------------------------
    public void secaoAurea(String func, double val_a, double val_b, double val_epsilon){
        //JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
        double mi = 0, lambda = 0, fMi = 0, fLam = 0;
        double alfa = (-1 + Math.sqrt(5)) / 2, beta;
        beta = 1 - alfa;
        
        int k = 0;
        strIt.append(" k \t a \t b \t μ \t λ \t f(μ) \t f(λ) \t f(μ)<f(λ) \n");
        while(val_b - val_a > val_epsilon && k < MAX_IT) {
	        mi = val_a + beta*(val_b - val_a);
	        lambda = val_a + alfa*(val_b - val_a);
	        try {
				fMi = Interpretador.FxR1(func, mi);
				fLam = Interpretador.FxR1(func, lambda);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
	        strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \t %.6f \t %.6f \t",k,val_a,val_b,mi,lambda,fMi,fLam));
	        if(fMi < fLam) {
	        	val_b = lambda;
	        	lambda = mi;
	        	mi = val_a + beta*(val_b - val_a);
	        	strIt.append("        V\n");
	        }else {
	        	val_a = mi;
	        	mi = lambda;
	        	lambda = val_a + alfa*(val_b - val_a);
	        	strIt.append("        F\n");
	        }
	        k++;
        }
        strIt.append(String.format(" --------\t%.6f \t %.6f \t -------- \t -------- \t -------- \t -------- \t\n",val_a,val_b));
        x = (val_b+val_a)/2;
        double fx = 0;
        try {
        	fx = Interpretador.FxR1(func, x);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fx +"\n");
        txtIt.setText(strIt.toString());
        txtX.setText(""+x);
        return;
    }
    //----------------------------------------METODO DE FIBONACCI----------------------------------------------------------------
    public void fibonacci(String func, double val_a, double val_b, double val_epsilon){
        //JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
        double fn = (val_b-val_a)/val_epsilon;
        ArrayList<Integer> list_fib = new ArrayList<Integer>();
        int n = -1;
        do{
            n++;
            if(n == 0 || n == 1)
                list_fib.add(1);
            else
                list_fib.add(list_fib.get(n-2) + list_fib.get(n-1));
        }while(list_fib.get(n) <= fn);
        strIt.append(" k \t a \t b \t μ \t λ \t f(μ) \t f(λ) \t f(μ)>f(λ) \n");
        int maxIt = n - 1;
        double mi = 0, lambda = 0,fmi = 0,flam = 0;
        int k;
        for( k = 0; k < maxIt && k < MAX_IT; k++){
            mi = val_a + list_fib.get(n-k-2)/Double.valueOf(list_fib.get(n-k))*(val_b-val_a);
            lambda = val_a + list_fib.get(n-k-1)/Double.valueOf(list_fib.get(n-k))*(val_b-val_a);
            try {
                fmi = Interpretador.FxR1(func, mi);
                flam = Interpretador.FxR1(func, lambda);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \t %.6f \t %.6f \t",k,val_a,val_b,mi,lambda,fmi,flam));
            if(fmi > flam){
                val_a = mi;
                strIt.append("        V\n");
            }else{
                val_b = lambda;
                strIt.append("        F\n");
            }
        }
        strIt.append(String.format(" --------\t%.6f \t %.6f \t -------- \t -------- \t -------- \t -------- \t\n",val_a,val_b));
        x = (val_b+val_a)/2;
        double fx = 0;
        try{
            fx = Interpretador.FxR1(func, x);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+ex.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        strIt.append("\n\tx* = " + x + "\n\tf(x*) = "+ fx +"\n");
        txtIt.setText(strIt.toString());
        txtX.setText(""+x);
        return;
    }
    //----------------------------------------METODO DA BISSECAO----------------------------------------------------------------
    public void bissecao(String func, double val_a, double val_b, double val_epsilon){
        double num_it = 0, fx_linha = 0, xk = 0, x_otimo = 0, fx_otimo = 0;
        double a = val_a; b = val_b;
        int k = 0;
        num_it = Math.round(Math.abs(Math.log(val_epsilon/(b - a)) / Math.log(2)));
        strIt.append(" k \t a \t b \t xk \t f '(xk)\n");
        do {
            xk = (a + b) / 2;
            fx_linha = derivadaPrimeira(func, xk);
            strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \n", k+1 ,a ,b ,xk ,fx_linha ));
            if(fx_linha > 0)
                b = xk;
            else if(fx_linha < 0)
                a = xk; 
            //System.out.println("num it\n "+ k);
        } while (++k < num_it && k < MAX_IT);
        x_otimo = (a + b) / 2;
        strIt.append(String.format(" --------\t%.6f \t %.6f \t -------- \t -------- \t\n",a,b));
        try {
            fx_otimo = Interpretador.FxR1(func, x_otimo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return;
        }
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        strIt.append("\n\tx* = " + x_otimo + "\n\tf(x*) = "+ fx_otimo +"\n");
        txtIt.setText(strIt.toString());
        txtX.setText(""+xk);
        //JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    //----------------------------------------METODO DE NEWTON----------------------------------------------------------------
    public void newton(String func, double val_a, double val_b, double val_epsilon){
        double fx_linha = 0, fxkmais1_linha = 0,fx_duaslinhas = 0, xk = val_a, x_kmais1 = 0, fx_otimo = 0;
        double cp = Double.MAX_VALUE;
        int k = 0;
        strIt.append(" k\txk \t f '(xk) \t f \"(xk) \t xk+1 \t f '(xk+1) \t CP \n");
        while(cp > val_epsilon && k < MAX_IT){
            fx_linha = derivadaPrimeira(func, xk);
            fx_duaslinhas = derivadaSegunda(func, xk);
            x_kmais1 = xk - (fx_linha / fx_duaslinhas);
            fxkmais1_linha = derivadaPrimeira(func, x_kmais1);
            cp = Math.abs(x_kmais1 - xk) / Math.max(Math.abs(x_kmais1), 1.0);
            //System.out.println("valor de cp "+ cp);
            strIt.append(String.format(" %02d\t%.6f \t %.6f \t %.6f \t %.6f \t %.6f \t %.6f\n", ++k ,xk ,fx_linha ,fx_duaslinhas ,x_kmais1, fxkmais1_linha, cp));
            xk = x_kmais1;
        }
        try {
            fx_otimo = Interpretador.FxR1(func, x_kmais1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(k >= MAX_IT)
            JOptionPane.showMessageDialog(this, "Máximo de iterações atingido!\n","Atenção", JOptionPane.WARNING_MESSAGE);
        strIt.append("\n\tx* = " + x_kmais1 + "\n\tf(x*) = "+ fx_otimo +"\n");
        txtIt.setText(strIt.toString());
        txtX.setText(""+xk);

        
       // JOptionPane.showMessageDialog(this, "Método não implementado!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    //----------------------------------------DERIVADA PRIMEIRA DE f(x)----------------------------------------------------------------
    public double derivadaPrimeira(String f, double x ){
        double eps = 1e-8;
        double h = 1.0, fx = 0, fx_ant = Double.MAX_VALUE;
        double xplus_h = 0, xminus_h = 0; //xplus_h = f(x + h); xminus_h = f(x - h)

        double err;
        double err_ant = Double.MAX_VALUE;

        for(int i=0; i < 10; i++){
            try {
                xplus_h = Interpretador.FxR1(f, x + h);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            
            try {
                xminus_h = Interpretador.FxR1(f, x - h);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            fx = (xplus_h - xminus_h)/(2*h);
            
            err = erro(fx, fx_ant);
            if (err < eps) {
                break;
            }

            fx_ant = fx;

            if (err_ant < err) {
                // Erro crescente
                break;
            }
            err_ant = err;
            h = h/2;
        }
        return fx;
    }

    //----------------------------------------DERIVADA SEGUNDA DE f(x)----------------------------------------------------------------
    public double derivadaSegunda(String f, double x ){
        double eps = 1e-8;
        double h = 1.0, fx = 0, fx_ant = Double.MAX_VALUE;
        double xp_hh = 0, xm_hh = 0, dois_fx = 0; //xp_hh = (x+2h); xm_hh = (x-2h); dois_fx = 2*f(x) 

        double err;
        double err_ant = Double.MAX_VALUE;

        for(int i=0; i < 10; i++){
            try {
                xp_hh = Interpretador.FxR1(f, x + (2*h));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            
            try {
                xm_hh = Interpretador.FxR1(f, x - (2*h));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return 1;
            }

            try {
                dois_fx = 2 * Interpretador.FxR1(f, x);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na avaliação da função!\n"+e.toString(),"Erro", JOptionPane.ERROR_MESSAGE);
                return 1;
            }

            fx = (xp_hh - dois_fx + xm_hh)/(4 * h * h);
            
            err = erro(fx, fx_ant);
            if (err < eps) {
                break;
            }

            fx_ant = fx;

            if (err_ant < err) {
                // Erro crescente
                break;
            }
            err_ant = err;
            h = h/2;
        }
      //  System.out.println("valor fora do LACO de D = " + d);
        return fx;
    }

    private static double erro(double fx, double fx_ant) {
        double denom = Math.max(Math.abs(fx), 1);
        return Math.abs(fx - fx_ant) / denom;
    }

    public static void main(String args []){
        Janela jan = new Janela("Trabalho PO II: PNL Monovariável");
        JOptionPane.showMessageDialog(jan, "<html><body><p width='200px' align='justify'>Ao utilizar funções trigonométricas acrescente 'r' ao final do nome da função para realizar o cálculo em radianos (ex.: sinr(x)).</p></body></html>", "Importante!",JOptionPane.INFORMATION_MESSAGE);
    }
}
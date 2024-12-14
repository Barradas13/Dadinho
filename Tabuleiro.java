
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;

public class Tabuleiro extends JPanel implements ActionListener {
    public static JLabel lblquantidade;
    public static JLabel lbldado;

    public static JLabel informacoesJogador;
    public static JLabel informacoesPassada;

    public static JTextField txtquantidade;
    public static JTextField txtdado;
    public static JButton btnJogar;
    public static JButton btnDuvido;

    public static boolean isFirstPlayer = true;

    public static Random gerador = new Random();

    
    public static ArrayList<Jogador> jogadores = new ArrayList<>();

    public static int max_dados = 3;
    public static int qtd_dados = 0;
    public static int jogador_atual = 0;
    public static int dado_anterior = 0;
    public static int qtd_anterior = 0;
    
    public static JFrame frm;

    // Construtor
    public Tabuleiro() {
        
        informacoesJogador = new JLabel("Jogador atual é: ");
        informacoesPassada = new JLabel("Nenhuma jogada foi feita ainda");

        lblquantidade = new JLabel("Quantidade: ");
        txtquantidade = new JTextField(10);
        lbldado = new JLabel("Dado: ");
        txtdado = new JTextField(10);
        btnJogar = new JButton("Jogar");
        btnDuvido = new JButton("Duvido");

        setLayout(null); 

        txtquantidade.setToolTipText("Informe a quantidade");
        txtdado.setToolTipText("Informe o dado");

        informacoesJogador.setBounds(50, 20, 300, 25);
        informacoesPassada.setBounds(50, 50, 300, 25);

        // Quantidade
        lblquantidade.setBounds(50, 100, 150, 25);      
        txtquantidade.setBounds(220, 100, 100, 25);     

        // Dados
        lbldado.setBounds(50, 150, 150, 25);            
        txtdado.setBounds(220, 150, 100, 25);           

        // Botões
        btnJogar.setBounds(50, 200, 100, 25);           
        btnDuvido.setBounds(270, 200, 100, 25);
    

        // Adicionando botões ao painel
        add(lblquantidade);
        add(txtquantidade);
        add(informacoesJogador);
        add(informacoesPassada);
        add(lbldado);
        add(txtdado);
        add(btnJogar);
        add(btnDuvido);

        btnJogar.addActionListener(this);
        btnDuvido.addActionListener(this);
    }

    public static void rodarDados(){
        for (Jogador i : jogadores) {

            for(int j = 0; j < i.getDados().size(); j++){
                int dado_atual = gerador.nextInt(6) + 1;
                i.getDados().set(j, dado_atual);
                j++;
            }

        }
    }

    public static void adicionaJogador(){
    
        Jogador jogador = Jogador.criarJogador();

        int dados_novos[] = new int[max_dados];

        for(int i = 0; i < max_dados; i++){
            dados_novos[i] = gerador.nextInt(6) + 1;
        }

        jogador.setDados(dados_novos);

        jogadores.add(jogador);
    }


    public static void duvido(){
        int dados_duvidados = 0;

        for(Jogador i: jogadores){
            for(int j : i.getDados()){
                if(j == dado_anterior || j == 1){
                    dados_duvidados++;
                }
            }
        }
    
        if(dados_duvidados >= qtd_anterior){
            tirarDado(jogador_atual);
        }else{
            System.out.println(jogador_atual);

            if(jogador_atual == -1){
                jogador_atual = jogadores.size() - 1;
            }

            tirarDado(jogador_atual);
        }

        if(jogadores.size() == 1){
            JOptionPane.showMessageDialog(null, "Parabens " + jogadores.get(0).getNome() + " você venceu!");
            System.exit(0);
        }

        try {
            informacoesJogador.setText(jogadores.get(jogador_atual).toString());
            informacoesPassada.setText("A jogada passada foi: " + 0 + " " + 0);
        } catch (Exception e) {
            while (jogador_atual < jogadores.size()) {
                jogador_atual = jogador_atual + 1 % jogadores.size();
            }
        }
        
        qtd_anterior = 0;
        dado_anterior = 0;

    }

    public static void colocaQtdJogadores(int qtd){
        for(int i = 0; i < qtd; i++){
            adicionaJogador();
        }
    }

    public void actionPerformed(ActionEvent actEvt) {
        // Verifica o botão clicado
        if (actEvt.getSource() == btnJogar) {
            jogar();
        } else if (actEvt.getSource() == btnDuvido) {
            duvido();
        }
    }
    
    private void jogar() {
        // Lendo dados
        String dado = txtdado.getText();
        String quantidade = txtquantidade.getText();
    
        if (dado.isEmpty() || quantidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos antes de jogar.");
            return;
        }
    
        try {
            int dado_INT = Integer.parseInt(dado.trim());
            int qtd_INT = Integer.parseInt(quantidade.trim());
    
            if (validarJogada(dado_anterior, qtd_anterior, dado_INT, qtd_INT)) {
                dado_anterior = dado_INT;
                qtd_anterior = qtd_INT;
    
                jogador_atual = (jogador_atual + 1) % jogadores.size();

                JOptionPane.showMessageDialog(this, "O próximo jogador é o: " + jogadores.get(jogador_atual).getNome());

                informacoesJogador.setText(jogadores.get(jogador_atual).toString());
                informacoesPassada.setText("A jogada passada foi: " + qtd_anterior + " " + dado_anterior);
            } else {
                JOptionPane.showMessageDialog(this, "Coloque uma jogada válida!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Insira números válidos.");
        }
    
        // Limpar campos de entrada
        txtquantidade.setText("");
        txtdado.setText("");
    }

    public static boolean validarJogada(int dado_anterior, int qtd_anterior, int dado_atual, int qtd_atuals){

        if(dado_anterior == 1){
            if(dado_atual == 1){
                if(qtd_atuals > qtd_anterior){
                    return true;
                }else{
                    return false;
                }
            }else{
                if(qtd_atuals >= (qtd_anterior * 2) + 1){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            if((dado_anterior < dado_atual || qtd_anterior < qtd_atuals) || (qtd_anterior / 2 <= qtd_atuals && dado_atual == 1)){
                return true;
            }else{
                return false;
            }
        }
    }

    public static void tirarDado(int jogador){
        
        if(jogadores.get(jogador).getDados().size() >= 1){
            jogadores.get(jogador).getDados().remove(0);
        }

        JOptionPane.showMessageDialog(null, "O jogador que perdeu dado foi o: " + jogadores.get(jogador).getNome() + " e agora está com " + jogadores.get(jogador).getDados().size(), null, jogador);

        for (Jogador i : jogadores) {
            i.rolarDados();
        }

        if(jogadores.get(jogador).getDados().size() <= 0){
            jogadores.remove(jogador);
            return;
        }
    }

    public static void exec(){
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 500));

        int qtdJogadores = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de jogadores a jogar"));

        colocaQtdJogadores(qtdJogadores);

        Tabuleiro janelaPrincipal = new Tabuleiro();

        frm = new JFrame("Rodada");
        
        System.out.println(jogadores.size());

        informacoesJogador.setText(jogadores.get(0).toString());
        informacoesPassada.setText("A jogada passada foi: " + qtd_anterior + " "  + dado_anterior);

        frm.setContentPane(janelaPrincipal);
        frm.setSize(430, 350);
        frm.setVisible(true);

        // Para fechar a janela corretamente
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        exec();
    }
}

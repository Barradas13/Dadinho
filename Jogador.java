

import java.util.ArrayList;
import javax.swing.*;

class Jogador {
    
    public String nome;
    public int pontos;
    public ArrayList<Integer> dados = null;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontos = 0;
    }

    public String getNome(){
        return this.nome;
    }

    public int getPontos(){
        return this.pontos;
    }

    public ArrayList<Integer> getDados(){
        return this.dados;
    }


    public void setPontos(int pontos){
        this.pontos = pontos;
    }

    public void adicionarPontos(int pontos){
        this.pontos += pontos;
    }

    public void setDados(int dados_novos[]){

        this.dados = new ArrayList<>();

        for(int dado : dados_novos){
            this.dados.add(dado);
        }
    }

    public static Jogador criarJogador(){
        String nome = JOptionPane.showInputDialog("Digite o nome do jogador:");
        
        return new Jogador(nome);
    }

    public String toString(){
        String mensagem = "Jogador atual é: " + this.getNome() + " \n";

        mensagem += "\n\n";

        mensagem += "Seus dados são: ";

        for (int i : this.getDados()) {
            mensagem += i + " ";
        }

        mensagem += "\n";

        return mensagem;
    }


}

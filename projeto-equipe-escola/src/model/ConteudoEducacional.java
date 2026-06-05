package model;

public abstract class ConteudoEducacional {
    private int id;
    private String nome;
    private int cargaHoraria;

    public ConteudoEducacional(int id, String nome, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setNome(String nomeNovo) {
        this.nome = nomeNovo;
    }

    public void setCargaHoraria(int cargaHorariaNova) {
        this.cargaHoraria = cargaHorariaNova;
    }
}

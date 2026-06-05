package model;

public class Disciplina extends ConteudoEducacional {
    private String descricao;
    private boolean ativo;

    public Disciplina(int id, String nome, int cargaHoraria, String descricao, boolean ativo) {
        super(id, nome, cargaHoraria);
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

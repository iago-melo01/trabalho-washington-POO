import java.util.List;
import java.util.Set;


public class Disciplina extends ConteudoEducacional{
    private String descricao;
    //private List<Turma> turmas; Atributo que vai ser usado depois de criar a classe Turma
    //private set<Professor> professoresAptos; Atributo que vai ser usado depois de criar a classe Professor
    private boolean ativo;
    public Disciplina(int id,String nome, int cargaHoraria,String descricao, boolean ativo){ // Construtor da Classe, chama atributos herdados da classe super() e atributos próprios para definilos sempre que a classe é chamada
        super(id,nome,cargaHoraria);
        this.descricao = descricao;
        //this.turmas = turmas; será usado depois que criar as classe Turma
        //this.professoresAptos = professores; será usado depois que criar as classe Professor
        this.ativo = ativo;
    }

}

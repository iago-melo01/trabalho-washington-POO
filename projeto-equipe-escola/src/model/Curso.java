import java.util.List;
import java.util.Scanner;


public class Curso extends ConteudoEducacional {
    private double valorMensal;
    private boolean ativo;
    private List<Disciplina> disciplinas;

    public Curso(int id,String nome,int cargaHoraria,double valorMensal,boolean ativo,List<Disciplina> disciplinas){
        super(id,nome,cargaHoraria);
        this.valorMensal = valorMensal;
        this.ativo = ativo;
        this.disciplinas = disciplinas;
    }

    public void adicionarDisciplina(int id,String nome, int cargaHoraria, String descricao,boolean ativo){
        Disciplina disciplinaNova = new Disciplina(id,nome,cargaHoraria,descricao,ativo); // cria um objeto da classe Disciplina
        disciplinas.add(disciplinaNova); // adiciona na lista de disciplinas do objeto da classe Curso
    }

    public void listarDisciplinas(){
        System.out.println("Lista todas as disciplinas...");
        for (Disciplina disciplina: disciplinas){ // for que percorre toda a lista de disciplinas
            System.out.println("Id: " + disciplina.getId());
            System.out.println("Nome: " + disciplina.getNome());
            System.out.println("Carga Horária: "+ disciplina.getCargaHoraria());
        }
    }

    public boolean atualizarNomeDisciplina(int id, String novoNome) {

        for (Disciplina disciplina : disciplinas) {

            if (disciplina.getId() == id) {

                disciplina.setNome(novoNome);
                return true;
            }
        }

        return false;
    }

    public boolean atualizarCargaHorariaDisciplina(int id, int novaCargaHoraria) {

        for (Disciplina disciplina : disciplinas) {

            if (disciplina.getId() == id) {

                disciplina.setCargaHoraria(novaCargaHoraria);
                return true;
            }
        }

        return false;
    }

    public boolean removerDisciplina(int id){
        for (int i = 0; i < disciplinas.size(); i++){ // roda um for um numero determinado de vezes até o último índice da lista de disciplinas
            if (disciplinas.get(i).getId() == id){ // checa a disciplina do índice atual do for, usa o getId para ver se é igual ao Id procurado pelo user
                disciplinas.remove(i); // remove da lista a disciplina do índice encontrado
                System.out.println("Disciplina removida com sucesso!");
                return true; // retorna true para o main
            }
        }
        System.out.println("Disciplina não encontrada."); // se não encontrar vai printar a mensagem e retornar false
        return false;
    }

    public void ativarCurso(){

        ativo = true;
    }

    public void desativarCurso(){

        ativo = false;
    }

    public boolean getAtivo(){

        return ativo;
    }

    public void exibirDados(){
        System.out.println("Exibindo dados...");
        System.out.println("Valor do curso: R$" + valorMensal);
        System.out.println("Curso Ativo: " + ativo);
        System.out.println("Disciplinas:");
        for (Disciplina disciplina : disciplinas){ // roda toda a disciplina e printa os dados de cada uma
            System.out.println("ID da disciplina: " + disciplina.getId());
            System.out.println("Disciplina: " + disciplina.getNome());
            System.out.println("Carga Horária: " + disciplina.getCargaHoraria());
        }
    }
}
